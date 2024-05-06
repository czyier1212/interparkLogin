import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class InterparkLogin {
    public static void main(String[] args) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        String userId = "your_user_id";
        String userPwd = "your_password";

        String postData = "frUID=" + URLEncoder.encode(userId, "UTF-8") +
                          "&frPWD=" + URLEncoder.encode(userPwd, "UTF-8");

        HttpPost request = new HttpPost("https://ticket.interpark.com/gate/TPLoginCheck_Return.asp");
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setEntity(new StringEntity(postData));

        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String result = EntityUtils.toString(entity);
            System.out.println("Response: " + result);

            if ("0".equals(result)) {
                System.out.println("안녕하세요. [" + userId + "]님!");
            } else {
                System.err.println("로그인에 실패하였습니다.ㅠㅠ");
            }
        }
    }
}
