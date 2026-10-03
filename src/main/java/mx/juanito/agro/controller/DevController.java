package mx.juanito.agro.controller;
import mx.juanito.agro.api.dto.ClimaRequest;import mx.juanito.agro.service.AgroService;import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;import jakarta.validation.Valid;import java.util.Map;
@RestController @RequestMapping("/api/dev") @ConditionalOnProperty(prefix="app",name="dev-endpoints",havingValue="true",matchIfMissing=true) public class DevController{private final AgroService service;public DevController(AgroService s){service=s;}@PostMapping("/clima") public Map<String,Object> clima(@Valid @RequestBody ClimaRequest d){service.lluvia(d.parcelaId(),d.lluvia48hMm());return Map.of("parcelaId",d.parcelaId(),"lluvia48hMm",d.lluvia48hMm());}@DeleteMapping("/reset") public ResponseEntity<Void> reset(@RequestParam Long parcelaId){service.reset(parcelaId);return ResponseEntity.noContent().build();}}



