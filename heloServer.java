import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.sql.*;

public class heloServer extends JFrame implements ActionListener
{
    JButton btn;

    heloServer()
    {
        setSize(400,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        btn = new JButton("Start server ");
        btn.setBounds(10,100,150,90);
        btn.addActionListener(this);
        add(btn);

        setVisible(true);
    } 
    @Override
    public void actionPerformed(ActionEvent e)
    {
        
        try 
        {
            MyInstaServer obj = new MyInstaServer(8999);
            
        } 
        catch (Exception m) 
        {
            m.getStackTrace();
        }
    }
    public static void main(String[] args) {
        heloServer obj = new heloServer();
    }    
}
