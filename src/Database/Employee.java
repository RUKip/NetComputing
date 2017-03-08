package Database;

public class Employee {
	
	private String firstName;
	private String secondName;
	private String address; 
	private String phoneNumber; 
	private String position;


	private String salary;
		
	public Employee(){}

	public Employee(String firstName){
		this.firstName = firstName;
	}

	public Employee(String firstName, String secondName) {
		super();
		this.firstName = firstName;
		this.secondName = secondName;
	}
	
	public Employee(String firstName, String secondName, String address, String phoneNumber, String position,
			String salary) {
		super();
		this.firstName = firstName;
		this.secondName = secondName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.position = position;
		this.salary = salary;
	}
	
	public Employee(String firstName, String secondName, String address, String phoneNumber, String position) {
		super();
		this.firstName = firstName;
		this.secondName = secondName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.position = position;
	}
	
	public String toString() {
		return "Employee name: " + this.firstName + "; " + 
				"Employee address: " + this.address + "; ";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}


	
	
	
	
}
