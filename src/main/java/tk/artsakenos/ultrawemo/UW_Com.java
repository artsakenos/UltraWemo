/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.artsakenos.ultrawemo;

/**
 * Constants.
 *
 * @author Andrea
 */
public class UW_Com {

    public static final String USER_AGENT = "Mozilla/5.0"; // In case some day they will require it.
    public static final String WEMO_GetBinaryState_SERVICE = "upnp/control/basicevent1";
    public static final String WEMO_GetBinaryState_SOAPACTION = "\"urn:Belkin:service:basicevent:1#GetBinaryState\"";
    public static final String WEMO_SetBinaryState_SERVICE = "upnp/control/basicevent1";
    public static final String WEMO_SetBinaryState_SOAPACTION = "\"urn:Belkin:service:basicevent:1#SetBinaryState\"";

    public static final String getBinaryState() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n"
                + "	<s:Body>\n"
                + "		<u:GetBinaryState xmlns:u=\"urn:Belkin:service:basicevent:1\">\n"
                + "			<BinaryState>1</BinaryState>\n"
                + "		</u:GetBinaryState>\n"
                + "	</s:Body>\n"
                + "</s:Envelope>";
    }

    public static final String setBinaryState(String value) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n"
                + "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"> \n"
                + "	<s:Body> \n"
                + "		<u:SetBinaryState xmlns:u=\"urn:Belkin:service:basicevent:1\"> \n"
                + "			<BinaryState>" + value + "</BinaryState> \n"
                + "		</u:SetBinaryState> \n"
                + "	</s:Body> \n"
                + "</s:Envelope>";
    }

}
