# Java Chat
Sockets example. It's a simple chat built in Java and with Java swing Client.

- `Chat/`: Folder that contains the chat server and a CLI client.
    -  The CLI client uses JOptionPane to ask for a message.
- `ClienteSwing/`: Folder that contains the Java Swing GUI client.

**Note**: To run the projects, you should use an IDE (like Netbeans or IntelliJ). However, you can run it using Ant, or compiling and running it manually using `javac` and `java`.

### Current features
- Run a local chat server.
- Connect to a local chat server using a CLI project.
- Connect to a chat server using the GUI project.
- Set an user name.
- Show users connected.
- Send a message to a specific user.
- Broadcast a message (Send to all users).
- See the chat history.

### Protocol
We establish a mini-protocol to communicate clients and servers. And to identify what type of message the server receives.
- `REGISTER` to add or connect a new client.
- `SEND ALL` to send a message to all clients.
- `SEND USER` to send a message to a specific client.
- `USERS` to obtain the list of clients
- `QUIT` to disconnect a client from a server.

