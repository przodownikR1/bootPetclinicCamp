package pl.java.scalatech.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@EnableCaching
@Configuration
public class CacheConfig {
    
    @Bean
    public CacheManager cacheManager(EhCacheManagerFactoryBean factory) {
        return new EhCacheCacheManager(factory.getObject());
}
    
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
        factory.setCacheManagerName("EHCACHE");
        factory.setConfigLocation(new ClassPathResource("ehcache.xml"));        
        return factory;
}
}
