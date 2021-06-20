package com.qzero.tunnel.client;

import java.io.OutputStream;
import java.net.Socket;

public class ContactThread extends Thread {

    private String remoteIp;
    private int remotePort;
    private int localPort;
    private String clientId;

    public ContactThread(String remoteIp, int remotePort, int localPort, String clientId) {
        this.remoteIp = remoteIp;
        this.remotePort = remotePort;
        this.localPort = localPort;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        super.run();

        Socket socketForRemote;
        Socket socketForLocal;
        try {
            socketForRemote=new Socket(remoteIp,remotePort);
            OutputStream remoteOs=socketForRemote.getOutputStream();
            remoteOs.write(new byte[]{12,34,56});
            remoteOs.write(("contact "+clientId+"\n").getBytes());
        }catch (Exception e){
            System.err.println("Failed to connect remote tunnel server for client id "+clientId);
            e.printStackTrace();
            return;
        }

        try {
            socketForLocal=new Socket("127.0.0.1",localPort);
        }catch (Exception e){
            System.err.println("Failed to connect local server for client id "+clientId);
            e.printStackTrace();

            try {
                socketForRemote.close();
            }catch (Exception e1){

            }

            return;
        }

        ClientDisconnectedListener listener= () -> {

            System.out.println(String.format("Route for %s disconnected",clientId));

            try {
                socketForLocal.close();
            }catch (Exception e){

            }

            try {
                socketForRemote.close();
            } catch (Exception e) {

            }

        };

        RouteThread localToRemote=new RouteThread(socketForLocal,socketForRemote,listener);
        RouteThread remoteToLocal=new RouteThread(socketForRemote,socketForLocal,listener);

        localToRemote.start();
        remoteToLocal.start();
        System.out.println("Contacted successfully");
    }
}
