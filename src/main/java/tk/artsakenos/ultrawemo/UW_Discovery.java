/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.artsakenos.ultrawemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 
 * @author Andrea
 */
public class UW_Discovery {

    public static final void log(String text) {
        System.out.println("LOG: " + text);
    }

    public void discovery() throws UnknownHostException, IOException {
        InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName("239.255.255.250"), 1900);
        MulticastSocket socket = new MulticastSocket(null);
        try {
            // socket.bind(new InetSocketAddress("192.168.1.10", 1901));
            socket.bind(new InetSocketAddress("192.168.1.5", 1901));
            StringBuilder packet = new StringBuilder();
            packet.append("M-SEARCH * HTTP/1.1\r\n");
            packet.append("HOST: 239.255.255.250:1900\r\n");
            packet.append("MAN: \"ssdp:discover\"\r\n");
            packet.append("MX: ").append("10").append("\r\n");
            packet.append("ST: ").append("ssdp:all").append("\r\n").append("\r\n");
            // Or, specific for Belkin Devices
            // packet.append("ST: ").append("urn:Belkin:device:controllee:1").append("\r\n").append("\r\n");
            byte[] data = packet.toString().getBytes();
            socket.send(new DatagramPacket(data, data.length, socketAddress));
        } catch (IOException e) {
            throw e;
        } finally {
            socket.disconnect();
            socket.close();
        }

        MulticastSocket recSocket = new MulticastSocket(null);
        recSocket.bind(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 1901));
        recSocket.setTimeToLive(10);
        recSocket.setSoTimeout(5 * 1000);
        recSocket.joinGroup(InetAddress.getByName("239.255.255.250"));
        boolean inService = true;
        while (inService) { //inService is a variable controlled by a thread to stop the listener
            byte[] buf = new byte[2048];
            DatagramPacket input = new DatagramPacket(buf, buf.length);
            try {
                recSocket.receive(input);
                String originaldata = new String(input.getData());

                System.out.println(originaldata);
            } catch (SocketTimeoutException e) {
                throw e;
            }
        }
        recSocket.disconnect();
        recSocket.close();
    }

}
