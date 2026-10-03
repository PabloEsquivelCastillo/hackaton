# Juanito Agro API

MVP de recomendaciones de riego por goteo para una parcela de jitomate. La API recomienda y registra las decisiones humanas; no acciona válvulas ni ejecuta riego.

## Requisitos y ejecución

- Java 17 o superior y Maven 3.9+.
- Base H2 en memoria y parcela El Zapote semilla al iniciar.
- Ejecutar: `mvn spring-boot:run` (puerto 8080).
- Pruebas: `mvn test`.
- Consola H2: `http://localhost:8080/h2-console` (JDBC `jdbc:h2:mem:juanito`, usuario `sa`).
- Importa `postman/JuanitoAgro.postman_collection.json` en Postman; corre primero la carpeta Demo sequía.

## Configuración

La conexión predeterminada está definida en `src/main/resources/application.properties`: H2 en memoria con URL `jdbc:h2:mem:juanito;DB_CLOSE_DELAY=-1`, usuario `sa` y contraseña vacía. No necesitas instalar un servidor de base de datos para la demo.

Para PostgreSQL, la app carga `application-postgres.properties` al activar el perfil. La base `juanito` debe existir y aceptar conexiones; configura las credenciales y arranca así en PowerShell:

```powershell
$env:SPRING_PROFILES_ACTIVE = "postgres"
$env:DB_URL = "jdbc:postgresql://localhost:5432/juanito"
$env:DB_USER = "postgres"
$env:DB_PASS = "tu contraseña"
mvn spring-boot:run
```

Los parámetros del producto también están en `application.properties`. Para clima real establece `APP_CLIMA_MODO=real`; el clima simulado permite fijar lluvia mediante `/api/dev/clima`.

## Endpoints

| Método | Ruta | Uso |
|---|---|---|
| POST | `/api/parcelas` | Crear parcela (`201`) |
| GET | `/api/parcelas` | Listar parcelas (`200`) |
| GET | `/api/parcelas/{id}` | Consultar parcela (`200`, `404`) |
| PUT | `/api/parcelas/{id}` | Actualizar parcela (`200`, `404`) |
| POST | `/api/lecturas` | Registrar lectura y generar/actualizar recomendación (`201`) |
| GET | `/api/parcelas/{id}/lecturas?horas=24&nodoId=N1` | Historial ascendente con filtros opcionales (`200`) |
| GET | `/api/parcelas/{id}/estado` | Semáforo, cálculo de riego y estado de sensores (`200`) |
| GET | `/api/parcelas/{id}/recomendaciones/actual` | Recomendación pendiente (`200`, `204`) |
| GET | `/api/parcelas/{id}/recomendaciones?limite=20` | Historial (`200`) |
| POST | `/api/recomendaciones/{id}/decision` | Registrar decisión humana (`200`, `404`, `409`) |
| GET | `/api/parcelas/{id}/tierra` | Resumen de cuidado de 7 días (`200`) |
| GET | `/api/parcelas/{id}/impacto?dias=10` | Indicadores de impacto (`200`) |
| GET | `/api/health` | Salud (`200`) |
| POST | `/api/dev/clima` | Fijar lluvia simulada (`200`) |
| DELETE | `/api/dev/reset?parcelaId=1` | Borrar lecturas y recomendaciones (`204`) |

### JSON para probar los endpoints

Envía `Content-Type: application/json` en las solicitudes con cuerpo. Para `GET` y `DELETE` no se envía JSON.

**Crear o actualizar parcela** — el mismo cuerpo sirve para `POST /api/parcelas` y `PUT /api/parcelas/{id}`:

```json
{
  "nombre": "El Zapote",
  "cultivo": "JITOMATE",
  "suelo": "FRANCO",
  "metodoRiego": "GOTEO",
  "areaM2": 10000,
  "caudalLh": 30000,
  "latitud": 18.92,
  "longitud": -99.23
}
```

**Registrar lectura** — `POST /api/lecturas`. `fecha`, `tempSueloC` y `bateriaV` son opcionales; si falta `fecha`, se usa la hora actual:

```json
{
  "parcelaId": 1,
  "nodoId": "N1",
  "humedad": 0.15,
  "tempSueloC": 22.4,
  "bateriaV": 3.87,
  "fecha": "2026-10-02T14:30:00Z"
}
```

**Registrar decisión humana** — `POST /api/recomendaciones/{id}/decision`:

```json
{ "decision": "VOY_A_REGAR" }
```

Valores admitidos: `VOY_A_REGAR`, `VOY_A_ESPERAR`, `YA_REGUE`.

**Fijar lluvia simulada** — `POST /api/dev/clima`:

```json
{ "parcelaId": 1, "lluvia48hMm": 15 }
```

Los enums disponibles para la parcela son `cultivo: JITOMATE`, `suelo: ARENOSO | FRANCO | ARCILLOSO` y `metodoRiego: GOTEO`. La humedad de lectura debe estar en el rango configurado (por defecto `0.0` a `0.7`).

### Topología del código

```text
mx.juanito.agro
├── JuanitoAgroApplication.java
├── config/                 AppProperties, SeedConfig
├── controller/             ParcelaController, LecturaController,
│                           RecomendacionController, DevController,
│                           HealthController, WebConfig, ApiErrors
├── repository/             ParcelaRepository, LecturaRepository,
│                           RecomendacionRepository
├── api/dto/                 ParcelaRequest, LecturaRequest,
│                           DecisionRequest, ClimaRequest
├── service/                AgroService (estado, reloj, recomendaciones,
│                           lecturas, tierra, impacto y clima)
├── domain/
│   ├── entity/             Parcela, Lectura, Recomendacion
│   └── enums/              Cultivo, Suelo, MetodoRiego, Nivel, Accion,
│                           Decision, EstadoNodo
├── motor/                  MotorRiego
```

La petición HTTP entra al `controller`, que valida y delega al `service`; el servicio ejecuta las reglas de negocio y consulta los `repository`; los repositorios leen/escriben las entidades JPA en H2 o PostgreSQL.

### Comprobación rápida

Con la API levantada en `localhost:8080`, esta secuencia prueba health, lectura y recomendación:

1. `GET /api/health` → `200` y `status: ok`.
2. `GET /api/parcelas/1` → `200` con la parcela semilla El Zapote.
3. `POST /api/lecturas` con una humedad de `0.15` → `201`, `accion: REGAR` y un `recomendacionId`.
4. `GET /api/parcelas/1/estado` → `200`, semáforo `ROJO`.
5. `GET /api/parcelas/1/recomendaciones/actual` → `200` con la recomendación pendiente.
6. `POST /api/recomendaciones/{recomendacionId}/decision` con `{"decision":"VOY_A_REGAR"}` → `200`.
7. `GET /api/parcelas/1/tierra` y `GET /api/parcelas/1/impacto` → `200`.

Se comprobaron en ejecución local los endpoints de salud, parcela, lectura, estado, recomendaciones, decisión, tierra, impacto, clima de desarrollo y reset. La colección Postman incluida contiene una demo inicial de sequía.

## Decisiones asumidas

- Coordenadas son obligatorias en el modelo, con valores numéricos validados por el DTO al crear la parcela; `GOTEO` es el único método admitido.
- Los promedios diarios de cuidado/impacto cuentan todas las lecturas recibidas, y el día se delimita en `app.zona`.
- Se usa hora simulada según la lectura más reciente de la parcela; sin lecturas usa el reloj del sistema.
- Los valores agrícolas son referencias genéricas y deben validarse con un técnico agrícola.
