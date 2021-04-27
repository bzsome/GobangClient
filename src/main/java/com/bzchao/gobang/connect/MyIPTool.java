package com.bzchao.gobang.connect;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class MyIPTool {

    /**
     * 获取本机所有IP
     */
    public static List<String> getAllLocalHostIP() {
        ArrayList<String> res = new ArrayList<>();
        try {
            Enumeration<?> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                Enumeration<?> nii = ni.getInetAddresses();
                while (nii.hasMoreElements()) {
                    InetAddress ip = (InetAddress) nii.nextElement();
                    if (!ip.getHostAddress().contains(":")) {
                        res.add(ip.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return res;
    }

}
