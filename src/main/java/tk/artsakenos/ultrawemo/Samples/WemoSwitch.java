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
package tk.artsakenos.ultrawemo.Samples;

import java.io.IOException;
import tk.artsakenos.ultrawemo.UW_Device;
import tk.artsakenos.ultrawemo.UW_Discovery;

/**
 * Sample code.
 *
 * @author Andrea Addis
 */
public class WemoSwitch {

    public static void main(String[] args) throws IOException {

        // Object for device discovery with discover event implementation.
        UW_Discovery uwd = new UW_Discovery() {

            @Override
            public void onDiscover(String deviceName, String deviceIp, int devicePort) {
                System.out.println("Found Device: '" + deviceName + "' with address " + deviceIp + "#" + devicePort);
            }
        };

        // Let's disover devices within the range 192.168.1.5 to 192.168.1.20
        uwd.discovery_raw("192.168.1.%s", 5, 20);

        UW_Device uw_dev = new UW_Device("192.168.1.12", 49153);
        String state = uw_dev.getService(UW_Device.S_BinaryState);
        System.out.println("Device (on?):" + state);

        // Retrieve Device Name
        String name = uw_dev.getService(UW_Device.S_FriendlyName);
        System.out.println("DeviceName:" + name);

        // Retrieve Device Power Consumption (if device is an insight)
        // Not working anymore with the new firmware. Soon the patch.
        String power = uw_dev.getService(UW_Device.S_InstantPower);
        System.out.println("Instant Power:" + power + " mW");

        // Turn on the device and read its state.
        uw_dev.setService(UW_Device.S_BinaryState, "1");
        state = uw_dev.getService(UW_Device.S_BinaryState);
        System.out.println("Status(on):" + state);

        // Turn off the device and read its state.
        uw_dev.setService(UW_Device.S_BinaryState, "0");
        state = uw_dev.getService(UW_Device.S_BinaryState);
        System.out.println("Status(off):" + state);
    }

}
