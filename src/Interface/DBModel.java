package Interface;

import Database.Employee;

public class DBModel {
	private Employee employee;
	private String firstName,lastName, address,salary;

	public void setEmployeeFirstName(String firstName){
		this.employee.setFirstName(firstName);
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	public void setSalary(String salary){
		this.salary = salary;
	}
	
	
	
	/*TODO: Add all the functions to the model to set the
	 * details of the employee 
	 */
	
}

