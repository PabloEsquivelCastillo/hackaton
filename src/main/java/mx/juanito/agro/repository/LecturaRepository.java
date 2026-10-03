package mx.juanito.agro.repository;
import mx.juanito.agro.domain.entity.Lectura;import org.springframework.data.jpa.repository.JpaRepository;import java.time.Instant;import java.util.List;
public interface LecturaRepository extends JpaRepository<Lectura,Long>{List<Lectura> findByParcelaIdOrderByFechaAsc(Long id);List<Lectura> findByParcelaIdAndFechaBetweenOrderByFechaAsc(Long id,Instant desde,Instant hasta);List<Lectura> findByParcelaIdAndNodoIdOrderByFechaDesc(Long id,String nodo);}
