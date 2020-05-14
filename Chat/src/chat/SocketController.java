/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author brayan
 */
public class SocketController implements Runnable{

    private MessengerServer theServer = null;
    private int theID = 0;
    private String theName = "";
    private Thread theThread = null;
    private Socket theSocket = null;
    private PrintWriter theOut  = null;
    private BufferedReader theIn = null;
    
    public SocketController(Socket socket,String name,int id,MessengerServer server){
        this.theServer = server;
        this.theID = id;
        this.theName = name;
        this.theSocket = socket;
        
        try{
            theOut = new PrintWriter(theSocket.getOutputStream(),true);
            theIn = new BufferedReader(new InputStreamReader(theSocket.getInputStream(), "UTF-8"));
        }catch(IOException ex){
            System.out.println("ERROR 1 SOCKET");
        }
        this.theThread = new Thread(this);
        this.theThread.start();
    }

    public void close(){
        try{
            theOut.close();
            theIn.close();
            theSocket.close();
        }catch(IOException ex){
            System.out.println("ERROR 2 CLOSE SOCKETS");
        }
    }
  
    public void writeMessage(String message){
        theOut.println(message);
    }
    
    @Override
    public void run() {
        String readline = "";
        
        writeMessage(theName);
        writeMessage("Session ID = "+theID);
        
        System.out.println(theName);
        System.out.println("Session "+theID+ " Strated");
        
        while(!readline.trim().equalsIgnoreCase("QUIT")){
            try{
                readline = theIn.readLine();
            }catch(IOException ex){
                System.out.println("ERRROR 3 RUN SOCKETS");
            }
            System.out.println("Seesion : "+theID+" COMMAND : "+readline);
            
            if(readline.trim().toUpperCase().startsWith("REGISTER ")){
                readline = readline.substring(9);
                
                theServer.registrar(this, readline);
            }
            else if (readline.trim().toUpperCase().startsWith("SEND ALL ")) {
                readline = readline.substring(9);
                theServer.writeMessage("NMS RESP "+theName);
                theServer.writeMessage(readline);
            }else if(readline.trim().toUpperCase().startsWith("SEND USER ")){
                try{
                readline = readline.substring(10);
                String cadenas[] = readline.split("-");
                theServer.writeSpecific("NMS RESP "+theName, cadenas[0].toUpperCase());
                System.out.println("NMS RESP "+theName +" : " +cadenas[0].toUpperCase());
                theServer.writeSpecific(cadenas[1], cadenas[0].trim().toUpperCase());
                }catch(NullPointerException ex){
                    System.out.println("error server");
                }
            }
            else if (readline.trim().toUpperCase().equals("USERS")){
                String cadena = theServer.devolverUSers();
                System.out.println(cadena);
                theServer.writeMessage(cadena);
            }
            else if(!readline.equals("QUIT")){
                writeMessage("Error Invalid Command");
            }
        }
        //theServer.unregister(theName);
        close();
        
    }

    public String getTheName() {
        return theName;
    }

    public void setTheName(String theName) {
        this.theName = theName;
    }
    
    
    
}
