package mx.juanito.agro.controller;
import mx.juanito.agro.api.dto.LecturaRequest;import mx.juanito.agro.service.AgroService;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;import jakarta.validation.Valid;import java.util.Map;
@RestController @RequestMapping("/api/lecturas") public class LecturaController{private final AgroService service;public LecturaController(AgroService s){service=s;}@PostMapping public ResponseEntity<Map<String,Object>> crear(@Valid @RequestBody LecturaRequest d){return ResponseEntity.status(201).body(service.crearLectura(d));}}



