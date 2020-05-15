package com.brayvasq.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brayvasq
 */
public class Client {
    public static void main(String[] args) {
        try {
            Socket channel = null;
            OutputStream bufferOut;
            PrintWriter dataOut;
            BufferedReader dataIn;
            DataOutputStream data;
            channel = new Socket("127.0.0.1", 25000);
            bufferOut = channel.getOutputStream();
            data = new DataOutputStream(bufferOut);
            
            dataOut = new PrintWriter(bufferOut, true);
            dataIn = new BufferedReader(new InputStreamReader(channel.getInputStream(),"UTF-8"));
            
            String mensaje = "Hello world!\n";
            
            String dato = "";
            Scanner sc = new Scanner(System.in);
            while(mensaje.toUpperCase() != "QUIT"){
                dato = dataIn.readLine();
                System.out.println(dato);
                mensaje = sc.nextLine();
                dataOut.println(mensaje);
            }
            
            data.close();
            bufferOut.close();
            channel.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
