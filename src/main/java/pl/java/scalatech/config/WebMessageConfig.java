package pl.java.scalatech.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebMessageConfig {

    @Bean
    public MessageSource messageSource() {
        log.info(":::::            messageSource              ::::::");
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setCacheSeconds(20);
        return messageSource;
    }
}
