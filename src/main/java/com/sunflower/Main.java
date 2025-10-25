package com.sunflower;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class Main {
    private static Properties properties;
    public static void main(String[] args) throws Exception {

//        URL url = new URL("http://localhost:8080/");

//        readFileContent();
//        urlContentCall();

        server();
    }


    private static void readFileContent() {
        try{
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            String file = loader.getResource("hello.txt").getFile();
            URL url = new URL(file);
            try(InputStream is = url.openStream()){
                int data = is.read();
                while (data != -1) {
                    System.out.print((char)data);
                    data = is.read();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void urlContentCall() throws Exception {
        URL url = new URL("https://www.google.com/search?q=Java&num=10");
        System.out.println(url.getPath());
        System.out.println(url.getFile());

        URLConnection conn = url.openConnection();
        conn.setRequestProperty("Accept", "text/html");
        conn.setRequestProperty("Connection", "close");
        conn.setRequestProperty("Accept-Language", "en-US");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        try(InputStream is = conn.getInputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.print(line);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private static void server() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        properties = Prop.getProperties(classLoader, "app.properties");
        int port = Prop.getInt(properties, "port");
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/something", new PostHandler());
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static class PostHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println(exchange.getRequestURI());
            System.out.println(exchange.getHttpContext().getPath());
            try(InputStream is = exchange.getRequestBody();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                OutputStream os = exchange.getResponseBody()) {
                System.out.println("Received as body: ");
                in.lines().forEach(l -> System.out.println(" " + l));

                String confirm = "Got it! Thanks";
                exchange.sendResponseHeaders(200, confirm.length());
                os.write(confirm.getBytes());
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

