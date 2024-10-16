import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler extends Thread {
  private final Socket clientSocket;
  private String clientName;

  public ClientHandler(Socket socket) {
    this.clientSocket = socket;
  }

  public void run() {
    try (
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
    ) {
      // Receive the client's name as the first message
      clientName = in.readLine();
      System.out.println("Client name: " + clientName);
      out.println("Welcome " + clientName + " to the server!");

      String clientMessage;

      // Communication loop with the client
      while ((clientMessage = in.readLine()) != null) {
        System.out.println(clientName + ": " + clientMessage);

        if (clientMessage.equalsIgnoreCase("bye")) {
          out.println("Goodbye " + clientName + "!");
          break;
        }

        out.println("Server received: " + clientMessage);
      }
    } catch (IOException e) {
      System.out.println("Client connection error: " + e.getMessage());
    } finally {
      try {
        System.out.println("Connection with " + clientName + " closed.");
        clientSocket.close();
      } catch (IOException e) {
        System.out.println("Error closing client socket: " + e.getMessage());
      }
    }
  }
}