package EcoMarketSpa.EcoMarketSpa.repository;

import EcoMarketSpa.EcoMarketSpa.model.Proveedor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

}
