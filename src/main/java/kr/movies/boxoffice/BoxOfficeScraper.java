package kr.movies.boxoffice;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoxOfficeScraper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExcelExporter excelExporter = new ExcelExporter();
        PdfExporter pdfExporter = new PdfExporter();

        String url = "https://www.boxofficemojo.com/year/world/";

        while (true){
            System.out.println("---숫자를 입력하세요---");
            System.out.println("1. 2023년도 박스 오피스 엑셀로 저장");
            System.out.println("2. 2023년도 박스 오피스 PDF로 저장");
            System.out.println("3. 종료");
            int num = scanner.nextInt();
            scanner.nextLine();

            if (num == 1) {
                List<BoxOfficeMovie> movies = scrapMovies(url);
                excelExporter.excelExport(movies);
                System.out.println("엑셀 파일을 생성하였습니다.");
            } else if (num==2){
                List<BoxOfficeMovie> movies = scrapMovies(url);
                pdfExporter.pdfExport(movies);
                System.out.println("PDF 파일을 생성하였습니다.");
            } else if (num == 3){
                System.out.println("종료합니다.");
                return;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    public static List<BoxOfficeMovie> scrapMovies(String url) {
        List<BoxOfficeMovie> movies = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).userAgent("Mozilla").get();

            Elements ranks = document.getElementsByClass("a-text-right mojo-header-column mojo-truncate mojo-field-type-rank mojo-sort-column");
            Elements titles = document.getElementsByClass("a-text-left mojo-field-type-release_group");
            Elements w_d_fs = document.getElementsByClass("a-text-right mojo-field-type-money");

            List<String> movieRanks = new ArrayList<>();
            List<String> movieTitles = new ArrayList<>();
            List<String> movieGross = new ArrayList<>();

            for (Element rank : ranks) {
                movieRanks.add((String.valueOf(rank.text())));
            }

            for (Element title : titles) {
                movieTitles.add(title.text());
            }

            for (Element w_d_f : w_d_fs) {
                movieGross.add(String.valueOf(w_d_f.text()));
            }

            for (int i = 0; i < ranks.size(); i++) {
                String rank = movieRanks.get(i);
                String title = movieTitles.get(i);
                String worldWide = movieGross.get(i * 3);
                String domestic = movieGross.get(i * 3 + 1);
                String foreign = movieGross.get(i * 3 + 2);

                BoxOfficeMovie movie = new BoxOfficeMovie(rank, title, worldWide, domestic, foreign);
                movies.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }
}
