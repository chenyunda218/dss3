package mo.gov.dsscu.dss3.utils;

import java.io.StringReader;

import javax.net.ssl.SSLContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

@Component
public class AdLoginHelper {

  private String baseUrl;

  public AdLoginHelper() {
    this.baseUrl = "https://dschew03/LDAP/ldap.asmx";
  }

  public static CloseableHttpClient createHttpClientSkipAllVerification() throws Exception {
    SSLContext sslContext = SSLContexts.custom()
        .loadTrustMaterial(null, new TrustAllStrategy())
        .build();

    // 使用NoopHostnameVerifier跳过主机名验证
    SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
        sslContext,
        NoopHostnameVerifier.INSTANCE);

    return HttpClients.custom()
        .setSSLSocketFactory(sslSocketFactory)
        .build();
  }

  private String bodyBuilder(String username, String password) {
    String body = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + //
        "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
        +
        "  <soap:Body>\r\n" + //
        "    <isAuthenticated xmlns=\"http://tempuri.org/\">\r\n" + //
        "      <username>" + username + "</username>\r\n" + //
        "      <pwd>" + password + "</pwd>\r\n" + //
        "    </isAuthenticated>\r\n" + //
        "  </soap:Body>\r\n" + //
        "</soap:Envelope>";
    return body;
  }

  public Boolean login(String username, String password) {
    try (CloseableHttpClient httpClient = AdLoginHelper.createHttpClientSkipAllVerification()) {
      HttpPost request = new HttpPost(this.baseUrl);
      // 添加请求头
      request.addHeader("Content-Type", "text/xml; charset=utf-8");
      request.setEntity(new StringEntity(bodyBuilder(username, password)));
      HttpResponse response = httpClient.execute(request);

      String responseBody = EntityUtils.toString(response.getEntity());
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new InputSource(new StringReader(responseBody)));
      doc.getDocumentElement().normalize();
      Node envelope = doc.getElementsByTagName("soap:Envelope")
          .item(0).getChildNodes()
          .item(0).getChildNodes()
          .item(0).getChildNodes().item(0);
      return "true".equals(envelope.getTextContent());
    } catch (Exception e) {
      return false;
    }
  }

}
