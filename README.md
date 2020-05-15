# Java Chat
Sockets example. It's a simple chat built in Java and with Java swing Client.

- `Chat/`: Folder that contains the chat server and a CLI client.
  -  The CLI client uses JOptionPane to ask for a message.
- `ClienteSwing/`: Folder that contains the Java Swing GUI client.

**Note**: To run the projects, you should use an IDE (like Netbeans or IntelliJ). However, you can run it using Ant, or compiling and running it manually using `javac` and `java`.

| Lenguaje | Versión              | SO             |
| -------- | -------------------- | -------------- |
| Java     | Java version: 11.0.6 | Ubuntu 18.04.1 |

## Documentation

| Type                       | Link                                                         |
| -------------------------- | ------------------------------------------------------------ |
| Learn Java                 | https://www.learnjavaonline.org/                             |
| Official Docs              | https://docs.oracle.com/en/java/                             |
| Install Maven              | https://linuxize.com/post/how-to-install-apache-maven-on-ubuntu-18-04/ |
| Install Maven              | https://www.vogella.com/tutorials/ApacheMaven/article.html   |
| Example                    | https://mkyong.com/maven/how-to-create-a-java-project-with-maven/ |
| StyleGuide                 | https://www.oracle.com/technetwork/java/codeconventions-135099.html |
| Run multiple classes maven | https://stackoverflow.com/questions/51504549/run-multiple-classes-using-maven |
| Tutorial                   | https://www.codejava.net/java-se/networking/how-to-create-a-chat-console-application-in-java-using-socket |
| Swing layout               | https://www.geeksforgeeks.org/java-swing-simple-user-registration-form/ |

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
The mini-protocol to communicate clients and servers, and to identify what type of message the server receives, is:
- `register:` to add or connect a new client.
- `send all:` to send a message to all clients.
- `send user:` to send a message to a specific client.
- `users!` to obtain the list of clients
- `quit!` to disconnect a client from a server.

## Run project
```bash
# Compile and create a .jar
mvn clean package
mvn exec:java

# Execute project
java -cp target/server-1.0-SNAPSHOT.jar com.brayvasq.server.App

# Execute project using maven (the plugin 'exec-maven-plugin' is needed)
## you can use the project maven wrapper
## For Linux:
./mvnw exec:java -Dexec.args='[command]' -q

### Run server
cd server/
./mvnw clean package
./mvnw exec:java -Dexec.args='[command]' -q
./mvnw exec:java@server

### Run CLI client
cd client/
./mvnw clean package
./mvnw exec:java -Dexec.args='[command]' -q
./mvnw exec:java@client-cli

### Run GUI client
./mvnw clean package
./mvnw exec:java -Dexec.args='[command]' -q
./mvnw exec:java@gui

## For Windows:
mvnw.cmd exec:java -Dexec.args='[command]' -q

### Run server
cd server/
mvnw.cmd clean package
mvnw.cmd exec:java -Dexec.args='[command]' -q
mvnw.cmd exec:java@server

### Run CLI client
cd client/
mvnw.cmd clean package
mvnw.cmd exec:java -Dexec.args='[command]' -q
mvnw.cmd exec:java@client-cli

### Run GUI client
mvnw.cmd clean package
mvnw.cmd exec:java -Dexec.args='[command]' -q
mvnw.cmd exec:java@gui
```

### Usage

```bash
# Linux
./mvnw exec:java@client-cli -q

# Windows
mvnw.cmd exec:java@client-cli -q

The Java Chat - a sockets example

Usage:
  ./mvnw exec:java -Dexec.args='host port' -q

Available Commands:
  register <name>       set a name to the connection
  users                 return the list of connections
  send all              send a message to all connections (broadcast)
  send user <name>      send a message to an specific user
  quit                  kill the connection

Example:
Use ./mvnw exec:java -Dexec.args='127.0.0.1 25000' -q to stablish a connection
```

```bash
# Commands in the REPL
# ./mvnw exec:java@client-cli -Dexec.args='127.0.0.1 25000' -q
register: pepe
send all: message to send
send username: message to send
users!
quit!
```
