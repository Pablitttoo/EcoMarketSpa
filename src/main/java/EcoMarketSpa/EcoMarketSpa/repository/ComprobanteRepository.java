package EcoMarketSpa.EcoMarketSpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EcoMarketSpa.EcoMarketSpa.model.Comprobante;

@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Long> {

}
