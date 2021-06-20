package com.qzero.tunnel.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RouteThread extends Thread {

    private Socket source;
    private Socket destination;

    private ClientDisconnectedListener listener;

    public RouteThread(Socket source, Socket destination,ClientDisconnectedListener listener) {
        this.source = source;
        this.destination = destination;
        this.listener=listener;
    }

    @Override
    public void run() {
        super.run();

        try {
            InputStream sourceIs=source.getInputStream();
            OutputStream dstOs=destination.getOutputStream();

            byte[] buf=new byte[102400];
            int len;
            while (true){
                len=sourceIs.read(buf);
                if(len==-1){
                    break;
                }
                dstOs.write(buf,0,len);
            }
        }catch (Exception e){
            System.err.println("Route failed");
            e.printStackTrace();
        }

        listener.onDisconnected();

    }

}
