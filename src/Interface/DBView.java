//package Interface;
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//
//public class DBView extends JFrame{
//	
//    private static final long serialVersionUID = 1L;
//    private DBModel model;
//    private DBController controller;
//    private JPanel panel;
//    private double width, height;
//
//    public DBView(DBModel model) {
//        this.model = model;
//        this.controller = new DBController(this, model);
//        initComponents();
//    }
//	
//
//    public void initComponents(){
//
//        //set up the panel
//        panel = new JPanel();
//        panel.setLayout(null);
//        panel.setBackground(Color.white);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		this.width = screenSize.getWidth();
//		this.height = screenSize.getHeight();
//		this.setBounds(0,0, (int)width,(int) height-50);
//
//		setTitle();
//
////		setTextFields();
//		
//		setRetrieveButton();
//
//        this.add(panel);
//   
//    
//    
//    }
//
//    
//    private void setRetrieveButton(){
//		JButton retrieve = new JButton("Retrieve database entries");
//		retrieve.setBounds(1000, 160, 400, 50);
//		retrieve.setForeground(new Color(27,159,198));
//		retrieve.setBackground(Color.white);
//		panel.add(retrieve);
//		retrieve.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e) {
//            	System.out.println("Nothing happened");
//            }
//        });
//
//    }
//
//    
//    
//    
//	private void setTextFields() {
////		ArrayList<JTextField> fields = new ArrayList<>(); 
//		
//		//TODO: Maybe change this later?
//		JTextField firstNameField = new JTextField("First name"); 
//		panel.add(firstNameField);
//		firstNameField.setFont(new Font("Helvetica", Font.PLAIN, 15));
//		firstNameField.setForeground(new Color(27,159,198));
//		firstNameField.setBounds(100, 150 , 300,50);
//		firstNameField.setBackground(Color.white);
//		
//		JTextField lastNameField = new JTextField("Last name");
//		panel.add(lastNameField);
//		lastNameField.setFont(new Font("Helvetica", Font.PLAIN, 15));
//		lastNameField.setForeground(new Color(27,159,198));
//		lastNameField.setBounds(100, 150 + 60, 300,50);
//		lastNameField.setBackground(Color.white);
//		
//		JTextField addressField = new JTextField("address"); 
//		panel.add(addressField);
//		addressField.setFont(new Font("Helvetica", Font.PLAIN, 15));
//		addressField.setForeground(new Color(27,159,198));
//		addressField.setBounds(100, 150 + 2*60, 300,50);
//		addressField.setBackground(Color.white);
//		
//		JTextField salaryField = new JTextField("salary"); 
//		panel.add(salaryField);
//		salaryField.setFont(new Font("Helvetica", Font.PLAIN, 15));
//		salaryField.setForeground(new Color(27,159,198));
//		salaryField.setBounds(100, 150 + 3*60, 300,50);
//		salaryField.setBackground(Color.white);
//
//		JButton setFields = new JButton("Enter details");
//		setFields.setBounds(200, 430, 120, 50);
//		setFields.setForeground(new Color(27,159,198));
//		setFields.setBackground(Color.white);
//		panel.add(setFields);
//		
//		setFields.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e) {
//            	String text = firstNameField.getText();
//            	model.setFirstName(text);
////            	System.out.println(text);
//            	/* right now i just pass the name
//            	 * to the model; the model will have to 
//            	 * create the new Employee object
//            	 * with all the details of the 
//            	 * employee;
//            	 * */
//            	text = lastNameField.getText();
//            	model.setLastName(text);
//            	text = addressField.getText();
//            	model.setAddress(text);
//            	text = salaryField.getText();
//            	model.setSalary(text);
//            	
//
//            }	
//        });
//
//		
//	}
//
//
//	private void setTitle() {
//		JLabel title = new JLabel("Database Operations");
//        Font myfont = new Font("Helvetica", Font.PLAIN, 70);  
//        title.setFont(myfont);
//        title.setForeground(new Color(15 , 59 , 104));
//        title.setBounds((int) width/2 - 350,10,900,200);
//        panel.add(title);
//	}
//
//    public void removePanel() {
//        panel.setVisible(false);
//        dispose();
//    }
//    
//    
//}
