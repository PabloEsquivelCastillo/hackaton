package mx.juanito.agro.controller;
import mx.juanito.agro.config.AppProperties;import org.springframework.context.annotation.Configuration;import org.springframework.web.servlet.config.annotation.*;
@Configuration public class WebConfig implements WebMvcConfigurer{private final AppProperties props;public WebConfig(AppProperties props){this.props=props;}@Override public void addCorsMappings(CorsRegistry r){r.addMapping("/api/**").allowedOrigins(props.corsOrigenes.split(",")).allowedMethods("GET","POST","PUT","DELETE","OPTIONS");}}
