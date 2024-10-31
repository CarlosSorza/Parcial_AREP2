package eci.edu.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static spark.Spark.*;

public class ServiceProxy {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String[] SERVERS = {
            "ec2-3-84-153-166.compute-1.amazonaws.com",
            "ec2-54-147-190-178.compute-1.amazonaws.com"
    };
    private static int currentServerIndex = 0;

    public static void main(String... args) {
        setupServer();
        setupRoutes();
    }

    private static void setupServer() {
        port(getPort());
        staticFiles.location("/public");
    }

    private static void setupRoutes() {
        get("/proxy", (req, res) -> {
            String list = req.queryParams("list");
            String value = req.queryParams("value");
            String type = req.queryParams("type");

            if (isValidSearchType(type)) {
                return forwardRequestToServer(type, list, value);
            } else {
                res.status(400);
                return "Invalid search type.";
            }
        });
    }

    private static boolean isValidSearchType(String type) {
        return "linearsearch".equals(type) || "binarysearch".equals(type);
    }

    private static String forwardRequestToServer(String operation, String list, String value) throws IOException {
        String serverUrl = SERVERS[getNextServerIndex()] + "/" + operation + "?list=" + list + "&value=" + value;
        return fetchServerResponse(serverUrl);
    }

    private static int getNextServerIndex() {
        currentServerIndex = (currentServerIndex + 1) % SERVERS.length;
        return currentServerIndex;
    }

    private static String fetchServerResponse(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } else {
            return "Request failed.";
        }
    }

    private static int getPort() {
        String port = System.getenv("PORT");
        return (port != null) ? Integer.parseInt(port) : 4568;
    }
}
