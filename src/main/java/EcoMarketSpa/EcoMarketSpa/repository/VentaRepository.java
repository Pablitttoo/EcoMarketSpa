package EcoMarketSpa.EcoMarketSpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EcoMarketSpa.EcoMarketSpa.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

}
