package Interface;


public class Menu {
	
	public static void main(String [] args){
		
		new Menu();
		
		
	}
	public Menu(){
		MenuModel model = new MenuModel();
		MenuView view = new MenuView(model);
		
		view.setVisible(true);
		
	
	}

}
