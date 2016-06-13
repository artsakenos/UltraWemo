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

/**
 * Constants.
 *
 * @author Andrea Addis
 */
public class UW_Com {

    public static final String USER_AGENT = "Mozilla/5.0"; // In case some day they will require it.
    public static final String WEMO_GetBinaryState_SERVICE = "upnp/control/basicevent1";
    public static final String WEMO_GetBinaryState_SOAPACTION = "\"urn:Belkin:service:basicevent:1#GetBinaryState\"";
    public static final String WEMO_SetBinaryState_SERVICE = "upnp/control/basicevent1";
    public static final String WEMO_SetBinaryState_SOAPACTION = "\"urn:Belkin:service:basicevent:1#SetBinaryState\"";
    public static final String WEMO_GetFriendlyName_SERVICE = "upnp/control/basicevent1";
    public static final String WEMO_GetFriendlyName_SOAPACTION = "\"urn:Belkin:service:basicevent:1#GetFriendlyName\"";
    public static final String WEMO_GetInstantPower_SERVICE = "upnp/control/insight1";
    public static final String WEMO_GetInstantPower_SOAPACTION = "\"urn:Belkin:service:insight:1#GetPower\"";

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

    public static final String getFriendlyName() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n"
                + "	<s:Body>\n"
                + "		<u:GetFriendlyName xmlns:u=\"urn:Belkin:service:basicevent:1\">\n"
                + "			<FriendlyName>1</FriendlyName>\n"
                + "		</u:GetFriendlyName>\n"
                + "	</s:Body>\n"
                + "</s:Envelope>";
    }

    public static final String getInstantPower() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n"
                + "	<s:Body>\n"
                + "		<u:GetPower xmlns:u=\"urn:Belkin:service:insight:1\">\n"
                + "			<InstantPower>1</InstantPower>\n"
                + "		</u:GetPower>\n"
                + "	</s:Body>\n"
                + "</s:Envelope>";
    }

}
