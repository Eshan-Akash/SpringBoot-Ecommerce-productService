package dev.eshan.productservice.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class AsyncCall {
    @Async
    public CompletableFuture<Void> doSomething() {
        System.out.println("Debug :: doSomething...");

        return CompletableFuture.completedFuture(null);
    }
}
