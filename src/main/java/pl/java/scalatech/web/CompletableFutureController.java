package pl.java.scalatech.web;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompletableFutureController {
 final BlockingQueue<CompletableFuture<String>> q = new ArrayBlockingQueue<>(100);
    

    @RequestMapping("/hello")
    CompletableFuture hello() {
        CompletableFuture<String> future = new CompletableFuture<>();
        q.add(future);
        return future;
    }

    @Scheduled(initialDelay = 1_000, fixedDelay = 1_000)
    void emit() {
        for (int i = 0; i < 10; i++) {
            CompletableFuture<String> future = q.poll();
            if (future == null) return;
            log.info("emit");
            future.complete("Hello!");
        }
}
    //http://api.openweathermap.org/data/2.5/weather?q=London&APPID=4fb134c94be736b1b7ccea382d43b139
}
