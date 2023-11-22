package kr.excel.resume;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ResumeController {
    private ResumeView view; // 입력화면
    private Workbook workbook; // 엑셀파일
    public ResumeController(){
        view = new ResumeView();
        workbook = new XSSFWorkbook();
    }

    public void createResume(){
        PersonInfo personInfo = view.inputPersonInfo();
        List<Education> educations = view.inputEducationList();
        List<Career> careers = view.inputCareerList();
        String selfIntroduction = view.inputSelfIntroduction();

        createResumeSheet(personInfo,educations,careers);
        createSelfIntroductionSheet(selfIntroduction);

        saveWorkbookToFile();
        System.out.println("이력서 파일이 생성되었습니다.");
    }

    public void createResumeSheet(PersonInfo person,List<Education> educations, List<Career> careers){
        Sheet sheet = workbook.createSheet("이력서");

        // header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("사진");
        headerRow.createCell(1).setCellValue("성명");
        headerRow.createCell(2).setCellValue("이메일");
        headerRow.createCell(3).setCellValue("주소");
        headerRow.createCell(4).setCellValue("전화번호");
        headerRow.createCell(5).setCellValue("생년월일");

        // 데이터 입력
        Row dataRow = sheet.createRow(1);
        String photoFilename = person.getPhoto();
        try (InputStream photoStream = new FileInputStream(photoFilename)){
            BufferedImage originalImage = ImageIO.read(photoStream);

            // 이미지 크기 조절
            int newWidth = (int)(35*2.83465);
            int newHeight = (int)(45*2.83465);
            Image resizedImage = originalImage.getScaledInstance(newWidth,newHeight,Image.SCALE_SMOOTH);
            BufferedImage resizedBufferedImage = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2d = resizedBufferedImage.createGraphics();
            g2d.drawImage(resizedImage,0,0,null);
            g2d.dispose();

            // 조절된 이미지 바이트 배열로 변환
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedBufferedImage,"png",baos);
            byte[] imageBytes = baos.toByteArray();
            int imageIndex = workbook.addPicture(imageBytes,Workbook.PICTURE_TYPE_PNG);

            // Drawing 객체 생성 & 이미지 삽입
            XSSFDrawing drawing = (XSSFDrawing)sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = new XSSFClientAnchor(0,0,0,0,0,1,1,2);
            drawing.createPicture(anchor,imageIndex);

            dataRow.setHeightInPoints(newHeight*72/96);;
            int columnWidth = (int)Math.floor(((float)newWidth/(float)8)*256);
            sheet.setColumnWidth(0,columnWidth);

        } catch (IOException e){
            e.printStackTrace();
        }

        dataRow.createCell(1).setCellValue(person.getName());
        dataRow.createCell(2).setCellValue(person.getEmail());
        dataRow.createCell(3).setCellValue(person.getAddress());
        dataRow.createCell(4).setCellValue(person.getPhoneNumber());
        dataRow.createCell(5).setCellValue(person.getBirthDate());

        // 학력사항 헤더 생성
        int educationHeaderRowNum = 3;
        Row educationHeaderRow = sheet.createRow(educationHeaderRowNum-1); // educationHeaderRow = 2
        educationHeaderRow.createCell(0).setCellValue("졸업년도");
        educationHeaderRow.createCell(1).setCellValue("학교명");
        educationHeaderRow.createCell(2).setCellValue("전공");
        educationHeaderRow.createCell(3).setCellValue("졸업여부");

        // 학력사항 정보 삽입
        int educationRowNum = educationHeaderRowNum; // educationRowNum = 3
        for (Education education:educations){
            Row educationDataRow = sheet.createRow(educationRowNum++); // educationRowNum = 4
            educationDataRow.createCell(0).setCellValue(education.getGraduationYear());
            educationDataRow.createCell(1).setCellValue(education.getSchoolName());
            educationDataRow.createCell(2).setCellValue(education.getMajor());
            educationDataRow.createCell(3).setCellValue(education.getGraduationStatus());
        }

        // 학력사항 헤더 생성
        int careerHeaderRowNum = educationRowNum+1; // careerHeaderRowNum = 4+1 = 5
        Row careerHeaderRow = sheet.createRow(careerHeaderRowNum-1); // 5-1 = 4
        careerHeaderRow.createCell(0).setCellValue("근무기간");
        careerHeaderRow.createCell(1).setCellValue("근무처");
        careerHeaderRow.createCell(2).setCellValue("담당업무");
        careerHeaderRow.createCell(3).setCellValue("근속연수");

        int careerRowNum = careerHeaderRowNum;
        for (Career career : careers){
            Row careerDataRow = sheet.createRow(careerRowNum++);
            careerDataRow.createCell(0).setCellValue(career.getWorkPeriod());
            careerDataRow.createCell(1).setCellValue(career.getCompanyName());
            careerDataRow.createCell(2).setCellValue(career.getJobTitle());
            careerDataRow.createCell(3).setCellValue(career.getEmploymentYears());
        }
    }

    public void createSelfIntroductionSheet(String selfIntroduction){
        Sheet sheet = workbook.createSheet("자기소개서");

        Row dataRow = sheet.createRow(0);
        Cell selfIntroductionCell = dataRow.createCell(0);
        selfIntroductionCell.setCellStyle(getWrapCellStyle());
        selfIntroductionCell.setCellValue(new XSSFRichTextString // 엔터가 많으니까 RichTextString으로
                (selfIntroduction.replaceAll("\n",String.valueOf((char)10)))); // 아스키코드에서 10 = 줄바꿈
    }

    private XSSFCellStyle getWrapCellStyle(){
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        style.setWrapText(true);
        return style;
    }

    public void saveWorkbookToFile(){
        try (FileOutputStream fileOut = new FileOutputStream("이력서.xlsx")){
            workbook.write(fileOut);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ResumeController controller = new ResumeController();
        controller.createResume();
    }
}

