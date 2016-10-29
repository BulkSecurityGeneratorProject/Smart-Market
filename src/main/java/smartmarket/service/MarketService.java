package smartmarket.service;

import smartmarket.domain.Market;
import smartmarket.repository.MarketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Market.
 */
@Service
@Transactional
public class MarketService {

    private final Logger log = LoggerFactory.getLogger(MarketService.class);
    
    @Inject
    private MarketRepository marketRepository;

    /**
     * Save a market.
     *
     * @param market the entity to save
     * @return the persisted entity
     */
    public Market save(Market market) {
        log.debug("Request to save Market : {}", market);
        Market result = marketRepository.save(market);
        return result;
    }

    /**
     *  Get all the markets.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Market> findAll(Pageable pageable) {
        log.debug("Request to get all Markets");
        Page<Market> result = marketRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one market by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Market findOne(Long id) {
        log.debug("Request to get Market : {}", id);
        Market market = marketRepository.findOne(id);
        return market;
    }

    /**
     *  Delete the  market by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Market : {}", id);
        marketRepository.delete(id);
    }
}
