/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.artsakenos.ultrawemo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Andrea
 */
public class UW_Device {

    private String name = "";
    private String ip = "";
    private int port = 0;
    private String descriptor = ""; // XML Descriptor

    public static final String S_BinaryState = "BinaryState";

    /**
     * Create an instance of a WeMo Device from its ip and port.
     *
     * @param ip
     * @param port
     */
    public UW_Device(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    //--------------------------------------------------------------------------
    private String request(final String deviceName, final int deviceIp, final String service, final String soapaction, final String body) throws MalformedURLException, IOException {

        String url = "http://" + deviceName + ":" + deviceIp + "/" + service;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", UW_Com.USER_AGENT);
        // con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "text/xml; charset=\"utf-8\"");
        con.setRequestProperty("SOAPACTION", soapaction);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(body);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        UW_Discovery.log("Requested:" + soapaction + "; Response Code: " + responseCode + ";");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    //--------------------------------------------------------------------------
    public String getState(String service) throws IOException {
        switch (service) {
            case S_BinaryState:
                return request(ip, port, UW_Com.WEMO_GetBinaryState_SERVICE, UW_Com.WEMO_GetBinaryState_SOAPACTION, UW_Com.getBinaryState());
        }
        return "";
    }

    public String setState(String service, String value) throws IOException {
        switch (service) {
            case S_BinaryState:
                return request(ip, port, UW_Com.WEMO_SetBinaryState_SERVICE, UW_Com.WEMO_SetBinaryState_SOAPACTION, UW_Com.setBinaryState(value));
        }
        return "";
    }
    //--------------------------------------------------------------------------

}
