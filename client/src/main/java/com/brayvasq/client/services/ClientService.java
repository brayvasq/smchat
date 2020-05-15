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
import javax.swing.JComboBox;
import javax.swing.JTextArea;

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
    
    public void send(String message) {
        dataOut.println(message);
        Thread t = new Thread(() -> {
            String cad = "resp";
            String men = "hola mundo\n";
            while (!(cad.equals("") && cad.equals(null))) {
                try {
                    cad = dataIn.readLine();
                    System.out.println("" + cad);
                    if (cad.toUpperCase().startsWith("users ")) {
                        cad = cad.substring(8);
                        String[] names = cad.split("-");
                        for (String s : names) {
                            System.out.println(s);
                        }
                    } else if (cad.toUpperCase().startsWith("response ")) {
                        cad = cad.substring(9);
                        System.out.println("" + cad + " dice : ");
                    } else {
                        System.out.println(cad + "\n");
                    }

                } catch (IOException ex) {
                    Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
    }
}
