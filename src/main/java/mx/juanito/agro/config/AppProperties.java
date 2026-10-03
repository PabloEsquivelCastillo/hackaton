package mx.juanito.agro.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix="app")
public class AppProperties {
 public String zona="America/Mexico_City", corsOrigenes="*"; public boolean relojSimulado=true,devEndpoints=true; public Clima clima=new Clima(); public Lecturas lecturas=new Lecturas(); public Riego riego=new Riego(); public Recomendaciones recomendaciones=new Recomendaciones(); public Impacto impacto=new Impacto();
 public static class Clima{public String modo="simulado";} public static class Lecturas{public int maxAntiguedadMin=180,congeladoN=6;public double bateriaBajaV=3.3,humedadMinValida=0,humedadMaxValida=.7;} public static class Riego{public double laminaMaxPorRiegoMm=12,lluviaSuficienteMm=10,margenAviso=.03,margenExceso=.02;public int revisarDeNuevoHoras=6;} public static class Recomendaciones{public int silencioHoras=4;} public static class Impacto{public double riegoTradicionalMm=10;}
}

