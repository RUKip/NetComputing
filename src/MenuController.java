

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Sockets.SocketServer;



public class MenuController {
	
	private MenuView view;
	private MenuModel model;
	
	public MenuController(MenuView view, MenuModel model){
		this.view = view;
		this.model = model;
	}
	

	public void addActionListener(JButton button, int idx) {
		switch (idx) {
			case 0:
				button.addActionListener(new ActionListener() {  
			        public void actionPerformed(ActionEvent e) {
			        	//new Server()
			        	//view.removePanel();
			        }
				});
				break;
			case 1:
				button.addActionListener(new ActionListener() {  
			        public void actionPerformed(ActionEvent e) {
			        	System.out.println(" clicked client button" );
			        	SocketServer s = new SocketServer();
			        	new Client(s).run();
		        		System.out.println(" started a client " );
		    	    	//view.removePanel();
			        }
				});
			case 2:
				button.addActionListener(new ActionListener() {  
			        public void actionPerformed(ActionEvent e) {
		        		//new DBMenu();
		    	    	view.removePanel();
			        }
				});
				break;
//			case 3:
//				button.addActionListener(new ActionListener() {  
//			        public void actionPerformed(ActionEvent e) {
////		        		Joiner j = new Joiner(model.getAddress(), model.getPort());
////		    			Thread t_j = new Thread(j);
////		    			t_j.start();
//		    	    	view.removePanel();
//			        }
//				});
//				break;
//			case 4:
//				button.addActionListener(new ActionListener() {  
//			        public void actionPerformed(ActionEvent e) {
////		        		new ScoreWindow();
//			        }
//				});
//				break;
		}
	}
}
