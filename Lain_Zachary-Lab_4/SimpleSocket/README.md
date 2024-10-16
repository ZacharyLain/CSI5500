# Simple Socket Server
## Objective
Use sockets for network communication between a client and a server.

## Implementation
The ```SimpleServer``` class starts the server on the specified port and listens for incoming connections from clients.

The ```SimpleClient``` class is used to connect to the server. It connects through the server's IP address and port number.

The ```SimpleServer``` and ```SimpleClient``` classes are able to send bidirectional messages between the client and the server.
The server sends a welcome message and the client responds with a message.