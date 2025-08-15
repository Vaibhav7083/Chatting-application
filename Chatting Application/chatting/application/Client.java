package chatting.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.*;
import java.text.*;

public class Client extends JFrame implements ActionListener {

    static JPanel a1;
    JTextField text;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();

    static DataOutputStream dout;
    Client() {
        
        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon img1 = new ImageIcon(ClassLoader.getSystemResource("Icons/3.png"));
        Image img2 = img1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon img3 = new ImageIcon(img2);
        JLabel back = new JLabel(img3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon img4 = new ImageIcon(ClassLoader.getSystemResource("Icons/Pralhad.png"));
        Image img5 = img4.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        ImageIcon img6 = new ImageIcon(img5);
        JLabel profile = new JLabel(img6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);

        ImageIcon img7 = new ImageIcon(ClassLoader.getSystemResource("Icons/video.png"));
        Image img8 = img7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon img9 = new ImageIcon(img8);
        JLabel video = new JLabel(img9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);

        ImageIcon img10 = new ImageIcon(ClassLoader.getSystemResource("Icons/phone.png"));
        Image img11 = img10.getImage().getScaledInstance(35, 25, Image.SCALE_DEFAULT);
        ImageIcon img12 = new ImageIcon(img11);
        JLabel phone = new JLabel(img12);
        phone.setBounds(360, 20, 30, 30);
        p1.add(phone);

        ImageIcon img13 = new ImageIcon(ClassLoader.getSystemResource("Icons/3icon.png"));
        Image img14 = img13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon img15 = new ImageIcon(img14);
        JLabel morvert = new JLabel(img15);
        morvert.setBounds(420, 20, 10, 25);
        p1.add(morvert);

        JLabel name = new JLabel("Pralhad");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 12));
        p1.add(status);

        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1);

        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SA_SARIF", Font.PLAIN, 16));
        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320, 650, 123, 40);
        send.setBackground(new Color(7, 94, 94));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.BOLD, 12));
        f.add(send);

        f.setSize(450, 700);
        f.setLocation(800, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
        
    }

    public void actionPerformed(ActionEvent ae){
        try {
            String out = text.getText();

            JLabel output = new JLabel(out);

            JPanel p2 = formatLabel(out);
            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 15));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); 

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new Client();

        try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            
            while (true) {
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);

                f.validate();
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}