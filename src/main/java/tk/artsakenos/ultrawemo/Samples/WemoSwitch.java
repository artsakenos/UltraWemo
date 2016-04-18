/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.artsakenos.ultrawemo.Samples;

import java.io.IOException;
import tk.artsakenos.ultrawemo.UW_Device;
import tk.artsakenos.ultrawemo.UW_Discovery;

/**
 *
 * @author Andrea
 */
public class WemoSwitch {

    public static void main(String[] args) throws IOException {
        UW_Discovery uw = new UW_Discovery();
        // uw.discovery();

        String result;
        UW_Device uw_dev = new UW_Device("192.168.1.10", 49153);
        result = uw_dev.getState(UW_Device.S_BinaryState);
        System.out.println(result);

        uw_dev.setState(UW_Device.S_BinaryState, "1");
        result = uw_dev.getState(UW_Device.S_BinaryState);
        System.out.println(result);

        uw_dev.setState(UW_Device.S_BinaryState, "0");
        result = uw_dev.getState(UW_Device.S_BinaryState);
        System.out.println(result);

    }

}
