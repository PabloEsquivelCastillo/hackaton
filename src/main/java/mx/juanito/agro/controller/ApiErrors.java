package mx.juanito.agro.controller;
import org.springframework.web.bind.annotation.*;import org.springframework.http.*;import org.springframework.web.bind.MethodArgumentNotValidException;import org.springframework.http.converter.HttpMessageNotReadableException;import org.springframework.web.server.ResponseStatusException;import java.time.Instant;import java.util.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;
@RestControllerAdvice class ApiErrors {
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<Map<String,Object>> bad(IllegalArgumentException e){return body(400,"Bad Request",e.getMessage(),Map.of());}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException e){Map<String,String> c=new LinkedHashMap<>();e.getBindingResult().getFieldErrors().forEach(x->c.put(x.getField(),"valor inválido"));return body(400,"Bad Request","Hay campos inválidos.",c);}
 @ExceptionHandler(HttpMessageNotReadableException.class) ResponseEntity<Map<String,Object>> json(Exception e){return body(400,"Bad Request","JSON malformado o valor enum inválido.",Map.of());}
 @ExceptionHandler(ResponseStatusException.class) ResponseEntity<Map<String,Object>> status(ResponseStatusException e){int s=e.getStatusCode().value();return body(s,HttpStatus.valueOf(s).getReasonPhrase(),e.getReason(),Map.of());}
 @ExceptionHandler(NoResourceFoundException.class) ResponseEntity<Map<String,Object>> missing(NoResourceFoundException e){return body(404,"Not Found","Recurso inexistente.",Map.of());}
 ResponseEntity<Map<String,Object>> body(int s,String err,String msg,Map<String,String> campos){return ResponseEntity.status(s).body(Map.of("timestamp",Instant.now(),"status",s,"error",err,"message",msg==null?err:msg,"campos",campos));}
}

