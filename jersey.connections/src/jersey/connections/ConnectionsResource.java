package jersey.connections;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;


// Will map the resource to the URL todos
@Path("/connections")
public class ConnectionsResource {

        // Allows to insert contextual objects into the class,
        // e.g. ServletContext, Request, Response, UriInfo
        @Context
        UriInfo uriInfo;
        @Context
        Request request;

        // Return the list of todos to the user in the browser
        @GET
        @Produces(MediaType.TEXT_XML)
        public List<Connection> getTodosBrowser() {
                List<Connection> todos = new ArrayList<Connection>();
                todos.addAll(ConnectionDao.instance.getModel().values());
                return todos;
        }

        // Return the list of todos for applications
        @GET
        @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        public List<Connection> getTodos() {
                List<Connection> todos = new ArrayList<Connection>();
                todos.addAll(ConnectionDao.instance.getModel().values());
                return todos;
        }


        // retuns the number of todos
        // Use http://localhost:8080/com.vogella.jersey.todo/rest/todos/count
        // to get the total number of records
        @GET
        @Path("/count")
        @Produces(MediaType.TEXT_PLAIN)
        public String getCount() {
                int count = ConnectionDao.instance.getModel().size();
                return String.valueOf(count);
        }
        
		@Path("/html")
        @GET
        @Produces(MediaType.TEXT_HTML)
        public String displayHTML() {
        	
        	List<Connection> connections = new ArrayList<Connection>();
        	connections.addAll(ConnectionDao.instance.getModel().values());

        	String body = "<body><h1>" + 
        					"The following terminals are connected" +  
        					"</body></h1>" + "</html> ";	
        	        	
        	for(Connection c : connections){
        		body = body + "<body>" + "ID: " + 
        				c.getId() + " Address: " + c.getAddress() + "<br>" +
        				" Summary: " + c.getSummary() +
        				"</body>" + "</html> " + "<br><br>";
        	}

        	return  body ;
        }
        

//        @POST
//        @Produces(MediaType.TEXT_HTML)
//        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//        public void newTodo(@FormParam("id") String id,
//                        @FormParam("summary") String summary,
//                        @FormParam("description") String description,
//                        @Context HttpServletResponse servletResponse) throws IOException {
////                Todo todo = new Todo(id, summary);
//                Connection c = new Connection(id.toString(), Integer.parseInt(summary));
////                if (description != null) {
////                        todo.setDescription(description);
////                }
//                ConnectionDao.instance.getModel2().put(id, c);
//
//                servletResponse.sendRedirect("../create_todo.html");
//        }
        // Defines that the next path parameter after todos is
        // treated as a parameter and passed to the TodoResources
        // Allows to type http://localhost:8080/com.vogella.jersey.todo/rest/todos/1
        // 1 will be treaded as parameter todo and passed to TodoResource
        @Path("{todo}")
        public ConnectionResource getTodo(@PathParam("todo") String id) {
                return new ConnectionResource(uriInfo, request, id);
        }

}