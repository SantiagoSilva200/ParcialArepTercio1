package org.example;

import java.net.*;
import java.io.*;
import java.util.HashMap;

public class Backend {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(40000);
        while (true) {
            Socket Client = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(Client.getInputStream()));
            PrintWriter out = new PrintWriter(Client.getOutputStream(), true);
            String line = in.readLine();

            if (line == null) {
                Client.close();
                continue;
            }

            String[] Parts = line.split(" ");
            if (Parts.length < 2) {
                out.println("HTTP/1.1 400 Bad Request\r\n"
                        + "Content-Type: text/html\r\n");
                Client.close();
            }

            String path = Parts[0];
            if (!path.contains("/setkv?key")) {
                out.println("HTTP/1.1 404 Not Found \r\n"
                        + "Content-Type: text/html\r\n");
                Client.close();
            }

            String query = path.split(" ", 2)[1];
            String x = handleReq(query);
        }
    }

    private static <k, v> String handleReq(String query) {
        try {
            query = String.valueOf(new HashMap<k,v>());


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return query;
    }
}
