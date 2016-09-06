package pl.java.scalatech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.service.ClinicService;
import pl.java.scalatech.web.LocalDateFormatter;
import pl.java.scalatech.web.PetTypeFormatter;

@Configuration
@Slf4j
public class WebConfig extends  WebMvcConfigurationSupport{
   
    private final ClinicService clinicService;
    
   // private final LocaleChangeInterceptor localeChangeInterceptor;
    
    
    public WebConfig(ClinicService clinicService/*,LocaleChangeInterceptor localeChangeInterceptor*/) {
     log.info(":::::           webClinic              ::::::");
     this.clinicService = clinicService;
     /*this.localeChangeInterceptor = localeChangeInterceptor;*/
    }
    
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {  // mapujemy statyczne zasoby    
        log.info(":::::           addResourceHandlers              ::::::");
        registry.addResourceHandler("/internal/**").addResourceLocations("classpath:/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(3000);
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/");     
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/").setCachePeriod(3000);
        
    }
    

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info(":::::            addInterceptors              ::::::");
       // registry.addInterceptor(localeChangeInterceptor);
    }

    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {    
        log.info(":::::            addViewControllers             ::::::");   
        registry.addViewController("/").setViewName("welcome");
    }
    @Bean
    SimpleMappingExceptionResolver exceptionResolver(){
        log.info(":::::             exceptionResolver(           ::::::");  
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        resolver.setDefaultErrorView("exception");
        resolver.setWarnLogCategory("warn");
        return resolver;
        
    }
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.info(":::::            addFormatters            ::::::");   
        super.addFormatters(registry);
        registry.addFormatter(new PetTypeFormatter(clinicService));
        registry.addFormatter(new LocalDateFormatter());
        
    }
    
    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }
}
