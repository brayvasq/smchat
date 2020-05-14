/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 *
 * @author brayan
 */
public class MessengerServer {

    private int theSessionID = 1;
    private String theName = "";
    private int thePort = 0;
    private LinkedList<SocketController> clients = new LinkedList();
    //private LinkedList<String> nombres = new LinkedList<>();

    public MessengerServer(int port, String name) {
        this.thePort = port;
        this.theName = name;
    }

    /*public void unregister(String name){
        nombres.remove(name);
    }*/
    public void writeMessage(String message) {
        for (int i = 0; i < clients.size(); ++i) {
            clients.get(i).writeMessage(message);
        }
    }

    public void writeSpecific(String message, String name) {
        for (SocketController sc : clients) {
            if (sc.getTheName().toUpperCase().equals(name)) {
                sc.writeMessage(message);
            }
        }
    }

    public boolean existe(String name) {
        boolean res = false;
        for (SocketController sc : this.clients) {
            if (sc.getTheName().equals(name)) {
                res = true;
            }
        }
        return res;
    }

    public void registrar(SocketController s, String name) {
        for (SocketController sc : this.clients) {
            if (sc.equals(s)) {
                if (!existe(name)) {
                    sc.setTheName(name);
                } else {
                    System.out.println("El nombre ya existe");
                    s.writeMessage("El nombre : " + name + "-> ya existe");
                }
            }
        }
    }

    public void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        SocketController client = null;
        boolean quit = false;

        try {
            serverSocket = new ServerSocket(thePort);
            while (!quit) {
                try {
                    socket = serverSocket.accept();
                    client = new SocketController(socket, theName, theSessionID, this);
                    clients.add(client);

                    theSessionID++;
                } catch (IOException ex) {
                    System.out.println("ERROR 1 MESSENGER");
                }
            }
            serverSocket.close();
        } catch (IOException ex) {
            System.out.println("ERROR 2 MESSENGER");
        }
    }

    public String devolverUSers() {
        String cadena = "USR NMS ";
        for (SocketController sc : this.clients) {
            System.out.println(sc.getTheName());
            //theServer.writeSpecific(s,theName.toUpperCase());
            cadena += sc.getTheName() + "-";
        }
        return cadena;
    }

}
