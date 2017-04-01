//package Interface;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JButton;
//
//import Sockets.Client;
//
//public class DBController {
//	
//	private DBView view;
//	private DBModel model;
//	
//	public DBController(DBView view, DBModel model){
//		this.view = view;
//		this.model = model;
//	}
//
//	
//	public void addActionListener(JButton button, int idx) {
//		switch (idx) {
//			case 0:
//				button.addActionListener(new ActionListener() {  
//			        public void actionPerformed(ActionEvent e) {
//			        	//new Server()
//			        	//view.removePanel();
//			        	System.out.println("Button 1 pressed");
//			        }
//				});
//				break;
//			case 1:
//				button.addActionListener(new ActionListener() {  
//			        public void actionPerformed(ActionEvent e) {
//		        		//new Client(model.getAddress(), model.getPort()).run();
//		    	    	//view.removePanel();
//			        	System.out.println("Button 2 pressed");
//			        }
//				});
//			case 2:
//				button.addActionListener(new ActionListener() {  
//			        public void actionPerformed(ActionEvent e) {
////		        		new DBMenu();
////		    	    	view.removePanel();
//		        		System.out.println("Button 3 pressed");
//			        }
//				});
//				break;
//		}
//		
//	}
//}
