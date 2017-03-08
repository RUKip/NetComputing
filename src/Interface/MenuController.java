package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Sockets.Client;


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
			        	view.removePanel();
			        }
				});
				break;
			case 1:
				button.addActionListener(new ActionListener() {  
			        public void actionPerformed(ActionEvent e) {
		        		new Client(model.getAddress(), model.getPort()).run();
		    	    	view.removePanel();
			        }
				});
//			case 2:
//				button.addActionListener(new ActionListener() {  
//			        public void actionPerformed(ActionEvent e) {
////		        		Spectator s = new Spectator(model.getAddress(), model.getPort());
////		    			Thread t_s = new Thread(s);
////		    			t_s.start();
//		    	    	view.removePanel();
//			        }
//				});
//				break;
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
