package mx.juanito.agro.api.dto;
import mx.juanito.agro.domain.enums.*;import jakarta.validation.constraints.*;
public record ParcelaRequest(@NotBlank String nombre,@NotNull Cultivo cultivo,@NotNull Suelo suelo,@NotNull MetodoRiego metodoRiego,@Positive double areaM2,@Positive double caudalLh,@NotNull @DecimalMin("-90.0") @DecimalMax("90.0") Double latitud,@NotNull @DecimalMin("-180.0") @DecimalMax("180.0") Double longitud){}
