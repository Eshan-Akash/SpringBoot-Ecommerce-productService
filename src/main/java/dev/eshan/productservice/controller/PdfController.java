package dev.eshan.productservice.controller;

import dev.eshan.productservice.service.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/pdf")
public class PdfController {
    @Autowired
    private PdfService pdfService;
    @GetMapping("/")
    public void getPdf(@RequestBody boolean needPdf, HttpServletResponse response) throws IOException {
        pdfService.generatePdf(needPdf, response);
    }
}
