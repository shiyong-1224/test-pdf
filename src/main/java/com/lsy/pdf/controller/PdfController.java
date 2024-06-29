package com.lsy.pdf.controller;

import com.lsy.pdf.Utils.PdfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@RestController
@RequestMapping(value = "/pdf")
public class PdfController {

    @Autowired
    private ThreadPoolTaskExecutor pdfRenderExecutor;

    @GetMapping
    public String render(){

        Future<byte[]> future = pdfRenderExecutor.submit(() -> PdfUtils.renderPdf(getHtmlFromFile()));
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return "hello!";
    }

    @GetMapping("/hello")
    public String hello(@RequestParam String name){
        return "hello!" + name;
    }

    private String html;

    @Autowired
    private ResourceLoader resourceLoader;

    private String getHtmlFromFile(){
        if (html != null){
            return html;
        }
        synchronized (this){
            if (html != null){
                return html;
            }
            Resource resource = resourceLoader.getResource("classpath:html.html");
            try (FileReader fileReader = new FileReader(resource.getFile()); BufferedReader bf = new BufferedReader(fileReader)) {
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = bf.readLine()) != null){
                    sb.append(line);
                }
                html = sb.toString();
            }catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }
        return html;
    }

}
