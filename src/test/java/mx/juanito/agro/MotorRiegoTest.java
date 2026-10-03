package mx.juanito.agro;
import mx.juanito.agro.motor.MotorRiego;import mx.juanito.agro.domain.enums.*;
import org.junit.jupiter.api.Test;import java.util.List;import static org.junit.jupiter.api.Assertions.*;
class MotorRiegoTest {
 private final MotorRiego m=new MotorRiego();private MotorRiego.Parametros p(Suelo s,double q,double lluvia){return new MotorRiego.Parametros(s,10000,q,lluvia,12,10,.03,.02,6);}
 @Test void sequiaCalculaRiegoEnTandas(){var r=m.calcular(p(Suelo.FRANCO,30000,0),List.of(.15,.16,.14));assertEquals(Nivel.ROJO,r.nivel());assertEquals(Accion.REGAR,r.accion());assertEquals(120000,r.litros());assertEquals(240,r.minutosGoteo());assertEquals(41.33,r.pendienteMm(),.02);}
 @Test void lluviaAplazaRiego(){var r=m.calcular(p(Suelo.FRANCO,30000,15),List.of(.15,.16,.14));assertEquals(Accion.ESPERAR,r.accion());assertEquals(Nivel.AMARILLO,r.nivel());}
 @Test void decideSegunElSuelo(){var f=m.calcular(p(Suelo.FRANCO,30000,0),List.of(.15,.15));var a=m.calcular(p(Suelo.ARENOSO,30000,0),List.of(.15));assertEquals(Accion.REGAR,f.accion());assertEquals(Accion.NO_REGAR,a.accion());}
 @Test void sinLecturasRevisarNodo(){var r=m.calcular(p(Suelo.FRANCO,30000,0),List.of());assertEquals(Nivel.GRIS,r.nivel());assertEquals(Accion.REVISAR_NODO,r.accion());}
 @Test void permiteTiempoDeRiegoDistintoPorCaudal(){assertEquals(480,m.calcular(p(Suelo.FRANCO,15000,0),List.of(.15,.15)).minutosGoteo());}
}



