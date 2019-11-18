package com.everis.salamanca.web.rest;

import com.everis.salamanca.InssMonoliticInstallerApp;
import com.everis.salamanca.domain.Paso;
import com.everis.salamanca.domain.Instalacion;
import com.everis.salamanca.repository.PasoRepository;
import com.everis.salamanca.service.PasoService;
import com.everis.salamanca.web.rest.errors.ExceptionTranslator;
import com.everis.salamanca.service.dto.PasoCriteria;
import com.everis.salamanca.service.PasoQueryService;

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
    private PasoQueryService pasoQueryService;

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
        final PasoResource pasoResource = new PasoResource(pasoService, pasoQueryService);
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
    public void getPasosByIdFiltering() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        Long id = paso.getId();

        defaultPasoShouldBeFound("id.equals=" + id);
        defaultPasoShouldNotBeFound("id.notEquals=" + id);

        defaultPasoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPasoShouldNotBeFound("id.greaterThan=" + id);

        defaultPasoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPasoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPasosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where name equals to DEFAULT_NAME
        defaultPasoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the pasoList where name equals to UPDATED_NAME
        defaultPasoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPasosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where name not equals to DEFAULT_NAME
        defaultPasoShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the pasoList where name not equals to UPDATED_NAME
        defaultPasoShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPasosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPasoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the pasoList where name equals to UPDATED_NAME
        defaultPasoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPasosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where name is not null
        defaultPasoShouldBeFound("name.specified=true");

        // Get all the pasoList where name is null
        defaultPasoShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPasosByNameContainsSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where name contains DEFAULT_NAME
        defaultPasoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the pasoList where name contains UPDATED_NAME
        defaultPasoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPasosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where name does not contain DEFAULT_NAME
        defaultPasoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the pasoList where name does not contain UPDATED_NAME
        defaultPasoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPasosByCommandIsEqualToSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where command equals to DEFAULT_COMMAND
        defaultPasoShouldBeFound("command.equals=" + DEFAULT_COMMAND);

        // Get all the pasoList where command equals to UPDATED_COMMAND
        defaultPasoShouldNotBeFound("command.equals=" + UPDATED_COMMAND);
    }

    @Test
    @Transactional
    public void getAllPasosByCommandIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where command not equals to DEFAULT_COMMAND
        defaultPasoShouldNotBeFound("command.notEquals=" + DEFAULT_COMMAND);

        // Get all the pasoList where command not equals to UPDATED_COMMAND
        defaultPasoShouldBeFound("command.notEquals=" + UPDATED_COMMAND);
    }

    @Test
    @Transactional
    public void getAllPasosByCommandIsInShouldWork() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where command in DEFAULT_COMMAND or UPDATED_COMMAND
        defaultPasoShouldBeFound("command.in=" + DEFAULT_COMMAND + "," + UPDATED_COMMAND);

        // Get all the pasoList where command equals to UPDATED_COMMAND
        defaultPasoShouldNotBeFound("command.in=" + UPDATED_COMMAND);
    }

    @Test
    @Transactional
    public void getAllPasosByCommandIsNullOrNotNull() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where command is not null
        defaultPasoShouldBeFound("command.specified=true");

        // Get all the pasoList where command is null
        defaultPasoShouldNotBeFound("command.specified=false");
    }

    @Test
    @Transactional
    public void getAllPasosByOrigenIsEqualToSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where origen equals to DEFAULT_ORIGEN
        defaultPasoShouldBeFound("origen.equals=" + DEFAULT_ORIGEN);

        // Get all the pasoList where origen equals to UPDATED_ORIGEN
        defaultPasoShouldNotBeFound("origen.equals=" + UPDATED_ORIGEN);
    }

    @Test
    @Transactional
    public void getAllPasosByOrigenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where origen not equals to DEFAULT_ORIGEN
        defaultPasoShouldNotBeFound("origen.notEquals=" + DEFAULT_ORIGEN);

        // Get all the pasoList where origen not equals to UPDATED_ORIGEN
        defaultPasoShouldBeFound("origen.notEquals=" + UPDATED_ORIGEN);
    }

    @Test
    @Transactional
    public void getAllPasosByOrigenIsInShouldWork() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where origen in DEFAULT_ORIGEN or UPDATED_ORIGEN
        defaultPasoShouldBeFound("origen.in=" + DEFAULT_ORIGEN + "," + UPDATED_ORIGEN);

        // Get all the pasoList where origen equals to UPDATED_ORIGEN
        defaultPasoShouldNotBeFound("origen.in=" + UPDATED_ORIGEN);
    }

    @Test
    @Transactional
    public void getAllPasosByOrigenIsNullOrNotNull() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where origen is not null
        defaultPasoShouldBeFound("origen.specified=true");

        // Get all the pasoList where origen is null
        defaultPasoShouldNotBeFound("origen.specified=false");
    }
                @Test
    @Transactional
    public void getAllPasosByOrigenContainsSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where origen contains DEFAULT_ORIGEN
        defaultPasoShouldBeFound("origen.contains=" + DEFAULT_ORIGEN);

        // Get all the pasoList where origen contains UPDATED_ORIGEN
        defaultPasoShouldNotBeFound("origen.contains=" + UPDATED_ORIGEN);
    }

    @Test
    @Transactional
    public void getAllPasosByOrigenNotContainsSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where origen does not contain DEFAULT_ORIGEN
        defaultPasoShouldNotBeFound("origen.doesNotContain=" + DEFAULT_ORIGEN);

        // Get all the pasoList where origen does not contain UPDATED_ORIGEN
        defaultPasoShouldBeFound("origen.doesNotContain=" + UPDATED_ORIGEN);
    }


    @Test
    @Transactional
    public void getAllPasosByDestinoIsEqualToSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where destino equals to DEFAULT_DESTINO
        defaultPasoShouldBeFound("destino.equals=" + DEFAULT_DESTINO);

        // Get all the pasoList where destino equals to UPDATED_DESTINO
        defaultPasoShouldNotBeFound("destino.equals=" + UPDATED_DESTINO);
    }

    @Test
    @Transactional
    public void getAllPasosByDestinoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where destino not equals to DEFAULT_DESTINO
        defaultPasoShouldNotBeFound("destino.notEquals=" + DEFAULT_DESTINO);

        // Get all the pasoList where destino not equals to UPDATED_DESTINO
        defaultPasoShouldBeFound("destino.notEquals=" + UPDATED_DESTINO);
    }

    @Test
    @Transactional
    public void getAllPasosByDestinoIsInShouldWork() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where destino in DEFAULT_DESTINO or UPDATED_DESTINO
        defaultPasoShouldBeFound("destino.in=" + DEFAULT_DESTINO + "," + UPDATED_DESTINO);

        // Get all the pasoList where destino equals to UPDATED_DESTINO
        defaultPasoShouldNotBeFound("destino.in=" + UPDATED_DESTINO);
    }

    @Test
    @Transactional
    public void getAllPasosByDestinoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where destino is not null
        defaultPasoShouldBeFound("destino.specified=true");

        // Get all the pasoList where destino is null
        defaultPasoShouldNotBeFound("destino.specified=false");
    }
                @Test
    @Transactional
    public void getAllPasosByDestinoContainsSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where destino contains DEFAULT_DESTINO
        defaultPasoShouldBeFound("destino.contains=" + DEFAULT_DESTINO);

        // Get all the pasoList where destino contains UPDATED_DESTINO
        defaultPasoShouldNotBeFound("destino.contains=" + UPDATED_DESTINO);
    }

    @Test
    @Transactional
    public void getAllPasosByDestinoNotContainsSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where destino does not contain DEFAULT_DESTINO
        defaultPasoShouldNotBeFound("destino.doesNotContain=" + DEFAULT_DESTINO);

        // Get all the pasoList where destino does not contain UPDATED_DESTINO
        defaultPasoShouldBeFound("destino.doesNotContain=" + UPDATED_DESTINO);
    }


    @Test
    @Transactional
    public void getAllPasosByParametroIsEqualToSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where parametro equals to DEFAULT_PARAMETRO
        defaultPasoShouldBeFound("parametro.equals=" + DEFAULT_PARAMETRO);

        // Get all the pasoList where parametro equals to UPDATED_PARAMETRO
        defaultPasoShouldNotBeFound("parametro.equals=" + UPDATED_PARAMETRO);
    }

    @Test
    @Transactional
    public void getAllPasosByParametroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where parametro not equals to DEFAULT_PARAMETRO
        defaultPasoShouldNotBeFound("parametro.notEquals=" + DEFAULT_PARAMETRO);

        // Get all the pasoList where parametro not equals to UPDATED_PARAMETRO
        defaultPasoShouldBeFound("parametro.notEquals=" + UPDATED_PARAMETRO);
    }

    @Test
    @Transactional
    public void getAllPasosByParametroIsInShouldWork() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where parametro in DEFAULT_PARAMETRO or UPDATED_PARAMETRO
        defaultPasoShouldBeFound("parametro.in=" + DEFAULT_PARAMETRO + "," + UPDATED_PARAMETRO);

        // Get all the pasoList where parametro equals to UPDATED_PARAMETRO
        defaultPasoShouldNotBeFound("parametro.in=" + UPDATED_PARAMETRO);
    }

    @Test
    @Transactional
    public void getAllPasosByParametroIsNullOrNotNull() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where parametro is not null
        defaultPasoShouldBeFound("parametro.specified=true");

        // Get all the pasoList where parametro is null
        defaultPasoShouldNotBeFound("parametro.specified=false");
    }
                @Test
    @Transactional
    public void getAllPasosByParametroContainsSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where parametro contains DEFAULT_PARAMETRO
        defaultPasoShouldBeFound("parametro.contains=" + DEFAULT_PARAMETRO);

        // Get all the pasoList where parametro contains UPDATED_PARAMETRO
        defaultPasoShouldNotBeFound("parametro.contains=" + UPDATED_PARAMETRO);
    }

    @Test
    @Transactional
    public void getAllPasosByParametroNotContainsSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList where parametro does not contain DEFAULT_PARAMETRO
        defaultPasoShouldNotBeFound("parametro.doesNotContain=" + DEFAULT_PARAMETRO);

        // Get all the pasoList where parametro does not contain UPDATED_PARAMETRO
        defaultPasoShouldBeFound("parametro.doesNotContain=" + UPDATED_PARAMETRO);
    }


    @Test
    @Transactional
    public void getAllPasosByInstalacionIsEqualToSomething() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);
        Instalacion instalacion = InstalacionResourceIT.createEntity(em);
        em.persist(instalacion);
        em.flush();
        paso.setInstalacion(instalacion);
        pasoRepository.saveAndFlush(paso);
        Long instalacionId = instalacion.getId();

        // Get all the pasoList where instalacion equals to instalacionId
        defaultPasoShouldBeFound("instalacionId.equals=" + instalacionId);

        // Get all the pasoList where instalacion equals to instalacionId + 1
        defaultPasoShouldNotBeFound("instalacionId.equals=" + (instalacionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPasoShouldBeFound(String filter) throws Exception {
        restPasoMockMvc.perform(get("/api/pasos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paso.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].command").value(hasItem(DEFAULT_COMMAND.toString())))
            .andExpect(jsonPath("$.[*].origen").value(hasItem(DEFAULT_ORIGEN)))
            .andExpect(jsonPath("$.[*].destino").value(hasItem(DEFAULT_DESTINO)))
            .andExpect(jsonPath("$.[*].parametro").value(hasItem(DEFAULT_PARAMETRO)));

        // Check, that the count call also returns 1
        restPasoMockMvc.perform(get("/api/pasos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPasoShouldNotBeFound(String filter) throws Exception {
        restPasoMockMvc.perform(get("/api/pasos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPasoMockMvc.perform(get("/api/pasos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
