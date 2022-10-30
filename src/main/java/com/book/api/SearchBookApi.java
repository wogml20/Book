//package com.book.api;
//
////import org.json.simple.JSONArray;
////import org.json.simple.JSONObject;
////import org.json.simple.parser.JSONParser;
////import org.json.simple.parser.ParseException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.io.*;
//import java.net.*;
//import java.nio.ByteBuffer;
//import java.nio.charset.StandardCharsets;
//import java.text.ParseException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class SearchBookApi {
//
//    public static void main(String[] args) {
//        String clientId = "zwvvcSwJFxCgtvbJTnvm";
//        String clientSecret = "qqkxIC0KAZ";
//        String text = null;
//
//        try {
//            text = URLEncoder.encode("그린팩토리", "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException("검섹어 인코딩 실패", e);
//        }
//
//        String apiURL = "https://openapi.naver.com/v1/search/book?query=" + text;
//
//        Map<String, String> requestHeaders = new HashMap<>();
//        requestHeaders.put("X-Naver-Client-Id", clientId);
//        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
//        String responseBody = get(apiURL, requestHeaders);
//
//        System.out.println(responseBody);
//    }
//
//    private static String get(String apiUrl, Map<String, String> requestHeaders) {
//        HttpURLConnection con = connect(apiUrl);
//        try {
//            con.setRequestMethod("GET");
//            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
//                con.setRequestProperty(header.getKey(), header.getValue());
//            }
//
//            int responseCode = con.getResponseCode();
//            if(responseCode == HttpURLConnection.HTTP_OK) {
//                return readBody(con.getInputStream());
//            }else {
//                return readBody(con.getErrorStream());
//            }
//        }catch (IOException e) {
//            throw new RuntimeException("API 요청과 응답 실패", e);
//        }finally {
//            con.disconnect();
//        }
//    }
//
//    private static HttpURLConnection connect(String apiUrl) {
//        try {
//            URL url = new URL(apiUrl);
//            return (HttpURLConnection)url.openConnection();
//        }catch (MalformedURLException e) {
//            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
//        }catch (IOException e) {
//            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl,e);
//        }
//    }
//    private static String readBody(InputStream body) {
//        InputStreamReader streamReader = new InputStreamReader(body);
//
//        try(BufferedReader lineReader = new BufferedReader(streamReader)) {
//            StringBuilder responseBody = new StringBuilder();
//
//            String line;
//            while ((line = lineReader.readLine()) != null) {
//                responseBody.append(line);
//            }
//
//            return responseBody.toString();
//        }catch (IOException e) {
//            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
//        }
//    }
//
//    public static Object apiTest(String query) throws ParseException {
//
//        ByteBuffer buffer = StandardCharsets.UTF_8.encode(query);
//        String encode = StandardCharsets.UTF_8.decode(buffer).toString();
//
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://openapi.naver.com")
//                .path("/v1/search/book.json")
//                .queryParam("query",encode)
//                .queryParam("display",10)
//                .encode()
//                .build()
//                .toUri();
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        RequestEntity<Void> req = RequestEntity
//                .get(uri)
//                .header("X-Naver-Client-Id", "zwvvcSwJFxCgtvbJTnvm")
//                .header("X-Naver-Client-Secret","qqkxIC0KAZ")
//                .build();
//
//        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
//        String r = result.getBody();
//
////        JSONParser parser = new JSONParser();
////        JSONArray values = (JSONArray)parser.parse(r);
////        JSONObject value = (JSONObject)values.get(0);
//
//        return  value;
//    }
//}
