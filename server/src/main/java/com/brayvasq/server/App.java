package com.brayvasq.server;

import com.brayvasq.server.services.ChatService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        ChatService server = new ChatService(25000, "Servidorcito!");
        server.run();
    }
}
