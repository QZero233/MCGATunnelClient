package com.qzero.tunnel.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();

        try {
            InputStream is=socket.getInputStream();
            OutputStream os=socket.getOutputStream();

            while (true){
                int i=is.read();
                os.write(i);
                System.out.println(i);
            }
        }catch (Exception e){
            System.err.println("Server error");
            e.printStackTrace();
        }
    }
}
