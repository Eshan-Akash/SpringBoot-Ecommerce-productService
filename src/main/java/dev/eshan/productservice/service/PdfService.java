package dev.eshan.productservice.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

@Service
public class PdfService {
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private AsyncCall asyncCall;

    public void generatePdf(boolean needPdf, HttpServletResponse response) throws IOException {
        // get the sampleform16.pdf file for resource and send that in response to the client

        System.out.println("Debug :: generatePdf....");

        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000); // Delay for 30 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return null; // Return null as a placeholder result
        }).thenComposeAsync(v -> {
            try {
                return asyncCall.doSomething();
            } catch (Exception e) {
                System.out.println("Error :: Failed to doSomething: " + e.getMessage());
                return CompletableFuture.completedFuture(null);
            }
        });

        // 3. Proceed with the remaining tasks (e.g., request meeting)
        System.out.println("Debug :: Proceeding with the remaining tasks...");


        if (needPdf) {
            Resource resource = resourceLoader.getResource(ResourceLoader.CLASSPATH_URL_PREFIX + "sampleform16.pdf");
            try (InputStream inputStream = resource.getInputStream()) {
                // set the content type and headers
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=sampleform16.pdf");
                // copy the input stream to the response output stream
                response.getOutputStream().write(inputStream.readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
