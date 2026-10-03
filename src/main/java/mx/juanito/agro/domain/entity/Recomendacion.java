package mx.juanito.agro.domain.entity;
import mx.juanito.agro.domain.enums.*;
import jakarta.persistence.*; import java.time.Instant;
@Entity public class Recomendacion {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; private Long parcelaId; @Enumerated(EnumType.STRING) private Nivel nivel; @Enumerated(EnumType.STRING) private Accion accion; @Column(length=500) private String motivo; private double laminaMm; private double litros; private Integer minutosGoteo; private double pendienteMm; private Instant creadaEn; private boolean vigente; @Enumerated(EnumType.STRING) private Decision decision; private Instant decididaEn;
 public Recomendacion(){} public Long getId(){return id;} public Long getParcelaId(){return parcelaId;} public Nivel getNivel(){return nivel;} public Accion getAccion(){return accion;} public String getMotivo(){return motivo;} public double getLaminaMm(){return laminaMm;} public double getLitros(){return litros;} public Integer getMinutosGoteo(){return minutosGoteo;} public double getPendienteMm(){return pendienteMm;} public Instant getCreadaEn(){return creadaEn;} public boolean isVigente(){return vigente;} public Decision getDecision(){return decision;} public Instant getDecididaEn(){return decididaEn;}
 public void actualizar(Long p,Nivel n,Accion a,String m,double l,double litros,Integer min,double pend,Instant fecha){parcelaId=p;nivel=n;accion=a;motivo=m;laminaMm=l;this.litros=litros;minutosGoteo=min;pendienteMm=pend;if(creadaEn==null)creadaEn=fecha;vigente=true;}
 public void setVigente(boolean v){vigente=v;} public void decidir(Decision d,Instant f){decision=d;decididaEn=f;}
}



