package com.qzero.tunnel.client;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerForTest extends Thread {

    @Override
    public void run() {
        super.run();

        try {
            ServerSocket serverSocket=new ServerSocket(8849);
            Socket socket=serverSocket.accept();
            System.out.println("Server accepted client");
            new ServerThread(socket).start();
        }catch (Exception e){
            System.err.println("Server error");
            e.printStackTrace();
        }

    }
}
