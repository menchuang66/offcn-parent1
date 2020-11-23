package untils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpUtils1 {
    public static void main(String[] args) throws ClientProtocolException, IOException {
        //1������һ��httpClient
        HttpClient httpClient = new DefaultHttpClient();
        //2������һ������
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        //3���������󣬲��յ���Ӧ
        HttpResponse response = httpClient.execute(httpGet);
        //4����ȡ��Ӧ������
        HttpEntity entity = response.getEntity();
        //5���õ���Ӧ���ַ�������
        String string = EntityUtils.toString(entity);
        System.out.println(string);
    }
}
