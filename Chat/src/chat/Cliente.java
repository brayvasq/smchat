/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket canalComunicacion = null;
            OutputStream bufferSalida;
            PrintWriter dataOut;
            BufferedReader dataIn;
            DataOutputStream datos;
            canalComunicacion = new Socket("127.0.0.1", 25000);
            bufferSalida = canalComunicacion.getOutputStream();
            datos = new DataOutputStream(bufferSalida);
            
            dataOut = new PrintWriter(bufferSalida,true);
            dataIn = new BufferedReader(new InputStreamReader(canalComunicacion.getInputStream(),"UTF-8"));
            
            String mensaje = "Hola Mundo!\n";
            
            String dato = "";
            //Scanner sc = new Scanner(System.in);
            while(mensaje.toUpperCase()!="QUIT"){
                dato = dataIn.readLine();
                System.out.println(""+dato);
                mensaje = JOptionPane.showInputDialog("MENSAJE");//sc.next();
                dataOut.println(mensaje);
                
            }
            
            /*for (int i = 0; i < 10; i++) {
                datos.writeUTF(mensaje);
            }*/
            
            datos.close();
            bufferSalida.close();
            canalComunicacion.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}