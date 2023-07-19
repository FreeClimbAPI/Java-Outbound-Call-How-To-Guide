package main.java.make_outbound_call;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.freeclimbapi.ApiClient;
import com.github.freeclimbapi.ApiException;
import com.github.freeclimbapi.Configuration;
import com.github.freeclimbapi.auth.*;
import com.github.freeclimbapi.models.*;
import com.github.freeclimbapi.DefaultApi;

@RestController
public class MakeOutboundCallController {

  public static void run() {
    String accountId = System.getenv("ACCOUNT_ID");
    String apiKey = System.getenv("API_KEY");
    String applicationId = System.getenv("APPLICATION_ID");
    String toNumber = System.getenv("TO_PHONE_NUMBER");
    String fromNumber = System.getenv("FREECLIMB_PHONE_NUMBER");

    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://www.freeclimb.com/apiserver");
    defaultClient.setAccountId(accountId);
    defaultClient.setApiKey(apiKey);

    DefaultApi apiInstance = new DefaultApi(defaultClient);

    MakeCallRequest makeCallRequest = new MakeCallRequest();
    makeCallRequest.setFrom(fromNumber);
    makeCallRequest.setTo(toNumber);
    makeCallRequest.setApplicationId(applicationId);

    try {
      apiInstance.makeACall(makeCallRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#sendAnSmsMessage");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

  }

  // set url in call connect field in FreeClimb dashboard
  @RequestMapping(value = { "/InboundCall" }, method = RequestMethod.POST)
  public String respond() throws Exception {
    PerclScript script = new PerclScript();
    Say sayHelloWorld = new Say().text("Hello World!");
    script.addCommand(sayHelloWorld);

    return script.toJson();
  }
}