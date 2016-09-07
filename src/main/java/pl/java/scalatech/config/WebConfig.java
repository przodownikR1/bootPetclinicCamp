package pl.java.scalatech.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.service.ClinicService;
import pl.java.scalatech.web.LocalDateFormatter;
import pl.java.scalatech.web.PetTypeFormatter;
import pl.java.scalatech.web.VetsAtomView;

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
    
   /* @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver(){
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        ContentNegotiationManagerFactoryBean managerFactoryBean = new ContentNegotiationManagerFactoryBean();
        managerFactoryBean.setDefaultContentType(MediaType.TEXT_HTML);
        managerFactoryBean.setFavorPathExtension(Boolean.TRUE);
        Properties properties = new Properties();
        properties.setProperty("json", MediaType.APPLICATION_JSON_VALUE);
        properties.setProperty("xml", MediaType.APPLICATION_XML_VALUE);
        properties.setProperty("html", MediaType.TEXT_HTML_VALUE);
        
        managerFactoryBean.setMediaTypes(properties);
        resolver.setContentNegotiationManager(managerFactoryBean.getObject());
        return resolver;
}*/
    /*@Bean
    public ViewResolver setupViewResolver() {
        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
        viewResolver.setContentNegotiationManager(getContentNegotiationManager());
        return viewResolver;
    }

    private ContentNegotiationManager getContentNegotiationManager() {
        ContentNegotiationManagerFactoryBean manager = new ContentNegotiationManagerFactoryBean();
        manager.setFavorParameter(true);
        manager.setFavorPathExtension(true);
        manager.setParameterName("mediaType");
        manager.setDefaultContentType(MediaType.TEXT_HTML);
        manager.setMediaTypes(getMediaTypes());
        return manager.getObject();
    }

    private Properties getMediaTypes() {
        Properties properties = new Properties();
        properties.setProperty("json", MediaType.APPLICATION_JSON_VALUE);
        properties.setProperty("xml", MediaType.APPLICATION_XML_VALUE);
        return properties;
}*/
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        HashMap<String, MediaType> mediaTypes = new HashMap<>();              
        mediaTypes.put("xml", MediaType.APPLICATION_XML); 
        mediaTypes.put("json", MediaType.APPLICATION_JSON);
        mediaTypes.put("atom", MediaType.APPLICATION_ATOM_XML);
        configurer.mediaTypes(mediaTypes).defaultContentType(MediaType.TEXT_HTML).favorParameter(false).favorPathExtension(true); 
        super.configureContentNegotiation(configurer); 
      }
    
/*    .parameterName("type")
    .favorParameter(true)
    .ignoreUnknownPathExtensions(false)
    .ignoreAcceptHeader(false)
*/
    
    @Autowired
    private ThymeleafViewResolver thymeleafResolver;
    
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(this.thymeleafResolver);
        registry.enableContentNegotiation(
                new MappingJackson2XmlView(),
                new MappingJackson2JsonView(),
                new VetsAtomView()
                );
    }
}
