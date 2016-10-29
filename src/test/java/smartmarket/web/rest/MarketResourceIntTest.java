package smartmarket.web.rest;

import smartmarket.SmartmarketApp;

import smartmarket.domain.Market;
import smartmarket.repository.MarketRepository;
import smartmarket.service.MarketService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MarketResource REST controller.
 *
 * @see MarketResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartmarketApp.class)
public class MarketResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_CNPJ = 1;
    private static final Integer UPDATED_CNPJ = 2;

    private static final String DEFAULT_STREET = "AAAAA";
    private static final String UPDATED_STREET = "BBBBB";

    private static final String DEFAULT_NUMBER = "AAAAA";
    private static final String UPDATED_NUMBER = "BBBBB";

    @Inject
    private MarketRepository marketRepository;

    @Inject
    private MarketService marketService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMarketMockMvc;

    private Market market;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketResource marketResource = new MarketResource();
        ReflectionTestUtils.setField(marketResource, "marketService", marketService);
        this.restMarketMockMvc = MockMvcBuilders.standaloneSetup(marketResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Market createEntity(EntityManager em) {
        Market market = new Market()
                .name(DEFAULT_NAME)
                .cnpj(DEFAULT_CNPJ)
                .street(DEFAULT_STREET)
                .number(DEFAULT_NUMBER);
        return market;
    }

    @Before
    public void initTest() {
        market = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarket() throws Exception {
        int databaseSizeBeforeCreate = marketRepository.findAll().size();

        // Create the Market

        restMarketMockMvc.perform(post("/api/markets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(market)))
                .andExpect(status().isCreated());

        // Validate the Market in the database
        List<Market> markets = marketRepository.findAll();
        assertThat(markets).hasSize(databaseSizeBeforeCreate + 1);
        Market testMarket = markets.get(markets.size() - 1);
        assertThat(testMarket.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMarket.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testMarket.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testMarket.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketRepository.findAll().size();
        // set the field null
        market.setName(null);

        // Create the Market, which fails.

        restMarketMockMvc.perform(post("/api/markets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(market)))
                .andExpect(status().isBadRequest());

        List<Market> markets = marketRepository.findAll();
        assertThat(markets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCnpjIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketRepository.findAll().size();
        // set the field null
        market.setCnpj(null);

        // Create the Market, which fails.

        restMarketMockMvc.perform(post("/api/markets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(market)))
                .andExpect(status().isBadRequest());

        List<Market> markets = marketRepository.findAll();
        assertThat(markets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketRepository.findAll().size();
        // set the field null
        market.setStreet(null);

        // Create the Market, which fails.

        restMarketMockMvc.perform(post("/api/markets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(market)))
                .andExpect(status().isBadRequest());

        List<Market> markets = marketRepository.findAll();
        assertThat(markets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketRepository.findAll().size();
        // set the field null
        market.setNumber(null);

        // Create the Market, which fails.

        restMarketMockMvc.perform(post("/api/markets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(market)))
                .andExpect(status().isBadRequest());

        List<Market> markets = marketRepository.findAll();
        assertThat(markets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMarkets() throws Exception {
        // Initialize the database
        marketRepository.saveAndFlush(market);

        // Get all the markets
        restMarketMockMvc.perform(get("/api/markets?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(market.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getMarket() throws Exception {
        // Initialize the database
        marketRepository.saveAndFlush(market);

        // Get the market
        restMarketMockMvc.perform(get("/api/markets/{id}", market.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(market.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarket() throws Exception {
        // Get the market
        restMarketMockMvc.perform(get("/api/markets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarket() throws Exception {
        // Initialize the database
        marketService.save(market);

        int databaseSizeBeforeUpdate = marketRepository.findAll().size();

        // Update the market
        Market updatedMarket = marketRepository.findOne(market.getId());
        updatedMarket
                .name(UPDATED_NAME)
                .cnpj(UPDATED_CNPJ)
                .street(UPDATED_STREET)
                .number(UPDATED_NUMBER);

        restMarketMockMvc.perform(put("/api/markets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMarket)))
                .andExpect(status().isOk());

        // Validate the Market in the database
        List<Market> markets = marketRepository.findAll();
        assertThat(markets).hasSize(databaseSizeBeforeUpdate);
        Market testMarket = markets.get(markets.size() - 1);
        assertThat(testMarket.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMarket.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testMarket.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testMarket.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void deleteMarket() throws Exception {
        // Initialize the database
        marketService.save(market);

        int databaseSizeBeforeDelete = marketRepository.findAll().size();

        // Get the market
        restMarketMockMvc.perform(delete("/api/markets/{id}", market.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Market> markets = marketRepository.findAll();
        assertThat(markets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
