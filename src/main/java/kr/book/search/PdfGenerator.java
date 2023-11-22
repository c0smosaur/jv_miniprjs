package kr.book.search;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import static org.apache.poi.ss.util.CellUtil.createCell;

public class PdfGenerator {
    public static void generateBookListPdf(List<Book> books, String filename) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(filename);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.setFontSize(12);

        // 왜 처음에 null로 설정해주는 것인지?
        PdfFont font = null;
        try {
            font = PdfFontFactory.createFont(
                    "KyoboHandwriting2020pdy.ttf","Identity-H",true);
        } catch (IOException er){
            er.printStackTrace();
        }

        document.setFont(font);
        // 타이틀 추가
        Paragraph titleParagraph = new Paragraph("도서 목록");
        titleParagraph.setFontSize(24);
        titleParagraph.setTextAlignment(TextAlignment.CENTER);
        titleParagraph.setBold();
        document.add(titleParagraph);

        // 도서 정보 테이블 생성
        float[] columnWidth = new float[]{2,2,2,2};
        Table table = new Table(UnitValue.createPointArray(columnWidth));
        table.setWidth(UnitValue.createPercentValue(100));
        table.setMarginTop(20);

        // 테이블 헤더 추가
//        table.addHeaderCell(createCell("제목",true)); // 오류
        table.addHeaderCell(new Cell().add(new Paragraph(("제목")).setFont(font)));
        table.addHeaderCell(new Cell().add(new Paragraph(("저자")).setFont(font)));
        table.addHeaderCell(new Cell().add(new Paragraph(("출판사")).setFont(font)));
        table.addHeaderCell(new Cell().add(new Paragraph(("이미지")).setFont(font)));

        // 도서정보를 테이블에 추가
        for (Book book:books){
            table.addCell(new Cell().add(new Paragraph(book.getTitle()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(book.getAuthors()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(book.getPublisher()).setFont(font)));

            // 이미지 추가
            try{
                ImageData imageData = ImageDataFactory.create(book.getThumbnail());
                Image image = new Image(imageData);
                image.setAutoScale(true);
                table.addCell(new Cell().add(image).setPadding(5));
            } catch (MalformedURLException e) {
                table.addCell(new Cell().add(new Paragraph("이미지 로딩 실패").setFont(font)));
            }
        }
        document.add(table);
        document.close();





    }
}
