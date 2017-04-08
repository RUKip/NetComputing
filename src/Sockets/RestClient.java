package Sockets;
import java.net.URI;
import java.util.UUID;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class RestClient {

	
	public void update(String address, UUID id){
		
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget service = client.target(getBaseURI());
		
		
		Connection own = new Connection(id.toString() , address);
		own.setSummary("This is a new connection");
		Response response = service.path("rest").path("connections").path(own.getId()).request(MediaType.APPLICATION_XML).put(Entity.entity(own,MediaType.APPLICATION_XML),Response.class);
        System.out.println(response.getStatus());
	}
	
	

	public void delete(String id){
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget service = client.target(getBaseURI());
        service.path("rest").path("connections/"+id).request().delete();
		
	}
	
  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://145.97.173.179:8080/jersey.connections").build();
  }
  
  
  
  
  
	
//	public void update() {
//
//        ClientConfig config = new ClientConfig();
//        Client client = ClientBuilder.newClient(config);
//        WebTarget service = client.target(getBaseURI());
//
////        // create one todo
//        Connection todo = new Connection("2", "Blabla");
//        Response response = service.path("rest").path("todos").path(todo.getId()).request(MediaType.APPLICATION_XML).put(Entity.entity(todo,MediaType.APPLICATION_XML),Response.class);
////
////        // Return code should be 201 == created resource
//        System.out.println(response.getStatus());
//
//        // Get the Todos
//        System.out.println(service.path("rest").path("todos").request().accept(MediaType.TEXT_XML).get(String.class));
//
////        // Get JSON for application
////        System.out.println(service.path("rest").path("todos").request().accept(MediaType.APPLICATION_JSON).get(String.class));
//
//        // Get XML for application
//        System.out.println(service.path("rest").path("todos").request().accept(MediaType.APPLICATION_XML).get(String.class));
//
//        //Get Todo with id 1
//        Response checkDelete = service.path("rest").path("todos/1").request().accept(MediaType.APPLICATION_XML).get();
//
//        //Delete Todo with id 1
////        service.path("rest").path("todos/1").request().delete();
//
//        //Get get all Todos id 1 should be deleted
//        System.out.println(service.path("rest").path("todos").request().accept(MediaType.APPLICATION_XML).get(String.class));
//
//        //Create a Todo
//        Form form =new Form();
//        form.param("id", "4");
//        form.param("summary","Demonstration of the client lib for forms");
//        response = service.path("rest").path("todos").request().post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
//        System.out.println("Form response " + response.getStatus());
//
//        //Get all the todos, id 4 should have been created
//        System.out.println(service.path("rest").path("todos").request().accept(MediaType.APPLICATION_XML).get(String.class));
//
//     // create one todo
//        Connection todo2 = new Connection("2", "192.168.0.10");
//        todo2.setAddress("Bullshit");
//        Response response2 = service.path("rest").path("todos").path(todo2.getId()).request(MediaType.APPLICATION_XML).put(Entity.entity(todo2,MediaType.APPLICATION_XML),Response.class);
//        System.out.println(response2.getStatus());
//        
//        Connection todo3 = new Connection("3", "192.168.0.10");
//        todo3.setAddress("Even more bullshit");
//        response2 = service.path("rest").path("todos").path(todo3.getId()).request(MediaType.APPLICATION_XML).put(Entity.entity(todo3,MediaType.APPLICATION_XML),Response.class);
//        System.out.println(response2.getStatus());
//                  
//}
  
  
  
}