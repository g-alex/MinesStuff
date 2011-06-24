/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.garnier.btrvnc;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * VNCManager is a static class which provides methods to collect VNC-capable
 * machines via virsh.
 *
 * @author agarnier
 */
public class VNCManager {

    private static List<MachineVNC> VNCLIST = new ArrayList<MachineVNC>();

    /**
     * Collect data for VNC-displayable machines.
     *
     * @throws IOException
     */
    public static void updateVNVLIST() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("virsh list");
        InputStream inputStream = process.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String domain;
        bufferedReader.readLine();
        bufferedReader.readLine();
        while ((domain = bufferedReader.readLine()) != null) {
            String[] attributes = domain.trim().split(" ", 3);
            if (attributes.length == 3) {
                VNCManager.addDisplay(runtime, attributes);
            } // if
        } // while
        bufferedReader.close();
        inputStreamReader.close();
        bufferedInputStream.close();
        inputStream.close();
    } // static void setVNCLIST

    /**
     * Get the IP for a VNC-displayable machine.
     * 
     * @param machineName Machine name.
     * @return Given machine IP, null otherwise.
     */
    public static String getIp(String machineName) {
        for (MachineVNC machineVNC : VNCLIST) {
            if (machineVNC.getName().equals(machineName)) {
                return machineVNC.getIp();
            } // if
        } // for
        return null;
    } // static String getIp(String)

    /**
     * Get the VNC port for a displayable machine.
     *
     * @param machineName Machine name.
     * @return Given machine VNC port, -1 otherwise.
     */
    public static int getPort(String machineName) {
        for (MachineVNC machineVNC : VNCLIST) {
            if (machineVNC.getName().equals(machineName)) {
                return machineVNC.getPort();
            } // if
        } // for
        return -1;
    } // static String getIp(String)

    protected static void addDisplay(Runtime runtime, String... attributes)
            throws IOException {
        Process process = runtime.exec("virsh vncdisplay " + attributes[0].trim());
        InputStream inputStream = process.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String display = bufferedReader.readLine();
        String[] settings = display.split(":");
        String ip = settings[0].equals("") ? "127.0.0.1" : settings[0];
        VNCManager.VNCLIST.add(new MachineVNC(Integer.parseInt(attributes[0]),
                attributes[1], ip, 5900 + Integer.parseInt(settings[1])));
        assert bufferedReader.readLine() == null;
        bufferedReader.close();
        inputStreamReader.close();
        bufferedInputStream.close();
        inputStream.close();
    } // static void addDisplay(Runtime, String...)
} // class VNCManager

