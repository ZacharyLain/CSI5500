import java.io.*;
import java.net.*;

public class Client
{
  private static String name;

  public static void main(String[] args)
  {
    String host = "localhost";
    int port = 8080;

    try (Socket socket = new Socket(host, port))
    {
      // Get and set the client name
      setName();

      System.out.println("Connected to server.");

      // Send the client's name to the server
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      out.println(name); // Send the name as the first message

      // Receive message from server
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String message = in.readLine();
      System.out.println("Server says: " + message);

      // Keep sending messages to the server until "bye" is sent
      BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
      String userMsg;
      do {
        // Get user input
        System.out.print("You: ");
        userMsg = userInput.readLine();

        // Send user input to server
        out.println(userMsg);

      } while (!userMsg.equalsIgnoreCase("bye"));

      System.out.println("Disconnected...");

      // Close connections
      in.close();
      out.close();
    }
    catch (IOException e)
    {
      System.out.println("Could not connect to server on port " + port + ".\nExiting...");
    }
  }

  public static void setName() throws IOException {
    // Get user name
    System.out.print("Enter your name: ");
    BufferedReader nameInput = new BufferedReader(new InputStreamReader(System.in));
    name = nameInput.readLine();

    if (name == null || name.isEmpty()) {
      name = "Anonymous"; // Default if no name provided
    }
  }
}
