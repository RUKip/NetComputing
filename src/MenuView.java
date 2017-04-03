

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Sockets.SocketServer;

/** we need to refactor this **/

public class MenuView extends JFrame {
    private static final long serialVersionUID = 1L;
    private MenuModel model;
    private MenuController controller;
    private JPanel panel;
    private Font myFont = new Font("Helvetica", Font.BOLD, 25);
    public MenuView(MenuModel model) {
        this.model = model;
        this.controller = new MenuController(this, model);
        initComponents();
    }

    public void initComponents(){

        //set up the panel
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
//		this.setBounds((int) width / 2 - 300, (int) height / 2 - 300, 600, 600);
		this.setBounds(0,0, (int)width/2+200,(int) height/2+200);
				
        JLabel title = new JLabel("Activity Monitor");
        Font myfont = new Font("Helvetica", Font.PLAIN, 90);  

        title.setFont(myfont);
        title.setForeground(new Color(15 , 59 , 104));
        title.setBounds((int) width/4-200,30,900,200);
        panel.add(title);
        
        
        JLabel text = new JLabel("");
        text.setFont(myFont);
        text.setForeground(new Color(28,28,28));
        text.setBounds((int)width/4- 30,(int) 250+200,300,30);
        panel.add(text);

        JButton button1 = new JButton("Start a server");
        button1.setFont(myFont);
        button1.setForeground(new Color(27,159,198));
        button1.setBounds((int)width/4-200, 250 + 60, 600,50);
        button1.setBackground(Color.white);
        panel.add(button1);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					new Server();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				text.setText("Created a new server!");
				System.out.println("Started a server... " );
            }
        });

        JButton button2 = new JButton("Start a client");
        button2.setFont(myFont);
        button2.setForeground(new Color(27,159,198));
        button2.setBounds((int)width/4-200, 250 + 120, 600,50);
        button2.setBackground(Color.white);
        panel.add(button2);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	SocketServer s = new SocketServer();
				new Client(s).run();
				text.setText("Created a new client!");
				System.out.println("Started a client... " );

            }
        });
         
        //show local IP/port
        JLabel  localIPLabel = new JLabel();

        try {
			localIPLabel.setText(InetAddress.getLocalHost().getHostAddress() + ":8850");
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
        localIPLabel.setFont(new Font("Helvetica",Font.PLAIN, 30 ));
        localIPLabel.setForeground(new Color(28,28,28));
        localIPLabel.setBounds(0,(int)height-150,350,80);
        panel.add(localIPLabel);

        //enter server address
//        JLabel serverAddressLabel = new JLabel("server-address:port");
//        JTextField serverAddressField = new JTextField("192.168.0.10:8850"); 

//        JButton setServerButton = new JButton("Set address");
        
//        setServerButton.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e) {
//            	String[] addr = serverAddressField.getText().split(":");
//            	model.setAddress(addr[0]);
//            	model.setPort(Integer.parseInt(addr[1]));
//            }
//        });

//        serverAddressLabel.setForeground(new Color(28,28,28));
//        serverAddressLabel.setBounds((int)width/2-300, 670, 200, 30);
//        serverAddressLabel.setBackground(Color.white);

//        serverAddressField.setForeground(Color.gray);
//        serverAddressField.setBounds((int)width/2-300, 700, 600 ,50);
//        serverAddressField.setBackground(Color.white);
//        
//        setServerButton.setBounds((int)width/2+300, 700, 120, 50);
//        setServerButton.setForeground(new Color(27,159,198));
//        setServerButton.setBackground(Color.white);

//        panel.add(serverAddressLabel);
//        panel.add(serverAddressField);
//        panel.add(setServerButton);

        this.add(panel);
        }

    public void removePanel() {
        panel.setVisible(false);
        dispose();
    }

}