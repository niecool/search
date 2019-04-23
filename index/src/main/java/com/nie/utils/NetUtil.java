package com.nie.utils;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author zhaochengye
 * @date 2019-04-23 21:49
 */
public class NetUtil {
    public NetUtil() {
    }

    public static String getLocalIP() {
        String local = null;

        try {
            InetAddress ip = InetAddress.getLocalHost();
            local = ip.getHostAddress();
            if ("127.0.0.1".equals(local)) {
                Socket s = new Socket("www.nie.com", 80);
                ip = s.getLocalAddress();
                s.close();
                local = ip.getHostAddress();
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return local;
    }

    public static String localAddress() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException var2) {
            var2.printStackTrace();
            return "127.0.0.1";
        }
    }
}
