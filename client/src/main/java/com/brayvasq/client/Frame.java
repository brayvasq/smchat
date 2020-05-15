/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brayvasq.client;

import com.brayvasq.client.services.ClientService;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author brayvasq
 */
public class Frame extends JFrame {

    private JButton btnLogin;
    private JButton btnSend;
    private JButton btnUpdate;
    private JButton btnQuit;
    private JComboBox<String> cmbxUsers;
    private JLabel lblServer;
    private JLabel lblPort;
    private JLabel lblUserName;
    private JLabel lblUsers;
    private JLabel lblMessage;
    private JLabel lblConversation;
    private JTextField txfMesagge;
    private JTextField txfPort;
    private JTextField txfServer;
    private JTextField txfUserName;
    private JTextArea txaConversation;

    private ClientService client;

    public Frame() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        this.txfUserName = new JTextField();
        this.txfServer = new JTextField();
        this.txfPort = new JTextField();
        this.txfMesagge = new JTextField();

        this.txaConversation = new JTextArea();

        this.lblConversation = new JLabel("Conversation");
        this.lblMessage = new JLabel("Message");
        this.lblPort = new JLabel("Port");
        this.lblUserName = new JLabel("User name");
        this.lblServer = new JLabel("Server ip");
        this.lblUsers = new JLabel("Users");

        this.btnLogin = new JButton("Login");
        this.btnQuit = new JButton("Quit!");
        this.btnSend = new JButton("Send");
        this.btnUpdate = new JButton("Update list");

        this.cmbxUsers = new JComboBox<>();

        this.configGUI();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLocationRelativeTo(null);

        this.setResizable(false);

        this.setVisible(true);
    }

    public void configGUI() {
        this.setBounds(300, 90, 900, 600);

        Container container = getContentPane();
        container.setLayout(null);
        
        JLabel title = new JLabel("Chat"); 
        title.setFont(new Font("Arial", Font.PLAIN, 30)); 
        title.setSize(300, 30); 
        title.setLocation(300, 30); 
        container.add(title); 
        
        this.lblServer.setSize(100, 20);
        this.lblServer.setLocation(100, 100);
        container.add(this.lblServer);
        
        this.txfServer.setSize(190, 20);
        this.txfServer.setLocation(200, 100);
        container.add(this.txfServer);
        
        this.lblPort.setSize(100, 20);
        this.lblPort.setLocation(100, 150);
        container.add(this.lblPort);
        
        this.txfPort.setSize(190, 20);
        this.txfPort.setLocation(200, 150);
        container.add(this.txfPort);
        
        this.lblUserName.setSize(100,20);
        this.lblUserName.setLocation(100, 200);
        container.add(this.lblUserName);
        
        this.txfUserName.setSize(190, 20);
        this.txfUserName.setLocation(200, 200);
        container.add(this.txfUserName);
        
        this.btnLogin.setSize(190, 20);
        this.btnLogin.setLocation(200, 250);
        container.add(this.btnLogin);
        
        this.lblUsers.setSize(100, 20);
        this.lblUsers.setLocation(100, 300);
        container.add(this.lblUsers);
        
        this.cmbxUsers.setSize(190, 20);
        this.cmbxUsers.setLocation(200, 300);
        container.add(this.cmbxUsers);
        
        this.btnUpdate.setSize(190, 20);
        this.btnUpdate.setLocation(200, 350);
        container.add(this.btnUpdate);
        
        this.lblMessage.setSize(100,20);
        this.lblMessage.setLocation(100, 400);
        container.add(this.lblMessage);
        
        this.txfMesagge.setSize(190, 20);
        this.txfMesagge.setLocation(200, 400);
        container.add(this.txfMesagge);
        
        this.btnSend.setSize(190, 20);
        this.btnSend.setLocation(200, 450);
        container.add(this.btnSend);
        
        this.btnQuit.setSize(190, 20);
        this.btnQuit.setLocation(200, 500);
        container.add(this.btnQuit);
        
        this.lblConversation.setSize(500, 25); 
        this.lblConversation.setLocation(100, 500); 
        container.add(this.lblConversation); 
   
        this.txaConversation.setSize(300, 400); 
        this.txaConversation.setLocation(580, 100); 
        this.txaConversation.setLineWrap(true); 
        container.add(txaConversation);
    }

    public static void main(String[] args) {
        new Frame();
    }
}
