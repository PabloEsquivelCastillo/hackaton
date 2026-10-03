package mx.juanito.agro.api.dto;
import mx.juanito.agro.domain.enums.Decision;import jakarta.validation.constraints.NotNull;
public record DecisionRequest(@NotNull Decision decision){}
