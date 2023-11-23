package kr.movies.boxoffice;

import com.itextpdf.kernel.font.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;

import java.io.IOException;
import java.util.List;

public class PdfExporter {

    public PdfExporter(){}

    public void pdfExport(List<BoxOfficeMovie> movieList) {
        try {
            PdfWriter writer = new PdfWriter("Worldwide Box Office.pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont font = PdfFontFactory.createFont("KyoboHandwriting2020pdy.ttf", "Identity-H", true);
            document.setFont(font);
            document.setFontSize(10);

            Paragraph titleParagraph = new Paragraph("Worldwide Box Office");
            titleParagraph.setFontSize(16);
            titleParagraph.setTextAlignment(TextAlignment.LEFT);
            titleParagraph.setBold();
            document.add(titleParagraph);

            // 영화 정보 테이블 생성
            float[] columnWidth = new float[]{2, 3, 2, 2, 2};
            Table table = new Table(UnitValue.createPointArray(columnWidth));
            table.setWidth(UnitValue.createPercentValue(100));
            table.setMarginTop(20);

            table.addHeaderCell(new Cell().add(new Paragraph("순위").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("영화 제목").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("전세계 매출").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("미국 매출").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("해외 매출").setFont(font)));

            for (BoxOfficeMovie movie : movieList) {
                table.addCell(new Paragraph(movie.getRank()).setFont(font));
                table.addCell(new Paragraph(movie.getTitle()).setFont(font));
                table.addCell(new Paragraph(movie.getWorldWide()).setFont(font));
                table.addCell(new Paragraph(movie.getDomestic()).setFont(font));
                table.addCell(new Paragraph(movie.getForeign()).setFont(font));
            }
            document.add(table);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
