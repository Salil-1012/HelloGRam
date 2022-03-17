
import com.vmm.JHTTPServer;
import java.io.IOException;
import java.util.Properties;


public class Serverwithparam extends JHTTPServer {
    
    public Serverwithparam(int port) throws Exception {
        super(port);
    }
    @Override
    public Response serve(String uri,String method,Properties header,Properties parms,Properties files)
    {
        Response res=new Response(HTTP_OK,"text/plain","hello from server");
        if(uri.contains("sendname"))
        {
            String name=parms.getProperty("name");
            res=new Response(HTTP_OK,"text/plain","name is"+name);
        }
        return res;
    }
}
