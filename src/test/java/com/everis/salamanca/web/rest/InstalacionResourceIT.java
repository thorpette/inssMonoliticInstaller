package com.everis.salamanca.web.rest;

import com.everis.salamanca.InssMonoliticInstallerApp;
import com.everis.salamanca.domain.Instalacion;
import com.everis.salamanca.repository.InstalacionRepository;
import com.everis.salamanca.service.InstalacionService;
import com.everis.salamanca.web.rest.errors.ExceptionTranslator;

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
 * Integration tests for the {@link InstalacionResource} REST controller.
 */
@SpringBootTest(classes = InssMonoliticInstallerApp.class)
public class InstalacionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private InstalacionRepository instalacionRepository;

    @Autowired
    private InstalacionService instalacionService;

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

    private MockMvc restInstalacionMockMvc;

    private Instalacion instalacion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InstalacionResource instalacionResource = new InstalacionResource(instalacionService);
        this.restInstalacionMockMvc = MockMvcBuilders.standaloneSetup(instalacionResource)
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
    public static Instalacion createEntity(EntityManager em) {
        Instalacion instalacion = new Instalacion()
            .name(DEFAULT_NAME)
            .descripcion(DEFAULT_DESCRIPCION);
        return instalacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instalacion createUpdatedEntity(EntityManager em) {
        Instalacion instalacion = new Instalacion()
            .name(UPDATED_NAME)
            .descripcion(UPDATED_DESCRIPCION);
        return instalacion;
    }

    @BeforeEach
    public void initTest() {
        instalacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstalacion() throws Exception {
        int databaseSizeBeforeCreate = instalacionRepository.findAll().size();

        // Create the Instalacion
        restInstalacionMockMvc.perform(post("/api/instalacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instalacion)))
            .andExpect(status().isCreated());

        // Validate the Instalacion in the database
        List<Instalacion> instalacionList = instalacionRepository.findAll();
        assertThat(instalacionList).hasSize(databaseSizeBeforeCreate + 1);
        Instalacion testInstalacion = instalacionList.get(instalacionList.size() - 1);
        assertThat(testInstalacion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstalacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createInstalacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instalacionRepository.findAll().size();

        // Create the Instalacion with an existing ID
        instalacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstalacionMockMvc.perform(post("/api/instalacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instalacion)))
            .andExpect(status().isBadRequest());

        // Validate the Instalacion in the database
        List<Instalacion> instalacionList = instalacionRepository.findAll();
        assertThat(instalacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instalacionRepository.findAll().size();
        // set the field null
        instalacion.setName(null);

        // Create the Instalacion, which fails.

        restInstalacionMockMvc.perform(post("/api/instalacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instalacion)))
            .andExpect(status().isBadRequest());

        List<Instalacion> instalacionList = instalacionRepository.findAll();
        assertThat(instalacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstalacions() throws Exception {
        // Initialize the database
        instalacionRepository.saveAndFlush(instalacion);

        // Get all the instalacionList
        restInstalacionMockMvc.perform(get("/api/instalacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instalacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getInstalacion() throws Exception {
        // Initialize the database
        instalacionRepository.saveAndFlush(instalacion);

        // Get the instalacion
        restInstalacionMockMvc.perform(get("/api/instalacions/{id}", instalacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(instalacion.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    public void getNonExistingInstalacion() throws Exception {
        // Get the instalacion
        restInstalacionMockMvc.perform(get("/api/instalacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstalacion() throws Exception {
        // Initialize the database
        instalacionService.save(instalacion);

        int databaseSizeBeforeUpdate = instalacionRepository.findAll().size();

        // Update the instalacion
        Instalacion updatedInstalacion = instalacionRepository.findById(instalacion.getId()).get();
        // Disconnect from session so that the updates on updatedInstalacion are not directly saved in db
        em.detach(updatedInstalacion);
        updatedInstalacion
            .name(UPDATED_NAME)
            .descripcion(UPDATED_DESCRIPCION);

        restInstalacionMockMvc.perform(put("/api/instalacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInstalacion)))
            .andExpect(status().isOk());

        // Validate the Instalacion in the database
        List<Instalacion> instalacionList = instalacionRepository.findAll();
        assertThat(instalacionList).hasSize(databaseSizeBeforeUpdate);
        Instalacion testInstalacion = instalacionList.get(instalacionList.size() - 1);
        assertThat(testInstalacion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstalacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingInstalacion() throws Exception {
        int databaseSizeBeforeUpdate = instalacionRepository.findAll().size();

        // Create the Instalacion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstalacionMockMvc.perform(put("/api/instalacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instalacion)))
            .andExpect(status().isBadRequest());

        // Validate the Instalacion in the database
        List<Instalacion> instalacionList = instalacionRepository.findAll();
        assertThat(instalacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstalacion() throws Exception {
        // Initialize the database
        instalacionService.save(instalacion);

        int databaseSizeBeforeDelete = instalacionRepository.findAll().size();

        // Delete the instalacion
        restInstalacionMockMvc.perform(delete("/api/instalacions/{id}", instalacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Instalacion> instalacionList = instalacionRepository.findAll();
        assertThat(instalacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
