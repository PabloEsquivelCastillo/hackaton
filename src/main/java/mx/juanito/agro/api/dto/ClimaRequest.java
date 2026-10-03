package mx.juanito.agro.api.dto;
import jakarta.validation.constraints.PositiveOrZero;
public record ClimaRequest(Long parcelaId,@PositiveOrZero double lluvia48hMm){}
