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
 *
 * @author brayvasq
 */
public class ChatService {
    private int sessionId = 1;
    private String name = "";
    private int port = 0;
    private LinkedList<SocketService> clients;
    
    public ChatService(int port, String name) {
        this.clients = new LinkedList();
        this.port = port;
        this.name = name;
    }

    public void writeMessage(String message) {
        for (int i = 0; i < this.clients.size(); ++i) {
            this.clients.get(i).writeMessage(message);
        }
    }

    public void writeSpecific(String message, String name) {
        for (SocketService sc : this.clients) {
            if (sc.getName().toUpperCase().equals(name)) {
                sc.writeMessage(message);
            }
        }
    }

    public boolean exists(String name) {
        boolean res = false;
        for (SocketService sc : this.clients) {
            if (sc.getName().equals(name)) {
                res = true;
            }
        }
        return res;
    }

    public void register(SocketService s, String name) {
        for (SocketService sc : this.clients) {
            if (sc.equals(s)) {
                if (!exists(name)) {
                    sc.setName(name);
                } else {
                    s.writeMessage("Error: The name "+name+" are exists in the chat, please choose others");
                }
            }
        }
    }
    
    public String users() {
        String cadena = "USR NMS ";
        
        for (SocketService sc : this.clients) {
            System.out.println(sc.getName());
            cadena += sc.getName()+ "-";
        }
        return cadena;
    }

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
