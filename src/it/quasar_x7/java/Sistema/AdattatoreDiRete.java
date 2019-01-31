/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.quasar_x7.java.Sistema;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 *
 * @author ninja
 */
public class AdattatoreDiRete {
    
    public static String[] indizizziIP(){
        ArrayList<String> lista = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> nis =
                    java.net.NetworkInterface.getNetworkInterfaces();

            while(nis.hasMoreElements()){
                NetworkInterface ni  = nis.nextElement();

                Enumeration<InetAddress> indirizziIP = ni.getInetAddresses();
                while(indirizziIP.hasMoreElements()){
                    InetAddress IP = indirizziIP.nextElement();
                    lista.add(IP.getHostName());
                }
            }

        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return lista.toArray(new String[lista.size()]);
    }
    
}
