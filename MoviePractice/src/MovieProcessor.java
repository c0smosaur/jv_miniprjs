import model.MovieDTO;

import java.util.*;

public class MovieProcessor {
    public static void main(String[] args) {
        MovieOperations movieOp= new MovieOperations();

        while (true){
            // 초기 화면
            String menu = movieOp.start();

            // 영화 입력
            if (menu.equals("I")||menu.equals("i")){
                System.out.println("추가할 영화의 수: ");
                int num = Integer.parseInt(movieOp.scan.nextLine());
                movieOp.addMovie(num);
            }

            // 영화 출력
            else if (menu.equals("P")||menu.equals("p")) {
                if (movieOp.emptyMovies()) {} else {
                    movieOp.printMovies();
                }
            }

            // 영화 장르로 검색
            else if (menu.equals("S")||menu.equals("s")) {
                if (movieOp.emptyMovies()) {} else {
                    System.out.println("장르를 입력하세요.\n1=드라마, 2=액션, 3=호러");
                    int gen = Integer.parseInt(movieOp.scan.nextLine());
                    movieOp.searchByGenre(gen);
                }
            }

            else if (menu.equals("T")||menu.equals("t")) {
                if (movieOp.emptyMovies()) {} else {
                    System.out.println("제목을 입력하세요: ");
                    String title = movieOp.scan.nextLine();
                    movieOp.searchByName(title);
                }
            }

            // 종료
            else if ((menu.equals("E"))||(menu.equals("-1"))||menu.equals("e")){
                return;
            }

            // 잘못된 입력
            else {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요. ");
            }
        }
    }
}
