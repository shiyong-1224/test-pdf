# test-pdf
I use pdfRenderExecutor & PdfUtils to render html to pdf

Test Results

| CorePoolSize | renderPdf average costs | Jmeter Threads |
|--------------|-------------------------|----------------|
| 2            | 50 ms                   | 50             |
| 8            | 80 ms                   | 50             |
| 16           | 130 ms                  | 50             |

I run this program on my PC with 16 cores CPU and 32G Memory