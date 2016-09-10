package pl.java.scalatech.web;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
@RestController
@Slf4j
public class CFController2 {
   
    RestTemplate restTemplate = new RestTemplate();
    
    
    @Async
    @SuppressWarnings("unchecked")
    public CompletableFuture<String> getWeather() {
        log.info("get weather @ {}");
        Map<String, Object> result = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q=London", Map.class);
        Double temperature = (Double) ((Map<String, Object>) result.get("main")).get("temp") - 273;
        return CompletableFuture.completedFuture("The current temperature " + temperature + " degrees.");
}
    
    @RequestMapping("/cf")
    CompletableFuture<String> hello(@RequestParam Optional<String> where) {
        return getWeather().exceptionally(e -> "Error! " + e.getMessage());
    }

}
