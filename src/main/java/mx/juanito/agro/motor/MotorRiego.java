package mx.juanito.agro.motor;
import mx.juanito.agro.domain.enums.*;
import java.util.*;
public class MotorRiego {
 public record Parametros(Suelo suelo,double areaM2,double caudalLh,double lluvia48hMm,double laminaMax,double lluviaSuficiente,double margenAviso,double margenExceso,int revisarHoras){}
 public record Resultado(Nivel nivel,Accion accion,String motivo,double laminaMm,double litros,Integer minutosGoteo,double pendienteMm,Double humedadPromedio,Double humedadPeor,double umbralRiego,double capacidadCampo){}
 public Resultado calcular(Parametros p,List<Double> hs){double cc=switch(p.suelo()){case ARENOSO->.12;case FRANCO->.27;case ARCILLOSO->.38;};double pmp=switch(p.suelo()){case ARENOSO->.05;case FRANCO->.12;case ARCILLOSO->.22;};double u=cc-.4*(cc-pmp);if(hs.isEmpty())return new Resultado(Nivel.GRIS,Accion.REVISAR_NODO,"No hay lecturas válidas de los sensores. Revisa que estén encendidos.",0,0,0,0,null,null,u,cc);
 double prom=hs.stream().mapToDouble(x->x).average().orElse(0),peor=hs.stream().mapToDouble(x->x).min().orElse(0);double neta=Math.max(0,cc-prom)*.4*1000,bruta=neta/.9,aplicar=Math.min(bruta,p.laminaMax()),litros=aplicar*p.areaM2();int min=(int)Math.round(litros/p.caudalLh()*60);double pend=Math.max(0,bruta-aplicar);double l=aplicar,li=litros;Integer mi=min;double pp=pend;Nivel n;Accion a;String motivo;String pr=Math.round(prom*100)+"%",um=Math.round(u*100)+"%",pe=Math.round(peor*100)+"%";
 if(prom>cc+p.margenExceso()){n=Nivel.ROJO;a=Accion.NO_REGAR;l=li=0;mi=0;motivo="El suelo ya tiene de sobra ("+pr+"). No riegues: puedes dañar las raíces y perder nutrientes.";}
 else if(prom<=u&&p.lluvia48hMm()>=p.lluviaSuficiente()){n=Nivel.AMARILLO;a=Accion.ESPERAR;motivo="El suelo está seco ("+pr+"), pero se esperan "+Math.round(p.lluvia48hMm())+" mm de lluvia en 48 h. Conviene esperar.";}
 else if(prom<=u){n=Nivel.ROJO;a=Accion.REGAR;motivo="El suelo está seco ("+pr+", mínimo recomendado "+um+") y no se espera lluvia en 2 días. Riega unos "+min+" minutos.";}
 else if(peor<=u){n=Nivel.AMARILLO;a=Accion.REVISAR_NODO;l=li=0;mi=0;motivo="Una zona de la parcela está seca ("+pe+"), aunque el promedio está bien. Revisa ese sector.";}
 else if(prom<=u+p.margenAviso()){n=Nivel.AMARILLO;a=Accion.REGAR_PRONTO;motivo="La humedad está cerca del mínimo recomendado ("+pr+"). Prepárate para regar.";}
 else{n=Nivel.VERDE;a=Accion.NADA;l=li=0;mi=0;pp=0;motivo="La humedad del suelo está en rango adecuado ("+pr+").";}
 if(pp>0)motivo+=" Después vuelve a revisar en "+p.revisarHoras()+" h.";return new Resultado(n,a,motivo,l,li,mi,pp,prom,peor,u,cc);
 }
}




