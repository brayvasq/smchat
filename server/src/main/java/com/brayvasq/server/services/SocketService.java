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
import java.util.Arrays;

/**
 * Class used to stablish an specific channel for a client
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

    /**
     * Class constructor
     *
     * @param socket The channel to stablish a connection
     * @param name The name of the channel
     * @param id The session Id
     * @param chat The server that process the actions
     */
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

    /**
     * Close the streams and channels that are open It should be used when a
     * client stop working or when a client leaves the app.
     */
    public void close() {
        try {
            this.out.close();
            this.in.close();
            this.socket.close();
        } catch (IOException ex) {
            System.out.println("Error: An error occurs trying to close the streams");
        }
    }

    /**
     * Write messages in the Output Stream channel
     *
     * @param message the message to be written
     */
    public void writeMessage(String message) {
        this.out.println(message);
        // System.out.println(message);
    }

    /**
     * Reads messages from the Input Stream
     *
     * @return the message read
     */
    public String readMessage() {
        String readline = "";

        try {
            readline = this.in.readLine();
        } catch (IOException ex) {
            System.out.println("Error: An error ocurred trying to read a message");
        }

        return readline;
    }

    /**
     * run(). Listen from input stream, identify the specific action to execute
     * and call the respective action..
     *
     */
    @Override
    public void run() {
        String readline = "";

        this.writeMessage(this.name);
        this.writeMessage("Session Id: " + this.id);

        try {
            while (!readline.trim().equalsIgnoreCase("quit!")) {

                readline = readMessage();

                if (readline.trim().toLowerCase().startsWith("register: ")) {
                    readline = readline.substring(10);

                    this.chat.register(this, readline);
                } else if (readline.trim().toLowerCase().startsWith("send all: ")) {
                    readline = readline.substring(10);
                    this.chat.writeMessage("response: " + this.name);
                    this.chat.writeMessage(readline);
                } else if (readline.trim().toLowerCase().startsWith("send ")) {
                    try {
                        readline = readline.substring(5);
                        String str[] = readline.split(":");
                        this.chat.writeSpecific("response: " + this.name, str[0].toLowerCase());
                        System.out.println("response: " + this.name + " : " + str[0].toLowerCase());
                        this.chat.writeSpecific(str[1], str[0].trim().toLowerCase());
                    } catch (NullPointerException ex) {
                        System.out.println("Error: An error ocurred trying to send a message");
                    }
                } else if (readline.trim().toLowerCase().equals("users!")) {
                    String users = this.chat.users();
                    System.out.println(users);
                    this.chat.writeSpecific(users, this.getName());
                } else if (!readline.equals("quit!")) {
                    writeMessage("Error: Invalid Command");
                }

            }
        } catch (NullPointerException ex) {
            this.writeMessage("Error: Connection losed for " + this.name);
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
