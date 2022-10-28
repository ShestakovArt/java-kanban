package http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private String apiToken;
    private String url = "http://localhost:8078/";

    public KVTaskClient() {
        apiToken = register(url);

    }

    private String register(String url) {
        try{
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "register"))
                    .GET()
                    .build();
            HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return send.body();
        }
        catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }

    public void put(String key, String json){
        try{
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest build = HttpRequest.newBuilder()
                    .uri(URI.create(url + String.format("save/%s?API_TOKEN=%s", key, apiToken)))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        }
        catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }

    public String load(String key){
        try{
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest build = HttpRequest.newBuilder()
                    .uri(URI.create(url + String.format("load/%s?API_TOKEN=%s", key, apiToken)))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }
        catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }
}
