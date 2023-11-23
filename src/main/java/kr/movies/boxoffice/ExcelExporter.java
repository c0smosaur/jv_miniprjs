package kr.movies.boxoffice;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {
    private Workbook workbook;

    public ExcelExporter(){
        workbook = new XSSFWorkbook();
    }

    public void excelExport(List<BoxOfficeMovie> movieList) {
        Sheet sheet = workbook.createSheet("Worldwide Box Office");

        // 헤더
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("순위");
        headerRow.createCell(1).setCellValue("영화 제목");
        headerRow.createCell(2).setCellValue("전세계 매출");
        headerRow.createCell(3).setCellValue("미국 내 매출");
        headerRow.createCell(4).setCellValue("해외 매출");

        for (int i = 1; i <= movieList.size(); i++) {
            Row movieRow = sheet.createRow(i);
            movieRow.createCell(0).setCellValue(movieList.get(i-1).getRank());
            movieRow.createCell(1).setCellValue(movieList.get(i-1).getTitle());
            movieRow.createCell(2).setCellValue(movieList.get(i-1).getWorldWide());
            movieRow.createCell(3).setCellValue(movieList.get(i-1).getDomestic());
            movieRow.createCell(4).setCellValue(movieList.get(i-1).getForeign());

            try {
                FileOutputStream fos = new FileOutputStream("Worldwide Box Office.xlsx");
                workbook.write(fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
