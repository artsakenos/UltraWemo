/**
 * Copyright 2016 by Andrea Addis
 *
 * Licensed under the Apache License, Version 2.0; you may not use this file
 * except in compliance with the License. You may obtain a copy of the License
 * at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
 * @author Andrea Addis
 */
public abstract class UW_Discovery {

    /**
     * To complete and test. TODO: substitute the binding with localhost, and
     * perform the search.
     *
     * @throws UnknownHostException
     * @throws IOException
     */
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

    /**
     * This method allow to discover devices with standard settings in network
     * that don't support upnp. Example:
     * UW_Discovery.discovery_raw("192.168.1.%s", 5, 20);
     *
     * @param ipRange The ip range using one Java String format specifier
     * @param rangeMin the range from (e.g., 1)
     * @param rangeMax the range to (e.g., 254)
     */
    public void discovery_raw(final String ipRange, final int rangeMin, final int rangeMax) {

        // TODO: Some value checking.
        final int port = 49153;

        for (int d = rangeMin; d < rangeMax; d++) {
            final int df = d;
            new Thread() {
                @Override
                public void run() {
                    try {
                        UW_Device uw_dev;
                        String ipTry = String.format(ipRange, df);
                        // System.out.println("trying " + ipTry);
                        uw_dev = new UW_Device(ipTry, port);
                        String result = uw_dev.getService(UW_Device.S_FriendlyName);
                        onDiscover(result, ipTry, port);
                    } catch (IOException ex) {
                        // System.err.println("IP (" + df + ") non va bene.");
                    }
                }
            }.start();

        }

    }

    public abstract void onDiscover(String deviceName, String deviceIp, int devicePort);

}
