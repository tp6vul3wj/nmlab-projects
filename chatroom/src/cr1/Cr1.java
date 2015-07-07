package cr1;

import gui.window;
import java.awt.Image;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Cr1 {

    private ServerSocket server;
    private int port;
    private Client c;
    private List<Client> clients = new ArrayList<>();
    private Map<String, String> map = new HashMap<String, String>(); // picture+name!
    // addd
    private Vector<String> client_name = new Vector<>();
    private String name_string = null;
    private String file_string = null;
    int id; // cumulate client ids
    private int roomid = 0;
    window display;
    String ipp;

    public Cr1() {
        // initial connnection
        id = 0;
        display = new window(this);
        display.setVisible(true);
        port = 1024;
        System.out.println("port: " + port);

        try {
            server = new ServerSocket(port);
            display.addText("Server created.");
            display.addText("Waiting for client.");
            while (true) {
                synchronized (this) {
                    Socket client = server.accept();
                    display.addText("Connected from: " + client.getInetAddress().getHostAddress() + " ;ID: " + id);
                    ipp = client.getInetAddress().getHostAddress();
                    if (client != null) {

                        c = new Client(this, client, id);
                        id++;
                        clients.add(c);
                        Thread thd = new Thread(c);
                        thd.start();
                        c = null;
                    }

                }

            }
        } catch (IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
            display.addText("Server error !");
        }
    }

// for get server or client
    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket ss) {
        this.server = ss;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

// for sending msg to clients
    public void sendAll(String str) {

        for (Client c : clients) {
            c.send(str);
        }

        for (int i = 0; i < clients.size(); i++) {
            c = clients.get(i);
            System.out.println(c.getUsername());
        }
    }

// for print msg to server        
    public void printMsg(String msg) {
        display.addText(msg);
    }

//**************************************************************************
    public boolean check_Name_Avail(String name_in) {
        Client tmp;
        String name_tmp;
        int n = clients.size();

        for (int i = 0; i < n - 1; i++) {
            tmp = clients.get(i);
            name_tmp = tmp.getUsername();
            if (name_in.equals(name_tmp)) {
                System.out.println("name already");
                return false;
            };
        }
        return true;
    }
// for add user    

    public void adduser(String username, String filename) throws IOException {
        map.put(username, filename);
        display.addUser(username);
        display.addUsertoList(username, filename);
        client_name.add(username);
        name_string = null;
        for (int i = 0; i < clients.size(); i++) {
            Client c = clients.get(i);
            String tmp = c.getUsername();
            name_string = name_string + " " + tmp;
        }
        System.out.println(name_string);

    }

    public void adduser_client(String name) {
        for (Client c : clients) {
            if (c.getUsername() != name) {
                c.send("/d " + name);
            }

            if (c.getUsername() == name) {
                c.send("/t " + id + " " + name_string);

            }
        }

    }

    public void removeUser(String name) throws IOException {

        // remove from client array in server
        for (int i = 0; i < clients.size(); i++) {
            Client c = clients.get(i);
            String tmp = c.getUsername();
            if (tmp == name) {
                clients.remove(c);
            }
        }
        map.remove(name);
        id = id - 1;
        display.removeUserfromList(name);

    }

    //**********************************************************************
    public int getRoomid() {

        roomid = roomid + 1;
        return roomid;
    }

    public int getRoomid_o() {
        return roomid;
    }

    public void addRoomtoList() {
        display.addRoomtoList();
    }

    /**
     * ***************************************************************************
     */
    public String chooseFile() {
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(new JFrame());
        File sendfile;
        sendfile = fc.getSelectedFile();
        String fileName = sendfile.getAbsolutePath();
        int filesize = (int) sendfile.length();
        System.out.println(fileName + "  " + filesize);

        return fileName;
    }

    public void sendFile(String fileName) {

        if (fileName == null) {
            return; //增加文件流用來讀取文件中的資料
        }
        File file = new File(fileName);
        System.out.println("文件長度:" + (int) file.length()); // public Socket accept() throws
        System.out.println("文件名稱:" + (String) file.getName()); // public Socket accept() throws
        try {
            FileInputStream fos = new FileInputStream(file); //增加網絡服務器接受客戶請求
            ServerSocket ss = new ServerSocket(8082);
            Socket client = ss.accept(); //增加網絡輸出流並提供資料包裝器
            OutputStream netOut = client.getOutputStream();
            OutputStream doc = new DataOutputStream(new BufferedOutputStream(netOut)); //增加文件讀取緩衝區
            byte[] buf = new byte[2048];
            int num = fos.read(buf);
            System.out.println("傳送文件中:" + (String) file.getName()); // public Socket accept() throws
            while (num != (- 1)) { //是否讀完文件
                doc.write(buf, 0, num); //把文件資料寫出網絡緩衝區
                doc.flush(); //重整緩衝區把資料寫往客戶端
                num = fos.read(buf); //繼續從文件中讀取資料
            }
            fos.close();
            doc.close();
            ss.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
    }

    public void C_getFile(String strTemp, String IP) { //使用本地文件系統接受網絡資料並存為新文件
        try {
            File file = new File(strTemp); //如果文件已經存在，先刪除
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            RandomAccessFile raf = new RandomAccessFile(file, "rw"); // 通過Socket連接文件服務器
            Socket server = new Socket(IP, 8081); //增加網絡接受流接受服務器文件資料 
            InputStream netIn = server.getInputStream();
            InputStream in = new DataInputStream(new BufferedInputStream(netIn)); //增加緩衝區緩衝網絡資料
            byte[] buf = new byte[2048];
            int num = in.read(buf);
            System.out.println("接受文件中:" + (String) file.getName()); // public Socket accept() throws
            while (num != (- 1)) { //是否讀完所有資料
                raf.write(buf, 0, num); //將資料寫往文件
                raf.skipBytes(num); //順序寫文件字元
                num = in.read(buf); //繼續從網絡中讀取文件
            }
            in.close();
            raf.close();
            server.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
    }
    /**
     * ************************************************************************
     */
    SendImg srv;

    public void sendImg(String fileName) {
        srv = new SendImg(fileName);
        srv.ejecutar();
    }
}

class SendImg {

    ServerSocket ser;
    Socket con;
    ObjectOutputStream salida;
    ObjectInputStream entrada;
    String fileName;

    public SendImg(String file) {
        fileName = file;
    }

    void ejecutar() {
        try {
            ser = new ServerSocket(5700, 1);
            con = ser.accept();
            salida = new ObjectOutputStream(con.getOutputStream());
            salida.flush();
            entrada = new ObjectInputStream(con.getInputStream());
            procesar();
            ser.close();
        } catch (IOException e) {
        }
    }

    void procesar() {

        while (true) {
            try {

                File f = new File(fileName);
                Image img;
                img = ImageIO.read(f);
                Image newimg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
                salida.writeObject(new javax.swing.ImageIcon(newimg));
                break;

            } catch (IOException e) {
            }
        };
    }
}
