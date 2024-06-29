package com.lsy.pdf.Utils;

import lombok.extern.slf4j.Slf4j;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

@Slf4j
public class PdfUtils {

    public static byte[] renderPdf(String html) {
        long start = System.currentTimeMillis();
        byte[] result;
        try (var os = new ByteArrayOutputStream()) {
            var render = new ITextRenderer();
            render.setDocumentFromString(html);
            render.layout();
            render.createPDF(os);
            result = os.toByteArray();
            os.flush();
            long end = System.currentTimeMillis();
            log.info("renderPdf costs: {} ms", end-start);
        } catch (Exception e) {
            log.error("renderPdf error", e);
            throw new RuntimeException(e.getMessage());
        }

        return result;
    }
}