package EcoMarketSpa.EcoMarketSpa.repository;

import EcoMarketSpa.EcoMarketSpa.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p.stock FROM Producto p WHERE p.id = :id")
    Integer consultarStock(@Param("id") Long id);

}
