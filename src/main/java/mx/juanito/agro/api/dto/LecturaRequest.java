package mx.juanito.agro.api.dto;
import jakarta.validation.constraints.NotBlank;import jakarta.validation.constraints.NotNull;import java.time.Instant;
public record LecturaRequest(@NotNull Long parcelaId,@NotBlank String nodoId,double humedad,Double tempSueloC,Double bateriaV,Instant fecha){}
