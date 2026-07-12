package fastairuntime.tools;

import fastairuntime.FastObservation;
import fastairuntime.FastTool;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public final class BrowserTool implements FastTool {

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Override
    public String name() {
        return "browser.fetch";
    }

    @Override
    public FastObservation execute(Map<String, Object> args) {
        String url = (String) args.get("url");
        if (url == null || url.isEmpty()) {
            return new SimpleObservation(false, "url argument is missing.");
        }
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                // Truncate to avoid exploding the context window
                String body = response.body();
                if (body.length() > 2000) {
                    body = body.substring(0, 2000) + "... (truncated)";
                }
                return new SimpleObservation(true, "Fetched URL successfully:\n" + body);
            } else {
                return new SimpleObservation(false, "Failed to fetch URL, HTTP status: " + response.statusCode());
            }
        } catch (Exception e) {
            return new SimpleObservation(false, "Error fetching URL: " + e.getMessage());
        }
    }

    private record SimpleObservation(boolean success, String message) implements FastObservation {}
}
