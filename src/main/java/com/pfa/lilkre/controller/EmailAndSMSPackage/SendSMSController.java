package com.pfa.lilkre.controller.EmailAndSMSPackage;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/SMS")
@CrossOrigin(origins = "*")
public class SendSMSController {

    @Value("${api_KeyInfobip}")
    private String ApiKeyinfobip;

    @PostMapping("/senSMS")
    public void sendSMS() throws IOException {
        //Implementation for brevo
           /* ApiClient defaultClient = Configuration.getDefaultApiClient();
            ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
            apiKey.setApiKey("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");
            TransactionalSmsApi api = new TransactionalSmsApi();
            api.getApiClient().setApiKey(apiKey.getApiKey());

            // Initialise l'API


            // Crée un objet SendTransacSms
            SendTransacSms sendTransacSms = new SendTransacSms();
            sendTransacSms.sender("Maison Moderne");
            sendTransacSms.recipient("58850421");
            sendTransacSms.content("YOUR_SMS_CONTENT");

            try {
                // Envoie le SMS
               SendSms result = api.sendTransacSms(sendTransacSms);
                System.out.println("SMS sent successfully. Response: " + result);
            } catch (ApiException e) {
                System.err.println("Exception when calling TransactionalSMSApi#sendTransacSms");
                e.printStackTrace();
                e.getResponseBody();
                e.getResponseHeaders();
            }*/
        //Implementation for Infobip
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"messages\"" +
                ":[{\"destinations\":[{\"to\":\"21658850421\"}]," +
                "\"from\":\"e-maison\",\"text\":\"Hello,\\n\\nThis is a test message from Infobip. Have a nice day!\"}]}");
        Request request = new Request.Builder()
                .url("https://k2y5n3.api.infobip.com/sms/2/text/advanced")
                .method("POST", body)
                .addHeader("Authorization", ApiKeyinfobip)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();
    }
}
