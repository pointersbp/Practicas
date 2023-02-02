package org.example;
import java.io.*;
import java.net.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Preguntar por el URL
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter URL: ");
        String url = in.readLine();

        // Conectarse al URL
        Document doc = Jsoup.connect(url).get();

        // Indicar el numero de lineas retornadas
        System.out.println("Numero de lineas: " + doc.html().split("\n").length);

        // Indicar el numero de parrafos retornador
        Elements paragraphs = doc.select("p");
        System.out.println("Cantidad de parrafos: " + paragraphs.size());

        // Indicar el numero de imagenes en el documento HTML
        int imgCount = 0;
        for (Element p : paragraphs) {
            imgCount += p.select("img").size();
        }
        System.out.println("Cantidad de imagenes: " + imgCount);

        // Indicar el numero de parrafos por GET y POST
        Elements forms = doc.select("form");
        int postCount = 0;
        int getCount = 0;
        for (Element form : forms) {
            String method = form.attr("method").toUpperCase();
            if (method.equals("POST")) {
                postCount++;
            } else if (method.equals("GET")) {
                getCount++;
            }
        }
        System.out.println("Numero de formas (Metodo POST): " + postCount);
        System.out.println("Numero de formas (Metodo GET): " + getCount);

        // Mostrar el tipo de input
        for (Element form : forms) {
            Elements inputs = form.select("input");
            for (Element input : inputs) {
                System.out.println("Input type: " + input.attr("type"));
            }
        }

        // Indicar el metodo del formulario
        for (Element form : forms) {
            String method = form.attr("method").toUpperCase();
            if (method.equals("POST")) {
                String action = form.attr("action");
                Connection.Response response = Jsoup.connect(action)
                        .data("subject", "practice1")
                        .header("register", "assigned_registration")
                        .method(Connection.Method.POST)
                        .execute();
                System.out.println("Response: " + response.body());
            }
        }
    }
}