/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brayvasq.server.services;

import com.brayvasq.server.services.ChatService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author brayvasq
 */
public class SocketService implements Runnable {
    private ChatService chat = null;
    private int id = 0;
    private String name = "";
    private Thread thread = null;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    public SocketService(Socket socket, String name, int id, ChatService chat) {
        this.chat = chat;
        this.id = id;
        this.name = name;
        this.socket = socket;

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        } catch (IOException ex) {
            System.out.println("Error: An error occurs trying to setup the streams");
        }

        this.thread = new Thread(this);
        this.thread.start();
    }

    public void close() {
        try {
            this.out.close();
            this.in.close();
            this.socket.close();
        } catch (IOException ex) {
            System.out.println("Error: An error occurs trying to close the streams");
        }
    }

    public void writeMessage(String message) {
        this.out.println(message);
        System.out.println(message);
    }

    public String readMessage() {
        String readline = "";

        try {
            readline = this.in.readLine();
        } catch (IOException ex) {
            System.out.println("Error: An error ocurred trying to read a message");
        }

        return readline;
    }

    @Override
    public void run() {
        String readline = "";

        writeMessage(this.name);
        writeMessage("Session Id: " + this.id);

        while (!readline.trim().equalsIgnoreCase("QUIT")) {
            readline = readMessage();

            System.out.println("Session: " + this.id + " Command: " + readline);

            if (readline.trim().toUpperCase().startsWith("REGISTER ")) {
                readline = readline.substring(9);
                this.chat.register(this, readline);
            } else if (readline.trim().toUpperCase().startsWith("SEND ALL ")) {
                readline = readline.substring(9);
                this.chat.writeMessage("NMS RESP " + this.name);
                this.chat.writeMessage(readline);
            } else if (readline.trim().toUpperCase().startsWith("SEND USER ")) {
                try {
                    readline = readline.substring(10);
                    String strings[] = readline.split("-");
                    this.chat.writeSpecific("NMS RESP " + this.name, strings[0].toUpperCase());
                    System.out.println("NMS RESP " + this.name + " : " + strings[0].toUpperCase());
                    this.chat.writeSpecific(strings[1], strings[0].trim().toUpperCase());
                } catch (NullPointerException ex) {
                    System.out.println("Error: An error occurs trying to send a message");
                }
            } else if (readline.trim().toUpperCase().equals("USERS")) {
                String cadena = this.chat.users();
                System.out.println(cadena);
                this.chat.writeMessage(cadena);
            } else if (!readline.equals("QUIT")) {
                writeMessage("Error: Invalid command!!");
            }
        }
        
        close();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
