package com.brayvasq.client;

import com.brayvasq.client.services.ClientService;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        if (args.length > 1) {
            String host = args[0];
            int port = Integer.parseInt(args[1]);

            ClientService client = new ClientService(host, port, "Example");

            Scanner sc = new Scanner(System.in);
            String message = sc.nextLine();
            while (message.toLowerCase() != "quit") {
                client.send(message);
            }
        } else {
            System.out.println("Error: Please specify a port and host");
            System.out.println("The Java Chat - a sockets example");
            System.out.println("");
            System.out.println("Usage:");
            System.out.println("  ./mvnw exec:java -Dexec.args='host port' -q");
            System.out.println("");
            System.out.println("Available Commands:");
            System.out.println("  register <name>       set a name to the connection");
            System.out.println("  users                 return the list of connections"); 
            System.out.println("  send all              send a message to all connections (broadcast)");
            System.out.println("  send user <name>      send a message to an specific user");
            System.out.println("  quit                  kill the connection");
            System.out.println("");
            System.out.println("Example:");
            System.out.println("Use ./mvnw exec:java -Dexec.args='127.0.0.1 25000' -q to stablish a connection");
        }

    }
}
