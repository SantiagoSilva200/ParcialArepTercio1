package org.example;
import java.net.*;
import java.io.*;
import java.util.Arrays;

public class Facade {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(30000);
        while(true){
            Socket client = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            String line = in.readLine();

        if (line == null){
            continue;
        }

        String[] Parts = line.split(" ");
        if (Parts.length <2 ){
            out.println("HTTP/1.1 400 Bad Request\r\n"
                    + "Content-Type: text/html\r\n");
            client.close();
            continue;
        }

        String path = Parts[0];
        if (path.startsWith("/setkv?key")){
            out.println("HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n" + getHtml());

        } else if( path.startsWith("/getkv?key")){
            String[] query = new String[0];
            out.println("HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n" + callBackend(query));

        } else {
            out.println("HTTP/1.1 400 Bad Request\r\n"
                    + "Content-Type: text/html\r\n");
        }
    }
}

    private static String callBackend(String[] query) throws IOException {
        BufferedReader in;
        in = new BufferedReader(new InputStreamReader(new URL ("http:/localhost:40000/setkv?key" + Arrays.toString(query)).openStream()));
        return in.toString();
    }


    private static String getHtml() {
        try{
            InputStream in = Facade.class.getResourceAsStream("/index.html");
            return Arrays.toString(in.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
