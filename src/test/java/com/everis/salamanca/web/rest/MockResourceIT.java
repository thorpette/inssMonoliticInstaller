package com.everis.salamanca.web.rest;

import com.everis.salamanca.InssMonoliticInstallerApp;
import com.everis.salamanca.domain.Mock;
import com.everis.salamanca.repository.MockRepository;
import com.everis.salamanca.service.MockService;
import com.everis.salamanca.web.rest.errors.ExceptionTranslator;
import com.everis.salamanca.service.dto.MockCriteria;
import com.everis.salamanca.service.MockQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everis.salamanca.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MockResource} REST controller.
 */
@SpringBootTest(classes = InssMonoliticInstallerApp.class)
public class MockResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INPUT = "AAAAAAAAAA";
    private static final String UPDATED_INPUT = "BBBBBBBBBB";

    private static final String DEFAULT_OUTPUT = "AAAAAAAAAA";
    private static final String UPDATED_OUTPUT = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private MockRepository mockRepository;

    @Autowired
    private MockService mockService;

    @Autowired
    private MockQueryService mockQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMockMockMvc;

    private Mock mock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MockResource mockResource = new MockResource(mockService, mockQueryService);
        this.restMockMockMvc = MockMvcBuilders.standaloneSetup(mockResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mock createEntity(EntityManager em) {
        Mock mock = new Mock()
            .name(DEFAULT_NAME)
            .input(DEFAULT_INPUT)
            .output(DEFAULT_OUTPUT)
            .url(DEFAULT_URL);
        return mock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mock createUpdatedEntity(EntityManager em) {
        Mock mock = new Mock()
            .name(UPDATED_NAME)
            .input(UPDATED_INPUT)
            .output(UPDATED_OUTPUT)
            .url(UPDATED_URL);
        return mock;
    }

    @BeforeEach
    public void initTest() {
        mock = createEntity(em);
    }

    @Test
    @Transactional
    public void createMock() throws Exception {
        int databaseSizeBeforeCreate = mockRepository.findAll().size();

        // Create the Mock
        restMockMockMvc.perform(post("/api/mocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mock)))
            .andExpect(status().isCreated());

        // Validate the Mock in the database
        List<Mock> mockList = mockRepository.findAll();
        assertThat(mockList).hasSize(databaseSizeBeforeCreate + 1);
        Mock testMock = mockList.get(mockList.size() - 1);
        assertThat(testMock.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMock.getInput()).isEqualTo(DEFAULT_INPUT);
        assertThat(testMock.getOutput()).isEqualTo(DEFAULT_OUTPUT);
        assertThat(testMock.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createMockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mockRepository.findAll().size();

        // Create the Mock with an existing ID
        mock.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMockMockMvc.perform(post("/api/mocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mock)))
            .andExpect(status().isBadRequest());

        // Validate the Mock in the database
        List<Mock> mockList = mockRepository.findAll();
        assertThat(mockList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mockRepository.findAll().size();
        // set the field null
        mock.setName(null);

        // Create the Mock, which fails.

        restMockMockMvc.perform(post("/api/mocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mock)))
            .andExpect(status().isBadRequest());

        List<Mock> mockList = mockRepository.findAll();
        assertThat(mockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMocks() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList
        restMockMockMvc.perform(get("/api/mocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mock.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].input").value(hasItem(DEFAULT_INPUT)))
            .andExpect(jsonPath("$.[*].output").value(hasItem(DEFAULT_OUTPUT)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
    
    @Test
    @Transactional
    public void getMock() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get the mock
        restMockMockMvc.perform(get("/api/mocks/{id}", mock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mock.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.input").value(DEFAULT_INPUT))
            .andExpect(jsonPath("$.output").value(DEFAULT_OUTPUT))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }


    @Test
    @Transactional
    public void getMocksByIdFiltering() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        Long id = mock.getId();

        defaultMockShouldBeFound("id.equals=" + id);
        defaultMockShouldNotBeFound("id.notEquals=" + id);

        defaultMockShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMockShouldNotBeFound("id.greaterThan=" + id);

        defaultMockShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMockShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMocksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where name equals to DEFAULT_NAME
        defaultMockShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mockList where name equals to UPDATED_NAME
        defaultMockShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMocksByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where name not equals to DEFAULT_NAME
        defaultMockShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the mockList where name not equals to UPDATED_NAME
        defaultMockShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMocksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMockShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mockList where name equals to UPDATED_NAME
        defaultMockShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMocksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where name is not null
        defaultMockShouldBeFound("name.specified=true");

        // Get all the mockList where name is null
        defaultMockShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMocksByNameContainsSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where name contains DEFAULT_NAME
        defaultMockShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the mockList where name contains UPDATED_NAME
        defaultMockShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMocksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where name does not contain DEFAULT_NAME
        defaultMockShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the mockList where name does not contain UPDATED_NAME
        defaultMockShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMocksByInputIsEqualToSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where input equals to DEFAULT_INPUT
        defaultMockShouldBeFound("input.equals=" + DEFAULT_INPUT);

        // Get all the mockList where input equals to UPDATED_INPUT
        defaultMockShouldNotBeFound("input.equals=" + UPDATED_INPUT);
    }

    @Test
    @Transactional
    public void getAllMocksByInputIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where input not equals to DEFAULT_INPUT
        defaultMockShouldNotBeFound("input.notEquals=" + DEFAULT_INPUT);

        // Get all the mockList where input not equals to UPDATED_INPUT
        defaultMockShouldBeFound("input.notEquals=" + UPDATED_INPUT);
    }

    @Test
    @Transactional
    public void getAllMocksByInputIsInShouldWork() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where input in DEFAULT_INPUT or UPDATED_INPUT
        defaultMockShouldBeFound("input.in=" + DEFAULT_INPUT + "," + UPDATED_INPUT);

        // Get all the mockList where input equals to UPDATED_INPUT
        defaultMockShouldNotBeFound("input.in=" + UPDATED_INPUT);
    }

    @Test
    @Transactional
    public void getAllMocksByInputIsNullOrNotNull() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where input is not null
        defaultMockShouldBeFound("input.specified=true");

        // Get all the mockList where input is null
        defaultMockShouldNotBeFound("input.specified=false");
    }
                @Test
    @Transactional
    public void getAllMocksByInputContainsSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where input contains DEFAULT_INPUT
        defaultMockShouldBeFound("input.contains=" + DEFAULT_INPUT);

        // Get all the mockList where input contains UPDATED_INPUT
        defaultMockShouldNotBeFound("input.contains=" + UPDATED_INPUT);
    }

    @Test
    @Transactional
    public void getAllMocksByInputNotContainsSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where input does not contain DEFAULT_INPUT
        defaultMockShouldNotBeFound("input.doesNotContain=" + DEFAULT_INPUT);

        // Get all the mockList where input does not contain UPDATED_INPUT
        defaultMockShouldBeFound("input.doesNotContain=" + UPDATED_INPUT);
    }


    @Test
    @Transactional
    public void getAllMocksByOutputIsEqualToSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where output equals to DEFAULT_OUTPUT
        defaultMockShouldBeFound("output.equals=" + DEFAULT_OUTPUT);

        // Get all the mockList where output equals to UPDATED_OUTPUT
        defaultMockShouldNotBeFound("output.equals=" + UPDATED_OUTPUT);
    }

    @Test
    @Transactional
    public void getAllMocksByOutputIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where output not equals to DEFAULT_OUTPUT
        defaultMockShouldNotBeFound("output.notEquals=" + DEFAULT_OUTPUT);

        // Get all the mockList where output not equals to UPDATED_OUTPUT
        defaultMockShouldBeFound("output.notEquals=" + UPDATED_OUTPUT);
    }

    @Test
    @Transactional
    public void getAllMocksByOutputIsInShouldWork() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where output in DEFAULT_OUTPUT or UPDATED_OUTPUT
        defaultMockShouldBeFound("output.in=" + DEFAULT_OUTPUT + "," + UPDATED_OUTPUT);

        // Get all the mockList where output equals to UPDATED_OUTPUT
        defaultMockShouldNotBeFound("output.in=" + UPDATED_OUTPUT);
    }

    @Test
    @Transactional
    public void getAllMocksByOutputIsNullOrNotNull() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where output is not null
        defaultMockShouldBeFound("output.specified=true");

        // Get all the mockList where output is null
        defaultMockShouldNotBeFound("output.specified=false");
    }
                @Test
    @Transactional
    public void getAllMocksByOutputContainsSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where output contains DEFAULT_OUTPUT
        defaultMockShouldBeFound("output.contains=" + DEFAULT_OUTPUT);

        // Get all the mockList where output contains UPDATED_OUTPUT
        defaultMockShouldNotBeFound("output.contains=" + UPDATED_OUTPUT);
    }

    @Test
    @Transactional
    public void getAllMocksByOutputNotContainsSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where output does not contain DEFAULT_OUTPUT
        defaultMockShouldNotBeFound("output.doesNotContain=" + DEFAULT_OUTPUT);

        // Get all the mockList where output does not contain UPDATED_OUTPUT
        defaultMockShouldBeFound("output.doesNotContain=" + UPDATED_OUTPUT);
    }


    @Test
    @Transactional
    public void getAllMocksByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where url equals to DEFAULT_URL
        defaultMockShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the mockList where url equals to UPDATED_URL
        defaultMockShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllMocksByUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where url not equals to DEFAULT_URL
        defaultMockShouldNotBeFound("url.notEquals=" + DEFAULT_URL);

        // Get all the mockList where url not equals to UPDATED_URL
        defaultMockShouldBeFound("url.notEquals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllMocksByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where url in DEFAULT_URL or UPDATED_URL
        defaultMockShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the mockList where url equals to UPDATED_URL
        defaultMockShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllMocksByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where url is not null
        defaultMockShouldBeFound("url.specified=true");

        // Get all the mockList where url is null
        defaultMockShouldNotBeFound("url.specified=false");
    }
                @Test
    @Transactional
    public void getAllMocksByUrlContainsSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where url contains DEFAULT_URL
        defaultMockShouldBeFound("url.contains=" + DEFAULT_URL);

        // Get all the mockList where url contains UPDATED_URL
        defaultMockShouldNotBeFound("url.contains=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllMocksByUrlNotContainsSomething() throws Exception {
        // Initialize the database
        mockRepository.saveAndFlush(mock);

        // Get all the mockList where url does not contain DEFAULT_URL
        defaultMockShouldNotBeFound("url.doesNotContain=" + DEFAULT_URL);

        // Get all the mockList where url does not contain UPDATED_URL
        defaultMockShouldBeFound("url.doesNotContain=" + UPDATED_URL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMockShouldBeFound(String filter) throws Exception {
        restMockMockMvc.perform(get("/api/mocks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mock.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].input").value(hasItem(DEFAULT_INPUT)))
            .andExpect(jsonPath("$.[*].output").value(hasItem(DEFAULT_OUTPUT)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));

        // Check, that the count call also returns 1
        restMockMockMvc.perform(get("/api/mocks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMockShouldNotBeFound(String filter) throws Exception {
        restMockMockMvc.perform(get("/api/mocks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMockMockMvc.perform(get("/api/mocks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMock() throws Exception {
        // Get the mock
        restMockMockMvc.perform(get("/api/mocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMock() throws Exception {
        // Initialize the database
        mockService.save(mock);

        int databaseSizeBeforeUpdate = mockRepository.findAll().size();

        // Update the mock
        Mock updatedMock = mockRepository.findById(mock.getId()).get();
        // Disconnect from session so that the updates on updatedMock are not directly saved in db
        em.detach(updatedMock);
        updatedMock
            .name(UPDATED_NAME)
            .input(UPDATED_INPUT)
            .output(UPDATED_OUTPUT)
            .url(UPDATED_URL);

        restMockMockMvc.perform(put("/api/mocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMock)))
            .andExpect(status().isOk());

        // Validate the Mock in the database
        List<Mock> mockList = mockRepository.findAll();
        assertThat(mockList).hasSize(databaseSizeBeforeUpdate);
        Mock testMock = mockList.get(mockList.size() - 1);
        assertThat(testMock.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMock.getInput()).isEqualTo(UPDATED_INPUT);
        assertThat(testMock.getOutput()).isEqualTo(UPDATED_OUTPUT);
        assertThat(testMock.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingMock() throws Exception {
        int databaseSizeBeforeUpdate = mockRepository.findAll().size();

        // Create the Mock

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMockMockMvc.perform(put("/api/mocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mock)))
            .andExpect(status().isBadRequest());

        // Validate the Mock in the database
        List<Mock> mockList = mockRepository.findAll();
        assertThat(mockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMock() throws Exception {
        // Initialize the database
        mockService.save(mock);

        int databaseSizeBeforeDelete = mockRepository.findAll().size();

        // Delete the mock
        restMockMockMvc.perform(delete("/api/mocks/{id}", mock.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mock> mockList = mockRepository.findAll();
        assertThat(mockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
