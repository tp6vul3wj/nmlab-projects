package gui;

import cr1.Cr1;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 *
 * @author vivian
 */
public class window extends javax.swing.JFrame {

    private Cr1 us;
    private DefaultListModel user;
    private String msg;
    // variable for "online now"
    int client_number = 10;
    int room_number = 10;
    private ButtonGroup buttonGroup;
    private ButtonGroup buttonGroup2;
    private JToggleButton jToggleButton[] = new JToggleButton[client_number];
    private JToggleButton jToggleButton2[] = new JToggleButton[room_number];
    private String room_click;
    private String client_click = "Room 0";
    int roomcount;
    int count = 0;
    Vector<String> client_list = new Vector<String>();

    public window(Cr1 us) {
        this.us = us;
        user = new DefaultListModel();
        initComponents();
        //  for(int i=0; i<client_number; i++) addUsertoList("USER",i);
        buttonGroup = new ButtonGroup();
        buttonGroup2 = new ButtonGroup();
        roomcount = 0;
        jToggleButton2[roomcount] = new JToggleButton();
        jToggleButton2[roomcount].setText("Room 0");
        jToggleButton2[roomcount].setBounds(10, (30 + roomcount * 30), 93, 23);
        jToggleButton2[roomcount].addActionListener(new ActLis());
        jToggleButton2[roomcount].setFocusable(true);
//        buttonGroup.add(jToggleButton[count]);
        buttonGroup2.add(jToggleButton2[roomcount]);
        jLayeredPane3.add(jToggleButton2[roomcount], javax.swing.JLayeredPane.DEFAULT_LAYER);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLabel2 = new javax.swing.JLabel();
        PrivateButton = new javax.swing.JButton();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList(user);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jScrollPane1.setBounds(100, 40, 480, 260);
        jLayeredPane1.add(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });
        jTextField1.setBounds(220, 340, 360, 80);
        jLayeredPane1.add(jTextField1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 204, 255), new java.awt.Color(204, 204, 204)), "Online Now", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Centaur", 1, 18), new java.awt.Color(153, 204, 255))); // NOI18N
        jLayeredPane2.setBounds(600, 40, 150, 380);
        jLayeredPane1.add(jLayeredPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/05.png"))); // NOI18N
        jLabel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 204, 255), new java.awt.Color(204, 204, 204)), "Server", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Centaur", 1, 18), new java.awt.Color(153, 204, 255))); // NOI18N
        jLabel2.setBounds(100, 320, 110, 100);
        jLayeredPane1.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        PrivateButton.setText("Private");
        PrivateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrivateButtonActionPerformed(evt);
            }
        });
        PrivateButton.setBounds(510, 310, 65, 23);
        jLayeredPane1.add(PrivateButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 204, 255), new java.awt.Color(204, 204, 204)), "RoomID", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Centaur", 1, 18), new java.awt.Color(153, 204, 255))); // NOI18N
        jLayeredPane3.setBounds(780, 40, 120, 380);
        jLayeredPane1.add(jLayeredPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/03.png"))); // NOI18N
        jLabel1.setBounds(-40, -180, 1070, 620);
        jLayeredPane1.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jScrollPane2.setViewportView(jList1);

        jScrollPane2.setBounds(480, 210, 70, 50);
        jLayeredPane1.add(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() == '\n') {
            String msg = jTextField1.getText();
            jTextArea1.append((new Date()).toString() + "\n");
            jTextArea1.append(msg + "\n");
            jTextField1.setText(null);
            String sendall;
            String[] splitedLine = room_click.split(" ", 2);
            sendall = "/s Server " + splitedLine[1] + " BOLD 12 BLUE " + msg;
            System.out.println(sendall);
            us.sendAll(sendall);
        }


    }//GEN-LAST:event_jTextField1KeyPressed

    private void PrivateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrivateButtonActionPerformed
        String msg = jTextField1.getText();
        jTextArea1.append((new Date()).toString() + "\n");
        jTextArea1.append(msg + "\n");
        jTextField1.setText(null);
        String sendall;
        sendall = "/p Server " + client_click + " 0 " + msg;
        System.out.println(sendall);
        us.sendAll(sendall);
    }//GEN-LAST:event_PrivateButtonActionPerformed

    private void playDingdong(){
        File soundFile;
        JDialog playingDialog;
        Clip clip;
        soundFile = new File("C:\\Users\\vivian\\Desktop\\cr1\\BELLHISS.WAV");
        System.out.println("Playing " + soundFile.getName());
        Line.Info linfo = new Line.Info(Clip.class);
        Line line;
        try {
            line = AudioSystem.getLine(linfo);
            clip = (Clip) line;
            AudioInputStream ais = null;
            try {
                ais = AudioSystem.getAudioInputStream(soundFile);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                clip.open(ais);
            } catch (IOException ex) {
                Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
            }
            clip.start();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void addText(String str) {
        jTextArea1.append((new Date()).toString() + "\n");
        jTextArea1.append(" " + str + "\n");
        //Document d = jTextArea1.getDocument();
        //jTextArea1.select(d.getLength(), d.getLength());
    }

// ****************************************************************************
// for adding user to list...+ remove list    
    public void addUser(String name) {
        user.addElement(name);
        client_list.add(name);
    }

    
    public void addUsertoList(String name, String filename) throws IOException {
        Image img = null;
        File file = new File(filename);
        img = ImageIO.read(file);
        Image newimg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        Icon tmp = new ImageIcon(filename);
        //Image headPic = tmp.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        jToggleButton[count] = new JToggleButton();
        jToggleButton[count].setIcon(new javax.swing.ImageIcon(newimg));
        jToggleButton[count].setText(name);
        jToggleButton[count].setBounds(5, (30 + count * 65), 130, 60);
        jToggleButton[count].addActionListener(new ActLis());
        jToggleButton[count].setFocusable(true);
        buttonGroup.add(jToggleButton[count]);
        jLayeredPane2.add(jToggleButton[count], javax.swing.JLayeredPane.DEFAULT_LAYER);
        count++;
    }

    class ActLis implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("SYSTEM CLICK");
            for (int i = 0; i < count + 1; i++) {
                if (ae.getSource() == jToggleButton[i]) {
                    client_click = jToggleButton[i].getText();
                    System.out.println(client_click);
                }

            }

            for (int i = 0; i < roomcount + 1; i++) {
                if (ae.getSource() == jToggleButton2[i]) {
                    room_click = jToggleButton2[i].getText();
                    System.out.println(room_click);
                }

            }
        }
    }

    public void removeUserfromList(String name) throws IOException {

        jLayeredPane2.removeAll();
        count = count - 1;
        client_list.remove(name);
        for (int i = 0; i < client_list.size(); i++) {
            Image img = null;
            String filename = client_list.get(i) + ".png";
            File file = new File(filename);
            img = ImageIO.read(file);
            Image newimg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            Icon tmp = new ImageIcon(filename);

            jToggleButton[i] = new JToggleButton();
            jToggleButton[i].setText(client_list.get(i));
            jToggleButton[i].setIcon(new javax.swing.ImageIcon(newimg));
            jToggleButton[i].setBounds(5, (30 + i * 65), 130, 60);
            jToggleButton[i].addActionListener(new ActLis());
            jToggleButton[i].setFocusable(true);
            jLayeredPane2.add(jToggleButton[i], javax.swing.JLayeredPane.DEFAULT_LAYER);

        }
        repaint();
    }

    /**
     * **
     */
    public void addRoomtoList() {
        roomcount = us.getRoomid_o();
        jToggleButton2[roomcount] = new JToggleButton();
        jToggleButton2[roomcount].setText("Room " + us.getRoomid_o());
        jToggleButton2[roomcount].setBounds(10, (30 + roomcount * 30), 93, 23);
        jToggleButton2[roomcount].addActionListener(new ActLis());
        jToggleButton2[roomcount].setFocusable(true);
        buttonGroup2.add(jToggleButton2[roomcount]);
        jLayeredPane3.add(jToggleButton2[roomcount], javax.swing.JLayeredPane.DEFAULT_LAYER);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton PrivateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
