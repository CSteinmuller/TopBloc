package TopBlocexcel.TopBlocexcel;

import java.io.IOException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

/**
 * Main Class
 */

public class App
{
    public static void main(String[] args) throws IOException
    {
		ExcelReader dataone = new ExcelReader();
		ExcelReader datatwo = new ExcelReader();
       
    	dataone.readXcell("../../Downloads/Data1.xlsx");
    	datatwo.readXcell("../../Downloads/Data2.xlsx");
	    
		ExcelReader datathree = dataone.merge(datatwo);
        String json = datathree.MakeJSON();
    	CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://34.239.125.159:5000/challenge");
        StringEntity post = new StringEntity(json);
        httpPost.setEntity(post);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = client.execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode());
        client.close();
    }
}
