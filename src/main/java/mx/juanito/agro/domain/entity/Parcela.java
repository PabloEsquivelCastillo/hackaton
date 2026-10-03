package mx.juanito.agro.domain.entity;

import mx.juanito.agro.domain.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Parcela {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank private String nombre;
 @Enumerated(EnumType.STRING) @NotNull private Cultivo cultivo;
 @Enumerated(EnumType.STRING) @NotNull private Suelo suelo;
 @Enumerated(EnumType.STRING) @NotNull private MetodoRiego metodoRiego;
 @Positive private double areaM2;
 @Positive private double caudalLh;
 private double latitud;
 private double longitud;
 public Parcela() {}
 public Parcela(String nombre,Cultivo cultivo,Suelo suelo,MetodoRiego metodoRiego,double areaM2,double caudalLh,double latitud,double longitud){this.nombre=nombre;this.cultivo=cultivo;this.suelo=suelo;this.metodoRiego=metodoRiego;this.areaM2=areaM2;this.caudalLh=caudalLh;this.latitud=latitud;this.longitud=longitud;}
 public Long getId(){return id;} public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;} public Cultivo getCultivo(){return cultivo;} public void setCultivo(Cultivo v){cultivo=v;} public Suelo getSuelo(){return suelo;} public void setSuelo(Suelo v){suelo=v;} public MetodoRiego getMetodoRiego(){return metodoRiego;} public void setMetodoRiego(MetodoRiego v){metodoRiego=v;} public double getAreaM2(){return areaM2;} public void setAreaM2(double v){areaM2=v;} public double getCaudalLh(){return caudalLh;} public void setCaudalLh(double v){caudalLh=v;} public double getLatitud(){return latitud;} public void setLatitud(double v){latitud=v;} public double getLongitud(){return longitud;} public void setLongitud(double v){longitud=v;}
}



