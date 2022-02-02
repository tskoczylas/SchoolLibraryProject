package com.tomsapp.Toms.V2.utils;

import java.net.NetworkInterface;
import java.net.SocketException;

public class HostUtils {

    public static   String getHost(){
        String hostAddress;
        try {
            hostAddress = NetworkInterface.
                    getNetworkInterfaces().
                    nextElement().getInetAddresses().
                    nextElement().getHostAddress();
        } catch (SocketException e) {
            hostAddress="unknown";
        }

        return hostAddress;
    }
}
