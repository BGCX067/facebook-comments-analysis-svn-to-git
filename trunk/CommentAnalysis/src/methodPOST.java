/*import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.*;  
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class methodPOST {

		methodPOST() {

    HttpClient client = new DefaultHttpClient();
    client.getParams().setParameter("http.useragent", "Test Client");

    BufferedReader br = null;

    HttpPost method = new HttpPost("http://search.yahoo.com/search");
    method.addHeader("p", "\"java2s\"");

    try{
      HttpResponse returnCode = client.execute(method);

      if(returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
        System.err.println("The Post method is not implemented by this URI");
        // still consume the response body
        method.getResponseBodyAsString();
      } else {
        br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
        String readLine;
        while(((readLine = br.readLine()) != null)) {
          System.err.println(readLine);
      }
      }
    } catch (Exception e) {
      System.err.println(e);
    } finally {
      method.releaseConnection();
      if(br != null) try { br.close(); } catch (Exception fe) {}
    }

  }
}*/