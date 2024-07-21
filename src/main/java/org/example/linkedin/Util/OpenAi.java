package org.example.linkedin.Util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenAi {

    public static void main(String[] args) {
        String text = "hello";
        String x = generateTitle(text);
        System.out.println(x);

    }

    public static String generateContent(String title) {
        String prompt = "I want you to generate content for my linkedin about this and don't include title but just the body ";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://chatgpt-42.p.rapidapi.com/conversationgpt4-2"))
                .header("x-rapidapi-key", "ab47e39d37msh3b501e4250fc2d7p1928b3jsnf2e6e11aa2b8")
                .header("x-rapidapi-host", "chatgpt-42.p.rapidapi.com")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"messages\":[{\"role\":\"user\",\"content\":\""+prompt + title+"\"}],\"system_prompt\":\"\",\"temperature\":0.9,\"top_k\":5,\"top_p\":0.9,\"max_tokens\":100,\"web_access\":false}"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    public static String generateTitle(String title) {
        String prompt = "I want you to generate Title about this: ";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://chatgpt-42.p.rapidapi.com/conversationgpt4-2"))
                .header("x-rapidapi-key", "ab47e39d37msh3b501e4250fc2d7p1928b3jsnf2e6e11aa2b8")
                .header("x-rapidapi-host", "chatgpt-42.p.rapidapi.com")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"messages\":[{\"role\":\"user\",\"content\":\""+prompt + title+"\"}],\"system_prompt\":\"\",\"temperature\":0.9,\"top_k\":5,\"top_p\":0.9,\"max_tokens\":20,\"web_access\":false}"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }
}
