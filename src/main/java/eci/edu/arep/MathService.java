package main.java.eci.edu.arep;

import java.util.Arrays;

import static spark.Spark.*;

public class MathService {

    public static void main(String... args) {
        configurarServer();
        definirRutas();
    }

    private static void configurarServer() {
        port(getPort());
        staticFiles.location("/public");
    }

    private static void definirRutas() {
        // Ruta para la búsqueda lineal
        get("/linearSearch", (req, res) -> {
            String[] list = parsearLista(req.queryParams("list"));
            int value = parsearValor(req.queryParams("value"));
            int index = LinearSearch(list, value);
            return generateResponse("linearSearch", req.queryParams("list"), String.valueOf(value), index);
        });

        // Ruta para la búsqueda binaria
        get("/binarySearch", (req, res) -> {
            String[] list = parsearLista(req.queryParams("list"));
            int value = parsearValor(req.queryParams("value"));
            int index = BinarySearch(list, value);
            return generateResponse("binarySearch", req.queryParams("list"), String.valueOf(value), index);
        });
    }

    private static String[] parsearLista(String list) {
        return list != null ? list.split(",") : new String[0];
    }

    private static int parsearValor(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static int LinearSearch(String[] list, int value) {
        return Arrays.asList(list).indexOf(String.valueOf(value));
    }

    private static int BinarySearch(String[] list, int value) {
        Arrays.sort(list);
        int left = 0;
        int right = list.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midValue = Integer.parseInt(list[mid]);

            if (midValue == value) return mid;
            if (midValue < value) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    private static String generateResponse(String operation, String inputList, String value, int index) {
        return String.format(
                "{\n \"operation\": \"%s\",\n \"inputList\": \"%s\",\n \"value\": \"%s\",\n \"output\": %d\n}",
                operation, inputList, value, index);
    }

    private static int getPort() {
        String port = System.getenv("PORT");
        return (port != null) ? Integer.parseInt(port) : 4567;
    }
}
