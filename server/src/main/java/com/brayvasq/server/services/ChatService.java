/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brayvasq.server.services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * The Chat server. It executes all chat actions.
 *
 * @author brayvasq
 */
public class ChatService {

    private int sessionId = 1;
    private String name = "";
    private int port = 0;
    private LinkedList<SocketService> clients;

    /**
     * Class constructor
     *
     * @param port the port used to start the application and listen
     * @param name the Chat name
     */
    public ChatService(int port, String name) {
        this.clients = new LinkedList();
        this.port = port;
        this.name = name;
    }

    /**
     * Sends a message to all clients, like a broadcast
     *
     * @param message the messsage to be sended
     */
    public void writeMessage(String message) {
        for (int i = 0; i < this.clients.size(); ++i) {
            this.clients.get(i).writeMessage(message);
        }
    }

    /**
     * Sends a message to an specific client
     *
     * @param message the message to be sended
     * @param name the client name to send the message
     */
    public void writeSpecific(String message, String name) {
        for (SocketService sc : this.clients) {
            if (sc.getName().toLowerCase().equals(name)) {
                sc.writeMessage(message);
            }
        }
    }

    /**
     * Verifies if an names is used by another client
     *
     * @param name the name to check is used
     *
     * @return false if the name is available
     */
    public boolean exists(String name) {
        boolean res = false;
        for (SocketService sc : this.clients) {
            if (sc.getName().equals(name)) {
                res = true;
            }
        }
        return res;
    }

    /**
     * Method used to set a connection name
     *
     * @param s is the channel stablished for the client
     * @param name is the client name
     */
    public void register(SocketService s, String name) {
        if (name.length() > 1) {
            for (SocketService sc : this.clients) {
                if (sc.equals(s)) {
                    if (!exists(name)) {
                        sc.setName(name);
                        System.out.println("Joined: " + name);
                        this.writeMessage("Joined: " + name);
                    } else {
                        s.writeMessage("Error: The name " + name + " exists in the chat, please choose others");
                    }
                }
            }
        } else {
            s.writeMessage("Error: The name cannot be empty");
        }
    }

    /**
     * Return all the users names into a string
     *
     * @return a string with all users names separated by '-'
     */
    public String users() {
        String cadena = "users: ";

        for (SocketService sc : this.clients) {
            System.out.println(sc.getName());
            cadena += sc.getName() + "-";
        }
        return cadena;
    }

    /**
     * Setup the default channels and start to listen connections
     */
    public void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        SocketService client = null;
        boolean quit = false;

        try {
            serverSocket = new ServerSocket(port);
            while (!quit) {
                try {
                    socket = serverSocket.accept();
                    client = new SocketService(socket, name, sessionId, this);
                    clients.add(client);

                    sessionId++;
                } catch (IOException ex) {
                    System.out.println("Error: An error occurs trying to create a socket");
                }
            }
            serverSocket.close();
        } catch (IOException ex) {
            System.out.println("Error: An error occurs trying to start the server");
        }
    }
}
