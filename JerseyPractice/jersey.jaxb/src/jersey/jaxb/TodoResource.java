package jersey.jaxb;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/todo")
public class TodoResource {
	    // This method is called if XMLis request
	    @GET
	    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	    public Todo getXML() {
	            Todo todo = new Todo();
	            todo.setSummary("This is my first todo");
	            todo.setDescription("This is my first todo");
	            return todo;
	    }

        // This can be used to test the integration with the browser
        @GET
        @Produces( { MediaType.TEXT_XML })
        public Todo getHTML() {
                Todo todo = new Todo();
                todo.setSummary("This is my first todo");
                todo.setDescription("This is my first todo");
                return todo;
        }
//        
//        
//        // This method is called if HTML is request
//        @GET
//        @Produces(MediaType.TEXT_HTML)
//        public String sayHtmlHello() {
//          return "<html> " + "<title>" + "Hello Stupid" + "</title>"
//              + "<body><h1>" + "Hello stupider" + "</body></h1>" + "</html> ";
//        }
        
}