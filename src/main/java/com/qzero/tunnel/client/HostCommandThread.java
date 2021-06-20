package com.qzero.tunnel.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class HostCommandThread extends Thread {

    private String remoteIp;
    private int remotePort;
    private int localPort;

    public HostCommandThread(String remoteIp, int remotePort, int localPort) {
        this.remoteIp = remoteIp;
        this.remotePort = remotePort;
        this.localPort = localPort;
    }

    @Override
    public void run() {
        super.run();

        Scanner scanner;
        OutputStream outputStream;
        try {
            Socket socket=new Socket(remoteIp,remotePort);
            outputStream=socket.getOutputStream();
            scanner=new Scanner(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Failed to connect remote tunnel");
            e.printStackTrace();
            return;
        }

        try {
            outputStream.write(new byte[]{12,34,56});
            outputStream.write("asHost\n".getBytes());

            while (true){
                String commandLine=scanner.nextLine();
                System.out.println(commandLine);
                if(commandLine.startsWith("//")){
                    continue;
                }

                if(commandLine.startsWith("contact")){
                    //Do contact
                    new ContactThread(remoteIp,remotePort,localPort,commandLine.replace("contact ","")).start();
                }
            }

        } catch (IOException e) {
            System.err.println("IO Error");
            e.printStackTrace();
        }



    }
}
