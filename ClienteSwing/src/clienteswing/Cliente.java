/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteswing;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.event.ListDataListener;

/**
 *
 * @author brayan
 */
public class Cliente /*implements Runnable*/ {

    private Socket canalComunicacion;
    private OutputStream bufferSalida;
    private PrintWriter dataOut;
    private BufferedReader dataIn;
    private DataOutputStream datos;
    private String host;
    private int port;
    private boolean salir;
    private JTextArea Outputs;
    private JComboBox users;
    private String nombre;
    //private Thread hilo;

    public Cliente(String host, int port, JTextArea Outputs, JComboBox users, String nombre) {
        try {
            this.host = host;
            this.port = port;
            this.salir = false;
            this.canalComunicacion = new Socket(this.host, this.port);
            this.bufferSalida = canalComunicacion.getOutputStream();
            this.datos = new DataOutputStream(bufferSalida);

            this.dataOut = new PrintWriter(bufferSalida, true);
            this.dataIn = new BufferedReader(new InputStreamReader(canalComunicacion.getInputStream(), "UTF-8"));
            this.Outputs = Outputs;
            this.users = users;
            this.nombre = nombre;
            //this.hilo = new Thread(this);
            //this.hilo.start();

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void enviar(String mensaje) {
        dataOut.println(mensaje);
        Thread t = new Thread(() -> {
            String cad = "resp";
            String men = "hola mundo\n";
            while (!(cad.equals("") && cad.equals(null))) {
                try {
                    cad = dataIn.readLine();
                    System.out.println("" + cad);
                    if (cad.toUpperCase().startsWith("USR NMS ")) {
                        cad = cad.substring(8);
                        String[] names = cad.split("-");
                        users.removeAllItems();
                        for (String s : names) {
                            users.addItem(s);
                        }
                        users.addItem("All");
                    } else if (cad.toUpperCase().startsWith("NMS RESP ")) {
                        cad = cad.substring(9);
                        System.out.println("" + cad + " dice : ");
                        Outputs.append(cad + " dice : \n");

                    } else {
                        System.out.println(cad + "\n");
                        Outputs.append(cad + "\n");
                    }

                } catch (IOException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
        t.start();
    }

    public void close() {
        try {
            datos.close();
            bufferSalida.close();
            dataIn.close();
            dataOut.close();
            canalComunicacion.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
