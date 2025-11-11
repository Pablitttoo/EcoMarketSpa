package EcoMarketSpa.EcoMarketSpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import EcoMarketSpa.EcoMarketSpa.model.CarritoCompra;

@Repository
public interface CarritoRepository extends JpaRepository<CarritoCompra, Long> {

}
