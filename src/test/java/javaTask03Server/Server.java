package javaTask03Server;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;

public class Server {

    public static void main(String[] args) {

        int portNum = 4446;
//        int portNum = Integer.parseInt(System.getProperty("ServerPort"));

        HttpServer server = null;

        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(portNum), 0);
        } catch(IOException e){
            e.printStackTrace();
        }

        HttpContext context = server.createContext("/", new EchoHandler());
        HttpContext context2 = server.createContext("/help", new EchoHandler2());
        HttpContext context3 = server.createContext("/date", new EchoHandler3());
        HttpContext context4 = server.createContext("/info", new EchoHandler4());

        server.setExecutor(null);
        server.start();
    }

    static  class EchoHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            StringBuilder builder = new StringBuilder();

            ArrayList<String> headers = new ArrayList<>();
            exchange.getRequestHeaders().values().forEach(s->headers.add(s.toString()));
            exchange.getRequestHeaders().values().forEach(o->System.out.println("header=" + o));

            for (String a: headers){
                if (a.contains("Hello")){
                    builder.append("Hello to, my friend");
                }
            }

            builder.append("GoGoGo");
            byte[] bytes = builder.toString().getBytes();
            exchange.sendResponseHeaders(200, bytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
            exchange.close();
        }
    }
    static  class EchoHandler2 implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            StringBuilder builder = new StringBuilder();

            ArrayList<String> headers = new ArrayList<>();
            exchange.getRequestHeaders().values().forEach(s -> headers.add(s.toString()));
            exchange.getRequestHeaders().values().forEach(o -> System.out.println("header=" + o));

            for (String a : headers) {
                if (a.contains("Hello")) {
                    builder.append("Hello to, my friend");
                }
            }

            builder.append("HELP");
            byte[] bytes = builder.toString().getBytes();
            exchange.sendResponseHeaders(200, bytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
            exchange.close();
        }
    }

    static  class EchoHandler3 implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            StringBuilder builder = new StringBuilder();

            builder.append(("Date: " + new Date().toGMTString() + "\n"));
            byte[] bytes = builder.toString().getBytes();
            exchange.sendResponseHeaders(200, bytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
            exchange.close();
        }
    }

    static  class EchoHandler4 implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            StringBuilder builder = new StringBuilder();

            builder.append("<h1>URI: ").append(exchange.getRequestURI()).append("</h1>");

            Headers headers = exchange.getRequestHeaders();
            for (String header : headers.keySet()) {
                builder.append("<p>").append(header).append("=")
                        .append(headers.getFirst(header)).append("</p>");
            }

            builder.append("Info");
            byte[] bytes = builder.toString().getBytes();
            exchange.sendResponseHeaders(200, bytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
            exchange.close();
        }
    }
}
