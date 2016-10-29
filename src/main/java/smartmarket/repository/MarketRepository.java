package smartmarket.repository;

import smartmarket.domain.Market;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Market entity.
 */
@SuppressWarnings("unused")
public interface MarketRepository extends JpaRepository<Market,Long> {

    @Query("select market from Market market where market.user.login = ?#{principal.username}")
    List<Market> findByUserIsCurrentUser();

}
