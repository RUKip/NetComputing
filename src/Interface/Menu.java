package Interface;


public class Menu {

	public Menu(){
		System.out.println("Started menu");
		MenuModel model = new MenuModel();
		MenuView view = new MenuView(model);
		
		view.setVisible(true);
		
	
	}

}
