package mx.juanito.agro.config;
import mx.juanito.agro.domain.entity.Parcela;import mx.juanito.agro.domain.enums.*;import mx.juanito.agro.repository.ParcelaRepository;
import org.springframework.boot.CommandLineRunner;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;
@Configuration class SeedConfig {@Bean CommandLineRunner seed(ParcelaRepository repo){return args->{if(repo.count()==0)repo.save(new Parcela("El Zapote",Cultivo.JITOMATE,Suelo.FRANCO,MetodoRiego.GOTEO,10000,30000,18.92,-99.23));};}}



