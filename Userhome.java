
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import sun.util.locale.StringTokenIterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author DELL
 */
public class Userhome extends javax.swing.JFrame {

    String username;
    Thread t;

    public Userhome(String username) {
        this.username = username;
        initComponents();
        setSize(800, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //wall_pictures();
        fetchwall obj = new fetchwall();
        t = new Thread(obj);
        t.start();
    }

    public Userhome() {
        initComponents();
        setSize(800, 700);
    }

//    public void wall_pictures()
//    {
//        Userhome obj = new Userhome();
//        t = new Thread(obj);
//        t.start();
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jb1 = new javax.swing.JButton();
        followingbtn = new javax.swing.JButton();
        followerbtn = new javax.swing.JButton();
        postbtn = new javax.swing.JButton();
        searchbtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        user_wall_panel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jb1.setText("Change password");
        jb1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb1ActionPerformed(evt);
            }
        });
        getContentPane().add(jb1);
        jb1.setBounds(560, 460, 119, 36);

        followingbtn.setText("Followings");
        followingbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                followingbtnActionPerformed(evt);
            }
        });
        getContentPane().add(followingbtn);
        followingbtn.setBounds(120, 20, 210, 50);

        followerbtn.setText("Followers");
        followerbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                followerbtnActionPerformed(evt);
            }
        });
        getContentPane().add(followerbtn);
        followerbtn.setBounds(340, 20, 190, 50);

        postbtn.setBackground(new java.awt.Color(255, 255, 51));
        postbtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        postbtn.setText("+");
        postbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postbtnActionPerformed(evt);
            }
        });
        getContentPane().add(postbtn);
        postbtn.setBounds(10, 10, 70, 60);

        searchbtn.setText("Search Friends");
        searchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbtnActionPerformed(evt);
            }
        });
        getContentPane().add(searchbtn);
        searchbtn.setBounds(540, 10, 130, 40);

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel1.setText("Wall");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(310, 90, 130, 30);

        user_wall_panel.setBackground(new java.awt.Color(255, 255, 255));
        user_wall_panel.setLayout(null);
        jScrollPane1.setViewportView(user_wall_panel);

        jScrollPane2.setViewportView(jScrollPane1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 120, 660, 330);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jb1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb1ActionPerformed

        new ChangePassword(username).setVisible(true);

    }//GEN-LAST:event_jb1ActionPerformed

    private void followingbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_followingbtnActionPerformed
        try {
            new Following(username).setVisible(true);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        catch (UnirestException ex) {
//            Logger.getLogger(Userhome.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_followingbtnActionPerformed

    private void followerbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_followerbtnActionPerformed
        // TODO add your handling code here:
        try {
            new Followers(username).setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnirestException ex) {
            Logger.getLogger(Userhome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_followerbtnActionPerformed

    private void postbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postbtnActionPerformed
        try {
            new AddPost(username).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_postbtnActionPerformed

    private void searchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbtnActionPerformed
        new SearchUser(username).setVisible(true);
    }//GEN-LAST:event_searchbtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Userhome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Userhome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Userhome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Userhome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Userhome("salil").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton followerbtn;
    private javax.swing.JButton followingbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jb1;
    private javax.swing.JButton postbtn;
    private javax.swing.JButton searchbtn;
    public javax.swing.JPanel user_wall_panel;
    // End of variables declaration//GEN-END:variables

    class fetchwall implements Runnable {

        @Override
        public void run() {
            try {
                HttpResponse<String> res = Unirest.get("http://localhost:8999/wall")
                        .queryString("username", username)
                        .asString();

                if (res.getStatus() == 200) {
                    int y = 10;
                    int mainheight = 100;

                    String data = res.getBody();
                    StringTokenizer st = new StringTokenizer(data, "$");

                    user_wall_panel.removeAll();
                    while (st.hasMoreTokens()) {

                        String userdata = st.nextToken();
                        System.out.println(userdata);
                        StringTokenizer st1 = new StringTokenizer(userdata, "#;");
                        String followedto = st1.nextToken();
                        String user_photo = st1.nextToken();
                        String title = st1.nextToken();
                        String photo = st1.nextToken();
                        String date = st1.nextToken();
                        String pid = st1.nextToken();
                        String ans = st1.nextToken();
                        String count = st1.nextToken();
                        Wallpanel wp = new Wallpanel();
                        wp.setBounds(10, y, 600, 500);
                        user_wall_panel.add(wp);

                        URL url = new URL("http://localhost:8999/GetResource/" + photo);
                        BufferedImage bimg = ImageIO.read(url);
                        Image img = bimg.getScaledInstance(wp.postlb.getWidth(), wp.postlb.getHeight(), Image.SCALE_SMOOTH);
                        wp.postlb.setIcon(new ImageIcon(img));

                        URL url1 = new URL("http://localhost:8999/GetResource/" + user_photo);
                        BufferedImage bimg1 = ImageIO.read(url1);
                        Image img1 = bimg1.getScaledInstance(wp.photolb.getWidth(), wp.photolb.getHeight(), Image.SCALE_SMOOTH);
                        wp.photolb.setIcon(new ImageIcon(img1));

                        wp.usernamelb.setText(followedto);
                        wp.datelb.setText(date);
                        wp.titlelb.setText(title);
                        wp.likeslb.setText(count);
                        show_user_comments(pid, wp.commentpanel);

                        if (ans.equals("yes")) {
                            wp.likebtn.setText("Liked");
                        } else {
                            wp.likebtn.setText("Like");
                        }

                        wp.likebtn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ae) {

                                try {

                                    HttpResponse<String> res = Unirest.get("http://localhost:8999/likepost")
                                            .queryString("pid", pid)
                                            .queryString("username", username)
                                            .asString();

                                    if (res.getStatus() == 200) {
                                        String responsetext = res.getBody();
                                        if (res.getBody().equals("success")) {
                                            wp.likebtn.setText("Liked");
                                            int count = Integer.parseInt(wp.likeslb.getText());
                                            count++;
                                            wp.likeslb.setText(count + "");
                                        } else {
                                            wp.likebtn.setText("Like");

                                            int count = Integer.parseInt(wp.likeslb.getText());
                                            count--;
                                            wp.likeslb.setText(count + "");
                                        }

                                        wp.repaint();

                                    }

                                } catch (Exception e) {

                                    e.printStackTrace();
                                }

                            }
                        });

                        y += 510;
                        mainheight += 500;

                        user_wall_panel.setPreferredSize(new Dimension(jScrollPane2.getWidth() - 20, mainheight));
                        user_wall_panel.repaint();

                        wp.postlb.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent me) {
                                super.mouseClicked(me); //To change body of generated methods, choose Tools | Templates.
                                try {
                                    new StatusForm(pid).setVisible(true);
                                } catch (IOException ex) {
                                    Logger.getLogger(Userhome.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        });
                        wp.commentbtn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ae) {

                                String comment = wp.commenttf.getText();
                                try {
                                    HttpResponse<String> res1 = Unirest.get("http://localhost:8999/writecomment")
                                            .queryString("pid", pid)
                                            .queryString("username", username)
                                            .queryString("comment", comment)
                                            .asString();

                                    if (res1.getStatus() == 200) {
                                        JOptionPane.showMessageDialog(user_wall_panel, res1.getBody());
                                        show_user_comments(pid,wp.commentpanel);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void show_user_comments(String pid, JPanel commentpanel) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    HttpResponse<String> res = Unirest.get("http://localhost:8999/displaycomments")
                            .queryString("pid", pid)
                            .asString();

                    if (res.getStatus() == 200) {
                        int y = 10;
                        int mainheight = 300;

                        String data = res.getBody();
                        StringTokenizer st = new StringTokenizer(data, "$");

                  
                        
                        while (st.hasMoreTokens()) {

                            String userdata = st.nextToken();
                            System.out.println(userdata);
                            StringTokenizer st1 = new StringTokenizer(userdata, "#;");
                            String user_photo = st1.nextToken();
                            String user_name = st1.nextToken();
                            String comment = st1.nextToken();
                            ShowCommentPanel scp = new ShowCommentPanel();
                            scp.setBounds(10, y, 500, 80);
                            commentpanel.add(scp);

                            URL url = new URL("http://localhost:8999/GetResource/" + user_photo);
                            BufferedImage bimg = ImageIO.read(url);
                            Image img = bimg.getScaledInstance(scp.cphotolb.getWidth(), scp.cphotolb.getHeight(), Image.SCALE_SMOOTH);
                            scp.cphotolb.setIcon(new ImageIcon(img));

                            scp.usernamelb.setText(user_name);
                            scp.commentlb.setText(comment);

                            scp.repaint();
                            y += 85;
                            mainheight += 85;
                        }

                        commentpanel.repaint();
                        commentpanel.setPreferredSize(new Dimension(jScrollPane1.getWidth() - 20, mainheight));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}