package mx.juanito.agro.controller;
import mx.juanito.agro.api.dto.ParcelaRequest;import mx.juanito.agro.domain.enums.*;import mx.juanito.agro.domain.entity.*;import mx.juanito.agro.repository.ParcelaRepository;import mx.juanito.agro.service.AgroService;import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import org.springframework.web.server.ResponseStatusException;import jakarta.validation.Valid;import jakarta.validation.constraints.*;import java.util.*;
@RestController @RequestMapping("/api/parcelas") public class ParcelaController{
 private final ParcelaRepository repo;private final AgroService service;public ParcelaController(ParcelaRepository repo,AgroService service){this.repo=repo;this.service=service;}
 @PostMapping public ResponseEntity<Parcela> crear(@Valid @RequestBody ParcelaRequest d){return ResponseEntity.status(201).body(repo.save(new Parcela(d.nombre(),d.cultivo(),d.suelo(),d.metodoRiego(),d.areaM2(),d.caudalLh(),d.latitud(),d.longitud())));}
 @GetMapping public List<Parcela> listar(){return repo.findAll();}
 @GetMapping("/{id}") public Parcela obtener(@PathVariable Long id){return repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Parcela inexistente."));}
 @PutMapping("/{id}") public Parcela actualizar(@PathVariable Long id,@Valid @RequestBody ParcelaRequest d){Parcela p=obtener(id);p.setNombre(d.nombre());p.setCultivo(d.cultivo());p.setSuelo(d.suelo());p.setMetodoRiego(d.metodoRiego());p.setAreaM2(d.areaM2());p.setCaudalLh(d.caudalLh());p.setLatitud(d.latitud());p.setLongitud(d.longitud());return repo.save(p);}
 @GetMapping("/{id}/estado") public AgroService.Estado estado(@PathVariable Long id){return service.estado(id);}
 @GetMapping("/{id}/lecturas") public List<Lectura> lecturas(@PathVariable Long id,@RequestParam(defaultValue="24")int horas,@RequestParam(required=false)String nodoId){return service.lecturas(id,horas,nodoId);}
 @GetMapping("/{id}/recomendaciones") public List<Recomendacion> recomendaciones(@PathVariable Long id,@RequestParam(defaultValue="20")int limite){return service.recs(id).stream().limit(limite).toList();}
 @GetMapping("/{id}/recomendaciones/actual") public ResponseEntity<Recomendacion> actual(@PathVariable Long id){var r=service.actual(id);return r==null?ResponseEntity.noContent().build():ResponseEntity.ok(r);}
 @GetMapping("/{id}/tierra") public Map<String,Object> tierra(@PathVariable Long id){return service.tierra(id);}
 @GetMapping("/{id}/impacto") public Map<String,Object> impacto(@PathVariable Long id,@RequestParam(defaultValue="10")int dias){return service.impacto(id,dias);}
}



