package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** we need to refactor this **/

public class MenuView extends JFrame {
    private static final long serialVersionUID = 1L;
    private MenuModel model;
    private MenuController controller;
    private JPanel panel;

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
		this.setBounds(0,0, (int)width,(int) height-50);
		
		
		
		
        //set up 'Asteroids' label
		
        JLabel asteroidsLabel = new JLabel("Activity Monitor");
        Font myfont = new Font("Helvetica", Font.PLAIN, 90);  

        asteroidsLabel.setFont(myfont);
        asteroidsLabel.setForeground(new Color(15 , 59 , 104));
        asteroidsLabel.setBounds((int) width/2 - 300,30,700,200);
        panel.add(asteroidsLabel);

        //set up the buttons
        ArrayList<JButton> buttons = new ArrayList<>(); 
        buttons.add(new JButton("Run locally"));
        buttons.add(new JButton("Connect to a network"));
//        buttons.add(new JButton("Lay down and cry"));
//        buttons.add(new JButton("Join multiplayer game"));

        for (int i = 0; i < buttons.size(); i++) {
            JButton button = buttons.get(i);
            panel.add(button);
            button.setFont(new Font("Helvetica", Font.BOLD, 30));
            button.setForeground(new Color(27,159,198));
            button.setBounds((int)width/2-200, 250 + i * 60, 400,50);
            button.setBackground(Color.white);
            controller.addActionListener(button, i);
        }
        
        //TODO Add project description button
//        JButton projectDescription = new JButton("Project description");
//        highscores.setForeground(Color.white);
//        highscores.setBounds(200,500, 200, 30);
//        highscores.setBackground(Color.BLACK);   
//        this.add(highscores);
//        controller.addActionListener(highscores, 4);
        

        //show local IP/port
        JLabel  localIPLabel = new JLabel();
        JLabel  localPortLabel = new JLabel();

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
        JLabel serverAddressLabel = new JLabel("server-address:port");
        JTextField serverAddressField = new JTextField("localhost:8850"); 

        JButton setServerButton = new JButton("Set address");
        
        setServerButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	String[] addr = serverAddressField.getText().split(":");
            	model.setAddress(addr[0]);
            	model.setPort(Integer.parseInt(addr[1]));
            }
        });

        serverAddressLabel.setForeground(new Color(28,28,28));
        serverAddressLabel.setBounds((int)width/2-200, 370, 200, 30);
        serverAddressLabel.setBackground(Color.white);

        serverAddressField.setForeground(Color.gray);
        serverAddressField.setBounds((int)width/2-200, 400, 400 ,50);
        serverAddressField.setBackground(Color.white);
        
        setServerButton.setBounds((int)width/2+200, 400, 120, 50);
        setServerButton.setForeground(new Color(27,159,198));
        setServerButton.setBackground(Color.white);

        panel.add(serverAddressLabel);
        panel.add(serverAddressField);
        panel.add(setServerButton);

//        //enter nickname
//        JTextField nicknameField = new JTextField("dummy");
//        JLabel  playerNameDisplay = new JLabel();
//        JButton setNicknameButton = new JButton("Set Name");
//
//        panel.add(nicknameField);
//        nicknameField.setBounds(200, 370, 200, 20);
//        setNicknameButton.setBounds(400, 370, 120, 20);
//        setNicknameButton.setForeground(Color.white);
//        setNicknameButton.setBackground(Color.black);
//        panel.add(setNicknameButton);
//
//        setNicknameButton.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e) {
//                panel.add(playerNameDisplay);
//                model.setNickname(nicknameField.getText());
//                playerNameDisplay.setText(model.getNickname());
//                playerNameDisplay.setBackground(Color.WHITE);
//                playerNameDisplay.setForeground(Color.white);
//                playerNameDisplay.setBounds(200,150,100,40);
//            }
//        });

        this.add(panel);
        }

    public void removePanel() {
        panel.setVisible(false);
        dispose();
    }

}