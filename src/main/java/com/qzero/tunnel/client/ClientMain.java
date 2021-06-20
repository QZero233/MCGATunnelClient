package com.qzero.tunnel.client;

import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws Exception {
        //new ServerForTest().start();

        Scanner scanner=new Scanner(System.in);

        System.out.print("Please input server's ip address:");
        String ip=scanner.nextLine();

        System.out.print("Please input server's tunnel port:");
        String tunnelPortString=scanner.nextLine();
        int tunnelPort=Integer.parseInt(tunnelPortString);

        System.out.print("Please input local port:");
        String localPortString=scanner.nextLine();
        int localPort=Integer.parseInt(localPortString);

        new HostCommandThread(ip,tunnelPort,localPort).start();
    }

}
