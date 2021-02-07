package com.sardine.common.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * PDF 工具类
 *
 * @author keith
 */
public class PdfUtils {

    /* 生成 PDF 最大大小为 4M */
    private static final int PDF_MAX_SIZE = 4 * 1024 * 1024;

    /* PDF 默认 DPI 为 72 */
    private static final int PDF_DPI = 72;

    /**
     * Pdf压缩至4M以下
     * 将Pdf转为图片压缩，压缩后再将图片转为Pdf
     *
     * @param bytes 未压缩的Pdf字节流
     * @return  压缩后的Pdf字节流
     */
    public static byte[] compress(byte[] bytes){
        PDDocument doc = new PDDocument();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
        bos.write(bytes, 0, bytes.length);
        try {
            doc.save(bos);
            if (bytes.length > PDF_MAX_SIZE){
                int dpi = (PDF_DPI * PDF_MAX_SIZE) / bytes.length;
                // 加载Pdf
                PDDocument document = PDDocument.load(bytes);
                // 将Pdf转成对应的图片，并根据传递的dpi按比例压缩
                List<BufferedImage> images = pdfToImages(document, dpi);
                // 将图片转换成Pdf
                return imagesToPdf(images);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 如果大小已经是4M以下了，那么直接返回
        return bytes;
    }

    private static List<BufferedImage> pdfToImages(PDDocument document, int dpi) throws IOException {
        List<BufferedImage> images = new ArrayList<>();
        PDFRenderer renderer = new PDFRenderer(document);
        int pages = document.getNumberOfPages();
        for (int i = 0; i < pages; i++){
            BufferedImage image = renderer.renderImageWithDPI(1, dpi);
            images.add(image);
        }
        return images;
    }

    private static byte[] imagesToPdf(List<BufferedImage> images) throws IOException {
        if (CollectionUtils.isEmpty(images)){
            return null;
        }
        ByteArrayOutputStream bos = null;
        try {
            PDDocument document = new PDDocument();
            bos = new ByteArrayOutputStream();
            for (BufferedImage image : images) {
                PDPage pdPage = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));
                document.addPage(pdPage);
                PDImageXObject pdImageXObject = LosslessFactory.createFromImage(document, image);
                // 将文件写入新的Pdf中
                PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
                contentStream.drawImage(pdImageXObject, 0, 0, image.getWidth(), image.getHeight());
                contentStream.close();
            }
            document.save(bos);
            document.close();
        } finally {
            if (bos != null)
                bos.close();
        }
        return bos.toByteArray();
    }
}
