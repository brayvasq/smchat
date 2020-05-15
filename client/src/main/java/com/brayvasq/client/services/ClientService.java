/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brayvasq.client.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service used to connect to a chat server
 *
 * @author brayvasq
 */
public class ClientService {

    private Socket channel;
    private OutputStream bufferOut;
    private PrintWriter dataOut;
    private BufferedReader dataIn;
    private DataOutputStream datos;
    private String host;
    private int port;
    private boolean exit;
    private String name;
    
    /**
     * Class constructor
     * 
     * @param host is the server ip or url
     * @param port is the port of the server in which are listen
     * @param name is the name of the client
     */
    public ClientService(String host, int port, String name){
        try {
            this.host = host;
            this.port = port;
            this.exit = false;
            this.channel = new Socket(this.host, this.port);
            this.bufferOut = this.channel.getOutputStream();
            this.datos = new DataOutputStream(this.bufferOut);

            this.dataOut = new PrintWriter(this.bufferOut, true);
            this.dataIn = new BufferedReader(new InputStreamReader(this.channel.getInputStream(), "UTF-8"));
            this.name = name;
        } catch (IOException ex) {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method sends and linsten for messages between the client and the server.
     * 
     * @param message the message to send
     */
    public void send(String message) {
        dataOut.println(message);
        
        new Thread(() -> {
            String cad = "response:";

            while (!(cad.equals("") && cad.equals(null))) {
                try {
                    cad = dataIn.readLine();
                    if (cad.toLowerCase().startsWith("users: ")) {
                        cad = cad.substring(7);
                        
                        String[] names = cad.split("-");
                        int counter = 1;
                        for (String s : names) {
                            System.out.println(counter + ". "+s);
                            counter++;
                        }
                    } else if (cad.toLowerCase().startsWith("response: ")) {
                        cad = cad.substring(10);
                        System.out.print(cad + ": ");
                    } else {
                        System.out.println(cad + "\n");
                    }

                } catch (IOException ex) {
                    Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
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
