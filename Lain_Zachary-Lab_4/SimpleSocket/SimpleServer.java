package SimpleSocket;

import java.io.*;
import java.net.*;

public class SimpleServer {
  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(8080);
    System.out.println("Server started. Waiting for clients...");

    Socket clientSocket = serverSocket.accept();
    System.out.println("Client connected: " + clientSocket.getInetAddress());

    // Send message to client
    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
    out.println("Welcome to the server!");

    // Receive message from client
    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    String clientMessage = in.readLine();
    System.out.println("Client says: " + clientMessage);

    // Close connections
    in.close();
    out.close();
    clientSocket.close();
    serverSocket.close();
  }
}
