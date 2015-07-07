package cr1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {

    private Socket client = null;
    private Cr1 server;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private String msg, username;
    Vector<String> name_list = new Vector<String>();
    List<Client> clients;
    int count = 0;
    boolean setname_check = false;

    public Client(Cr1 cr, Socket client2, int id) {
        try {
            server = cr;
            client = client2;
            dis = new DataInputStream(client2.getInputStream());
            dos = new DataOutputStream(client2.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

    }

    public void setname() throws IOException {
        String msg;
        dos.writeUTF("/u");
        while (true) {
            msg = dis.readUTF();
            String[] splitedLine = msg.split(" ", 2);
            username = splitedLine[0];
            //filename = splitedLine[1];
            if (server.check_Name_Avail(username) == true) {
                send("/ua"); // send username ACK
                setname_check = true;
                send("/s Server 0 " + "BOLD 12 BLUE " + "Welcome to the chatroom " + username);
                server.printMsg(username + " joined.");
                server.C_getFile(username + ".png", server.ipp); //server get new client picture
                if(server.id!=0){
                    clients = server.getClients();
                    /**///send old clients the new clients picture
                    for (int i=0; i<clients.size()-1; i++){
                        Client client_tmp = clients.get(i);
                        String name_tmp = client_tmp.getUsername();
                        server.sendAll("/z "+name_tmp+" "+username);
                        server.sendImg("C:\\Users\\vivian\\Desktop\\cr1\\"+username+".png");     
                    }
                    
                    for (int i=0; i<clients.size()-1; i++){
                        Client client_tmp = clients.get(i);
                        String name_tmp = client_tmp.getUsername();
                        server.sendAll("/X "+username);
                        server.sendImg("C:\\Users\\vivian\\Desktop\\cr1\\"+name_tmp+".png");     
                    }
                                       
                }



                server.adduser(username, username + ".png");
                server.adduser_client(username);
                
            }
            else{
                send("/una");
                setname_check = false;
            }
            break;
        }
    }

    public void send(String s) {
        try {
            dos.writeUTF(s);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void run() {
        try {
            while (setname_check ==false) setname();
            while (true) {
                msg = dis.readUTF();
                if (msg.startsWith("/s") || msg.startsWith("/p")) {
                    server.printMsg(msg);
                    server.sendAll(msg);
                } // for client to ask a new rom --> send roomid
                else if (msg.startsWith("/n")) {
                    server.sendAll("/n " + server.getRoomid());
                    server.addRoomtoList();

                } // for client ask others to enter the tab
                else if (msg.startsWith("/a")) { // /a username tperson roomid size client
                    String[] splitedLine = msg.split(" ", 6);
                    int client_list_size = Integer.parseInt(splitedLine[4]);
                    String[] splitedLine2 = splitedLine[5].split(" ", client_list_size + 1);
                    for (int i = 0; i < client_list_size; i++) { // /b uer target roomid
                        server.sendAll("/b " + splitedLine2[i + 1] + " " + splitedLine[2] + " " + splitedLine[3]);
                    }
                    server.sendAll(msg);

                }
                else if (msg.startsWith("/F")){ // /F username target IP
                    server.sendAll(msg);
                }
                
                else if (msg.startsWith("/lr")){
                    server.sendAll(msg);
                }
                
                 else if (msg.startsWith("/S")){
                    server.sendAll(msg);
                }
                
                 else if (msg.startsWith("/D")){
                    server.sendAll(msg);
                }
                 
                 else if (msg.startsWith("/c")){
                    server.sendAll(msg);
                }
                else if (msg.startsWith("/CB")){
                    server.sendAll(msg);
                }
                else if (msg.startsWith("/CR")){
                    server.sendAll(msg);
                }
                else if (msg.startsWith("/CD")){
                    server.sendAll(msg);
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
            System.out.println("disconnect!");
            try {
                server.removeUser(username);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            server.sendAll("/r " + username); //for other to remove
            server.sendAll("/s Server 0" + " BOLD 16 BLUE " + username + " leaves this room...");
            server.printMsg("/s Server " + username + " leaved.");
        }
    }
}