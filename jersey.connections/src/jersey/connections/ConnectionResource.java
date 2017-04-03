package jersey.connections;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

@Path("/Connection")
public class ConnectionResource {
        @Context
        UriInfo uriInfo;
        @Context
        Request request;
        String id;
        public ConnectionResource(UriInfo uriInfo, Request request, String id) {
                this.uriInfo = uriInfo;
                this.request = request;
                this.id = id;
        }

//        // for the browser
        @GET
        @Produces(MediaType.TEXT_XML)
        public Connection getConnectionHTML() {
                Connection Connection = ConnectionDao.instance.getModel().get(id);
                if(Connection==null)
                        throw new RuntimeException("Get: Connection with " + id +  " not found");
                return Connection;
        }

        @PUT
        @Consumes(MediaType.APPLICATION_XML)
        public Response putConnection(JAXBElement<Connection> Connection) {
                Connection c = Connection.getValue();
                return putAndGetResponse(c);
        }

        @DELETE
        public void deleteConnection() {
                Connection c = ConnectionDao.instance.getModel().remove(id);
                if(c==null)
                        throw new RuntimeException("Delete: Connection with " + id +  " not found");
        }

        private Response putAndGetResponse(Connection Connection) {
                Response res;
                if(ConnectionDao.instance.getModel().containsKey(Connection.getId())) {
                        res = Response.noContent().build();
                } else {
                        res = Response.created(uriInfo.getAbsolutePath()).build();
                }
                ConnectionDao.instance.getModel().put(Connection.getId(), Connection);
                return res;
        }



}