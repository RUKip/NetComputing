import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main { 	
	//TODO deadlocks and too many lemmings in a field

	
	/*   **************README**************
	 * 	
	 *	 How to run the program: First run it with any port i the command line argument 
	 * 	(for example "8800"). This will create a field. In order to create another field, 
	 * you need to run the program again with two ports in the command line: a new port, 
	 * and the old one (so "8801 8800"). The first argument is for the new field, and the
	 *  second one is the field that it connects to. 
	 * 
	 */
	
	public static String[] names;
	
	

	public static void main(String[] args) {		
		
			//create array of names
			createNamesList();
			
			//read command line arguments			
			int port = Integer.parseInt(args[0]);
			int nextPort = 0;
			if (args.length == 2)
				nextPort = Integer.parseInt(args[1]);
			
			//create field
			Field field = new Field(port, nextPort);
			Thread t_field = new Thread(field);
			t_field.start();
			
			//add fieldview
			FieldView fieldView = new FieldView(field);
			fieldView.setVisible(true);
			
			//create lemming and put it in the field
			Lemming lemming = new Lemming(field, names[new Random().nextInt(50)]);
			field.addLemming(lemming);
			Thread t_lemming = new Thread(lemming);
			t_lemming.start();
			
		
	}
	
	private static void createNamesList(){
		Scanner sc;
		try {
			sc = new Scanner(new File("names.txt"));
			List<String> lines = new ArrayList<String>();
			while (sc.hasNextLine())
				lines.add(sc.nextLine());
			names = lines.toArray(new String[0]);
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Failed loading the names file");
			e.printStackTrace();
		}
		
	}
	
}
