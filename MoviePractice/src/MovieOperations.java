import model.MovieDTO;

import java.util.*;

public class MovieOperations {
    List<MovieDTO> movies;
    Scanner scan = new Scanner(System.in);

    public MovieOperations(){
        this.movies = new ArrayList<>();
    }

    public String start(){
        System.out.println("=======장르별 영화 검색 프로그램 (3조) =======");
        System.out.println(" 1. 영화 입력(I)    2. 영화 출력(P)     3. 영화 장르로 검색(S)     \n4. 영화 제목으로 검색(T)    5. 종료(E)");
        System.out.println("=========================================");
        System.out.println("메뉴입력: ");

        String menu = scan.nextLine();

        return menu;
    }

    public boolean emptyMovies(){
        if (movies.isEmpty()){
            System.out.println("-------------------");
            System.out.println("등록된 영화가 없습니다.");
            System.out.println("-------------------");
        }
        return movies.isEmpty();
    }

    // 영화 개수 받아 개수대로 리스트에 추가
    public void addMovie(int num){
        for (int i=0;i<num;i++){
            System.out.println("영화 제목: ");
            String title = scan.nextLine();
            // 영화 제목이 -1이면 입력 중지
            if (title.equals("-1")){
                break;
            }
            System.out.println("영화 주인공: ");
            String major = scan.nextLine();
            System.out.println("상영시간(분): ");
            int runningTime = Integer.parseInt(scan.nextLine());
            System.out.println("평점: ");
            float rating = Float.parseFloat(scan.nextLine());
            System.out.println("장르:\n(1=드라마, 2=액션, 3=호러) ");
            int genre = Integer.parseInt(scan.nextLine());
            // 장르 1,2,3 외의 숫자 입력 시
            while ((genre>3)||(genre<1)){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                System.out.println("장르:\n(1=드라마, 2=액션, 3=호러) ");
                genre = Integer.parseInt(scan.nextLine());
            }

            MovieDTO movie = new MovieDTO(title,major,runningTime,rating,genre);
            movies.add(movie);
        }
    }

    // 영화 출력
    public void printMovies() {
            // 평점 기준 내림차로 정렬
            List<MovieDTO> sorted = sortByRating(movies);

            // 출력
            for (MovieDTO movie : sorted) {
                System.out.println("영화명: " + movie.getTitle());
                System.out.println("영화 주인공: " + movie.getMajor());
                System.out.println("상영시간: " + movie.getRunningTime());
                System.out.println("평점: " + movie.getRating());
                switch (movie.getGenre()) {
                    case (1):
                        System.out.println("장르: 드라마");
                        break;
                    case (2):
                        System.out.println("장르: 액션");
                        break;
                    case (3):
                        System.out.println("장르: 호러");
                }
                System.out.println("-----------------");
            }
        }

    // 내림차순 정렬
    // swap 메서드
    public List<MovieDTO> sortByRating(List<MovieDTO> movies){
        for(int i=0;i<movies.size();i++){
            for(int j=0;j<i;j++){
                if (movies.get(j).getRating()<movies.get(i).getRating()){
                    MovieDTO temp = movies.get(j);
                    movies.set(j,movies.get(i));
                    movies.set(i,temp);
                }
            }
        }
        return movies;
    }

    // 장르로 검색
    public void searchByGenre(int gen){
        // 1,2,3 외의 숫자 입력시
            while ((gen>3)||(gen<1)){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                gen = Integer.parseInt(scan.nextLine());
            }

            int count = 0;
            for (MovieDTO movie: movies){
                if (movie.getGenre()==gen){
                    System.out.println("영화명: "+movie.getTitle());
                    System.out.println("영화 주인공: "+movie.getMajor());
                    System.out.println("상영시간: "+movie.getRunningTime());
                    System.out.println("평점: "+movie.getRating());
                    switch (movie.getGenre()){
                        case (1):
                            System.out.println("장르: 드라마");
                            break;
                        case (2):
                            System.out.println("장르: 액션");
                            break;
                        case(3):
                            System.out.println("장르: 호러");
                    }
                    count++;
                }
            }

            // 검색 결과가 없을 때
            if (count==0){
                System.out.println("검색 결과가 없습니다.");
            }
        }


    // 제목으로 검색
    public void searchByName(String title){
        int count = 0;
        for (MovieDTO movie: movies) {
            if (movie.getTitle().equals(title)) {
                System.out.println("영화명: " + movie.getTitle());
                System.out.println("영화 주인공: " + movie.getMajor());
                System.out.println("상영시간: " + movie.getRunningTime());
                System.out.println("평점: " + movie.getRating());
                switch (movie.getGenre()) {
                    case (1):
                        System.out.println("장르: 드라마");
                        break;
                    case (2):
                        System.out.println("장르: 액션");
                        break;
                    case (3):
                        System.out.println("장르: 호러");
                }
                count++;
            }
        }
        if (count==0){
            System.out.println("검색 결과가 없습니다.");
        }
    }
}

