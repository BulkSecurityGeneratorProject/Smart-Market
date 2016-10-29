package smartmarket.web.rest;

import com.codahale.metrics.annotation.Timed;
import smartmarket.domain.Market;
import smartmarket.service.MarketService;
import smartmarket.web.rest.util.HeaderUtil;
import smartmarket.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Market.
 */
@RestController
@RequestMapping("/api")
public class MarketResource {

    private final Logger log = LoggerFactory.getLogger(MarketResource.class);
        
    @Inject
    private MarketService marketService;

    /**
     * POST  /markets : Create a new market.
     *
     * @param market the market to create
     * @return the ResponseEntity with status 201 (Created) and with body the new market, or with status 400 (Bad Request) if the market has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/markets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Market> createMarket(@Valid @RequestBody Market market) throws URISyntaxException {
        log.debug("REST request to save Market : {}", market);
        if (market.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("market", "idexists", "A new market cannot already have an ID")).body(null);
        }
        Market result = marketService.save(market);
        return ResponseEntity.created(new URI("/api/markets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("market", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /markets : Updates an existing market.
     *
     * @param market the market to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated market,
     * or with status 400 (Bad Request) if the market is not valid,
     * or with status 500 (Internal Server Error) if the market couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/markets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Market> updateMarket(@Valid @RequestBody Market market) throws URISyntaxException {
        log.debug("REST request to update Market : {}", market);
        if (market.getId() == null) {
            return createMarket(market);
        }
        Market result = marketService.save(market);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("market", market.getId().toString()))
            .body(result);
    }

    /**
     * GET  /markets : get all the markets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of markets in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/markets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Market>> getAllMarkets(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Markets");
        Page<Market> page = marketService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/markets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /markets/:id : get the "id" market.
     *
     * @param id the id of the market to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the market, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/markets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Market> getMarket(@PathVariable Long id) {
        log.debug("REST request to get Market : {}", id);
        Market market = marketService.findOne(id);
        return Optional.ofNullable(market)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /markets/:id : delete the "id" market.
     *
     * @param id the id of the market to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/markets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMarket(@PathVariable Long id) {
        log.debug("REST request to delete Market : {}", id);
        marketService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("market", id.toString())).build();
    }

}
