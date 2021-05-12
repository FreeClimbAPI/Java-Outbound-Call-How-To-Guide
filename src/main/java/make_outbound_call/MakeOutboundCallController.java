package main.java.make_outbound_call;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.vailsys.freeclimb.api.FreeClimbClient;
import com.vailsys.freeclimb.api.FreeClimbException;
import com.vailsys.freeclimb.percl.Say;
import com.vailsys.freeclimb.percl.PerCLScript;
import com.vailsys.freeclimb.api.call.Call;
import com.vailsys.freeclimb.api.call.CallOptions;

@RestController
public class MakeOutboundCallController {
  private static FreeClimbClient client;

  public static void run() {
    String accountId = System.getenv("ACCOUNT_ID");
    String apiKey = System.getenv("API_KEY");
    String applicationId = System.getenv("APPLICATION_ID");
    String toNumber = System.getenv("TO_PHONE_NUMBER");
    String fromNumber = System.getenv("FREECLIMB_PHONE_NUMBER");
    try {
      // Create FreeClimbClient object
      client = new FreeClimbClient(accountId, apiKey);
    } catch (FreeClimbException e) {
      // handle exception
    }

    outDial(fromNumber, toNumber, applicationId);
  }

  public static void outDial(String fromNumber, String toNumber, String applicationId) {
    try {
      CallOptions callOptions = new CallOptions();
      callOptions.setCallConnectUrl("http://example.com/InboundCall");
      // Create outgoing call from FreeClimb
      Call call = client.calls.create(toNumber, fromNumber, applicationId, callOptions);
    } catch (FreeClimbException ex) {
      // Exception throw upon failure
      System.out.print(ex);
    }
  }

  // set url in call connect field in FreeClimb dashboard
  @RequestMapping("/InboundCall")
  public String respond() {
    PerCLScript script = new PerCLScript();
    Say say = new Say("You just made a call with the FreeClimb API!");
    // Add PerCL play script to PerCL container
    script.add(say);
    return script.toJson();
  }
}