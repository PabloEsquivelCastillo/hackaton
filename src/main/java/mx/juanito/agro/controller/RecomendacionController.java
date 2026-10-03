package mx.juanito.agro.controller;
import mx.juanito.agro.api.dto.DecisionRequest;import mx.juanito.agro.domain.entity.Recomendacion;import mx.juanito.agro.domain.enums.Decision;import mx.juanito.agro.service.AgroService;import org.springframework.web.bind.annotation.*;import jakarta.validation.Valid;import jakarta.validation.constraints.NotNull;
@RestController @RequestMapping("/api/recomendaciones") public class RecomendacionController{private final AgroService service;public RecomendacionController(AgroService s){service=s;}@PostMapping("/{id}/decision") public Recomendacion decidir(@PathVariable Long id,@Valid @RequestBody DecisionRequest d){return service.decision(id,d.decision());}}




