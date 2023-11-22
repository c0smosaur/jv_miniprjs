package kr.book.search;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class BookSearchMain {
    public static void main(String[] args) {
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("도서제목을 입력하세요.");
            String bookTitle = scanner.nextLine();
            List<Book> books = KakaoBookApi.searchBooks(bookTitle);

            if (books.isEmpty()){
                System.out.println("검색 결과 없음");
            } else {
                String fileName = "도서목록.pdf";
                PdfGenerator.generateBookListPdf(books, fileName);
                System.out.println(fileName+"파일이 생성되었습니다.");
            }
        } catch (IOException e){
            System.out.println("오류 발생: "+e.getMessage());;
        }
    }
}
