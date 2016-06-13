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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Andrea Addis
 */
public class UW_Device {

    // private String name = "";
    private String ip = "";
    private int port = 0;
    // private String descriptor = ""; // XML Descriptor

    public static final String S_BinaryState = "BinaryState";
    public static final String S_FriendlyName = "FriendlyName";
    public static final String S_InstantPower = "InstantPower";

    /**
     * Create an instance of a WeMo Device from its IP and port.
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

        // Add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", UW_Com.USER_AGENT);
        // con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "text/xml; charset=\"utf-8\"");
        con.setRequestProperty("SOAPACTION", soapaction);
        // con.setReadTimeout(2000);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(body);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("[UW_Device] Requested:" + soapaction + "; Response Code: " + responseCode + ";");

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
    public String getService(String service) throws IOException {
        String output = "";
        switch (service) {
            case S_BinaryState:
                output = request(ip, port, UW_Com.WEMO_GetBinaryState_SERVICE, UW_Com.WEMO_GetBinaryState_SOAPACTION, UW_Com.getBinaryState());
                break;
            case S_FriendlyName:
                output = request(ip, port, UW_Com.WEMO_GetFriendlyName_SERVICE, UW_Com.WEMO_GetFriendlyName_SOAPACTION, UW_Com.getFriendlyName());
                break;
            case S_InstantPower:
                // output = request(ip, port, UW_Com.WEMO_GetFriendlyName_SERVICE, UW_Com.WEMO_GetFriendlyName_SOAPACTION, UW_Com.getInstantPower());
                output = request(ip, port, UW_Com.WEMO_GetInstantPower_SERVICE, UW_Com.WEMO_GetInstantPower_SOAPACTION, UW_Com.getInstantPower());
                System.out.println("INSTANT_POWER_DEBUG:" + output);
                break;
        }
        return getTag(output, service);
    }

    public String setService(String service, String value) throws IOException {
        switch (service) {
            case S_BinaryState:
                return request(ip, port, UW_Com.WEMO_SetBinaryState_SERVICE, UW_Com.WEMO_SetBinaryState_SOAPACTION, UW_Com.setBinaryState(value));
        }
        return "";
    }

    private static String getTag(String value, String tag) {
        String output = "";
        int begin = value.indexOf("<" + tag + ">");
        int end = value.indexOf("</" + tag + ">");
        if (begin < 0 || end < 0) {
            return output;
        }
        begin += tag.length() + 2;
        return value.substring(begin, end);
    }
    //--------------------------------------------------------------------------

}
