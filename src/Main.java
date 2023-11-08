import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server start on localhost, port 8080...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String request = reader.readLine();
            String path = request.split(" ")[1];

            String content = "";
            String contentType = "text/html";
            if (path.equals("/hello")) {
                content = "<h1>Hello, World!</h1>";
            } else if (path.equals("/greeting")) {
                content = "<h1>Welcome to our website!</h1>";
            } else if (path.equals("/registration")) {
                content = "<h1>Registration Form</h1>"
                        + "<form action=\"/register\" method=\"post\">"
                        + "Name: <input type=\"text\" name=\"name\"><br>"
                        + "Email: <input type=\"email\" name=\"email\"><br>"
                        + "<input type=\"submit\" value=\"Register\">"
                        + "</form>";
            } else {
                content = "Page not found.";
                contentType = "text/plain";
            }

            String responseHeader = "HTTP/1.1 200 OK\r\n";
            responseHeader += "Content-Type: " + contentType + "\r\n\r\n";
            OutputStream outputStream = clientSocket.getOutputStream();

            outputStream.write(responseHeader.getBytes());
            outputStream.write(content.getBytes());

            clientSocket.close();
        }
    }
}

