package mx.juanito.agro.repository;
import mx.juanito.agro.domain.entity.Recomendacion;import org.springframework.data.jpa.repository.JpaRepository;import java.util.List;
public interface RecomendacionRepository extends JpaRepository<Recomendacion,Long>{List<Recomendacion> findByParcelaIdOrderByCreadaEnDesc(Long id);List<Recomendacion> findByParcelaIdAndVigenteTrueAndDecisionIsNull(Long id);}
