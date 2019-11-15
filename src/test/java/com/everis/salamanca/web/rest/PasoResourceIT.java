package com.everis.salamanca.web.rest;

import com.everis.salamanca.InssMonoliticInstallerApp;
import com.everis.salamanca.domain.Paso;
import com.everis.salamanca.repository.PasoRepository;
import com.everis.salamanca.service.PasoService;
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

import com.everis.salamanca.domain.enumeration.Command;
/**
 * Integration tests for the {@link PasoResource} REST controller.
 */
@SpringBootTest(classes = InssMonoliticInstallerApp.class)
public class PasoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Command DEFAULT_COMMAND = Command.ROBOCOPY;
    private static final Command UPDATED_COMMAND = Command.DELETE;

    private static final String DEFAULT_ORIGEN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGEN = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINO = "AAAAAAAAAA";
    private static final String UPDATED_DESTINO = "BBBBBBBBBB";

    private static final String DEFAULT_PARAMETRO = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETRO = "BBBBBBBBBB";

    @Autowired
    private PasoRepository pasoRepository;

    @Autowired
    private PasoService pasoService;

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

    private MockMvc restPasoMockMvc;

    private Paso paso;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PasoResource pasoResource = new PasoResource(pasoService);
        this.restPasoMockMvc = MockMvcBuilders.standaloneSetup(pasoResource)
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
    public static Paso createEntity(EntityManager em) {
        Paso paso = new Paso()
            .name(DEFAULT_NAME)
            .command(DEFAULT_COMMAND)
            .origen(DEFAULT_ORIGEN)
            .destino(DEFAULT_DESTINO)
            .parametro(DEFAULT_PARAMETRO);
        return paso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paso createUpdatedEntity(EntityManager em) {
        Paso paso = new Paso()
            .name(UPDATED_NAME)
            .command(UPDATED_COMMAND)
            .origen(UPDATED_ORIGEN)
            .destino(UPDATED_DESTINO)
            .parametro(UPDATED_PARAMETRO);
        return paso;
    }

    @BeforeEach
    public void initTest() {
        paso = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaso() throws Exception {
        int databaseSizeBeforeCreate = pasoRepository.findAll().size();

        // Create the Paso
        restPasoMockMvc.perform(post("/api/pasos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paso)))
            .andExpect(status().isCreated());

        // Validate the Paso in the database
        List<Paso> pasoList = pasoRepository.findAll();
        assertThat(pasoList).hasSize(databaseSizeBeforeCreate + 1);
        Paso testPaso = pasoList.get(pasoList.size() - 1);
        assertThat(testPaso.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaso.getCommand()).isEqualTo(DEFAULT_COMMAND);
        assertThat(testPaso.getOrigen()).isEqualTo(DEFAULT_ORIGEN);
        assertThat(testPaso.getDestino()).isEqualTo(DEFAULT_DESTINO);
        assertThat(testPaso.getParametro()).isEqualTo(DEFAULT_PARAMETRO);
    }

    @Test
    @Transactional
    public void createPasoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pasoRepository.findAll().size();

        // Create the Paso with an existing ID
        paso.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPasoMockMvc.perform(post("/api/pasos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paso)))
            .andExpect(status().isBadRequest());

        // Validate the Paso in the database
        List<Paso> pasoList = pasoRepository.findAll();
        assertThat(pasoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pasoRepository.findAll().size();
        // set the field null
        paso.setName(null);

        // Create the Paso, which fails.

        restPasoMockMvc.perform(post("/api/pasos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paso)))
            .andExpect(status().isBadRequest());

        List<Paso> pasoList = pasoRepository.findAll();
        assertThat(pasoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPasos() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList
        restPasoMockMvc.perform(get("/api/pasos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paso.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].command").value(hasItem(DEFAULT_COMMAND.toString())))
            .andExpect(jsonPath("$.[*].origen").value(hasItem(DEFAULT_ORIGEN)))
            .andExpect(jsonPath("$.[*].destino").value(hasItem(DEFAULT_DESTINO)))
            .andExpect(jsonPath("$.[*].parametro").value(hasItem(DEFAULT_PARAMETRO)));
    }
    
    @Test
    @Transactional
    public void getPaso() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get the paso
        restPasoMockMvc.perform(get("/api/pasos/{id}", paso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paso.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.command").value(DEFAULT_COMMAND.toString()))
            .andExpect(jsonPath("$.origen").value(DEFAULT_ORIGEN))
            .andExpect(jsonPath("$.destino").value(DEFAULT_DESTINO))
            .andExpect(jsonPath("$.parametro").value(DEFAULT_PARAMETRO));
    }

    @Test
    @Transactional
    public void getNonExistingPaso() throws Exception {
        // Get the paso
        restPasoMockMvc.perform(get("/api/pasos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaso() throws Exception {
        // Initialize the database
        pasoService.save(paso);

        int databaseSizeBeforeUpdate = pasoRepository.findAll().size();

        // Update the paso
        Paso updatedPaso = pasoRepository.findById(paso.getId()).get();
        // Disconnect from session so that the updates on updatedPaso are not directly saved in db
        em.detach(updatedPaso);
        updatedPaso
            .name(UPDATED_NAME)
            .command(UPDATED_COMMAND)
            .origen(UPDATED_ORIGEN)
            .destino(UPDATED_DESTINO)
            .parametro(UPDATED_PARAMETRO);

        restPasoMockMvc.perform(put("/api/pasos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaso)))
            .andExpect(status().isOk());

        // Validate the Paso in the database
        List<Paso> pasoList = pasoRepository.findAll();
        assertThat(pasoList).hasSize(databaseSizeBeforeUpdate);
        Paso testPaso = pasoList.get(pasoList.size() - 1);
        assertThat(testPaso.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaso.getCommand()).isEqualTo(UPDATED_COMMAND);
        assertThat(testPaso.getOrigen()).isEqualTo(UPDATED_ORIGEN);
        assertThat(testPaso.getDestino()).isEqualTo(UPDATED_DESTINO);
        assertThat(testPaso.getParametro()).isEqualTo(UPDATED_PARAMETRO);
    }

    @Test
    @Transactional
    public void updateNonExistingPaso() throws Exception {
        int databaseSizeBeforeUpdate = pasoRepository.findAll().size();

        // Create the Paso

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPasoMockMvc.perform(put("/api/pasos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paso)))
            .andExpect(status().isBadRequest());

        // Validate the Paso in the database
        List<Paso> pasoList = pasoRepository.findAll();
        assertThat(pasoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaso() throws Exception {
        // Initialize the database
        pasoService.save(paso);

        int databaseSizeBeforeDelete = pasoRepository.findAll().size();

        // Delete the paso
        restPasoMockMvc.perform(delete("/api/pasos/{id}", paso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paso> pasoList = pasoRepository.findAll();
        assertThat(pasoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
