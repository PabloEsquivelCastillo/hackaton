package mx.juanito.agro.controller;
import org.springframework.web.bind.annotation.*;import java.time.Instant;import java.util.Map;
@RestController public class HealthController{@GetMapping("/api/health") public Map<String,Object> health(){return Map.of("status","ok","hora",Instant.now());}}
