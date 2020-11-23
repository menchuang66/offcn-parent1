package mc.user.comp;


import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;


import untils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

@Component

public class SmsTemplate {
    @Value("${sms.host}")
    private String host;
    @Value("${sms.path}")
    private String path;
    @Value("${sms.methos:POST}")
    private String method;
    @Value("${sms.appcode}")
    private String appcode;

    public String sendCode(Map<String, String> querys) {
        HttpResponse response = null;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, String> bodys = new HashMap<String, String>();
        try {
            if (method.equalsIgnoreCase("get")) {
                response = HttpUtils.doGet(host, path, method, headers, querys);
            } else {
                response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            }
            String string = EntityUtils.toString(response.getEntity());

            return string;
        } catch (Exception e) {

            return "fail";
        }
    }
}