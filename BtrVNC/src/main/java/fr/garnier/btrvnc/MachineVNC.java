/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.garnier.btrvnc;

/**
 * MachineVNC
 *
 * @author agarnier
 */
class MachineVNC {

    private int id;
    private String name;
    private String ip;
    private int port;

    MachineVNC(int id, String name, String ip, int port) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
    } // MachineVNC(String,int)

    int getId(){
        return this.id;
    } // int getId()

    String getName(){
        return this.name;
    } // getName();

    String getIp() {
        return this.ip;
    } // String getIp()

    int getPort() {
        return this.port;
    } // int getPort()

    @Override
    public String toString() {
        return "MachineVNC{" + "id=" + id + ";name=" + name + ";ip=" + ip + ";port=" + port + '}';
    }
} // class MachineVNC
