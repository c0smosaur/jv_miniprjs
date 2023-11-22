package kr.book.search;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KakaoBookApi {
    private static final String API_KEY = "5efacab4edeb9dd0909827803f0eb87d"; // Rest Key
    private static final String API_BASE_URL = "https://dapi.kakao.com/v3/search/book";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    // 책 검색 메서드
    public static List<Book> searchBooks(String title) throws IOException{
        // url 생성
        HttpUrl.Builder urlBuilder = HttpUrl.parse(API_BASE_URL).newBuilder();
        // 검색 쿼리 추가 // --data-urlencode "query=미움받을 용기" \
        urlBuilder.addQueryParameter("query",title);

        // 요청 객체에 URL 전달, 헤더에 권한 & API_KEY 추가
        // -H "Authorization: KakaoAK ${REST_API_KEY}"
        Request request = new Request.Builder().url(urlBuilder.build())
                .addHeader("Authorization","KakaoAK "+API_KEY)
                .build();

        // 클라이언트 객체로 요청 실행 & 결과 받기
        try (Response response = client.newCall(request).execute()){
            // 정상적인 응답이 아니면 예외 처리
            if (!response.isSuccessful()) throw new IOException("Request failed: "+response);

            // 정상적인 응답이면 응답 body에서 정보 가져오기
            // 받은 response를 JsonObject로 변환
            JsonObject jsonResponse = gson.fromJson(response.body().charStream(),JsonObject.class);
            // document 키를 가진 json 항목 Array로 (책 여러권일 경우)
            JsonArray documents = jsonResponse.getAsJsonArray("documents");

            // 책 정보에서 한 권씩 Book 객체에 정보 등록
            List<Book> books = new ArrayList<>();
            for (JsonElement document:documents){
                JsonObject bookJson = document.getAsJsonObject();
                Book book = new Book(
                        bookJson.get("title").getAsString(),
                        bookJson.get("authors").getAsString(),
                        bookJson.get("publisher").getAsString(),
                        bookJson.get("thumbnail").getAsString());
                books.add(book);
            }
            return books;
        }
    }

}
