package com.swtestacademy;

import com.sun.jersey.core.util.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


/**
 * Create a new JIRA Issue
 */
public class JiraIntegration {
    public static void main(String[] args) throws Exception {
        String key = "YOUR_TICKET_KEY";
        String issueType = "Bug";
        String summary = "First bug Record";
        String description = "{code}DB.EXECUTION.ERROR{code} Got 500 as Htp Status Code";

        String auth = new String(Base64.encode("JIRA_USERNAME" + ":" + "JIRA_API_TOKEN"));

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://YOUR_URL.atlassian.net/rest/api/2/issue");
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic "+auth);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");

        String body = "{\"fields\":{\"project\":{\"key\":\""+key+"\"},\"summary\":\""+summary+"\",\"description\":\""+description+"\",\"issuetype\":{\"name\":\""+issueType+"\"}}}";
        StringEntity entity = new StringEntity(body);
        httpPost.setEntity(entity);

        HttpResponse response = client.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Body: " +responseBody);
        System.out.println("Status Code: " +statusCode);


    }

}