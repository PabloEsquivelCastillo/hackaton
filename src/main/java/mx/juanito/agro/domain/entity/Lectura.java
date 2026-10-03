package mx.juanito.agro.domain.entity;
import mx.juanito.agro.domain.enums.*;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(indexes=@Index(columnList="parcelaId,nodoId,fecha"))
public class Lectura {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private Long parcelaId; private String nodoId; private double humedad; private Double tempSueloC; private Double bateriaV; private Instant fecha;
 public Lectura(){} public Lectura(Long p,String n,double h,Double t,Double b,Instant f){parcelaId=p;nodoId=n;humedad=h;tempSueloC=t;bateriaV=b;fecha=f;}
 public Long getId(){return id;} public Long getParcelaId(){return parcelaId;} public String getNodoId(){return nodoId;} public double getHumedad(){return humedad;} public Double getTempSueloC(){return tempSueloC;} public Double getBateriaV(){return bateriaV;} public Instant getFecha(){return fecha;} public void setFecha(Instant f){fecha=f;}
}



