package com.pfa.lilkre.controller;


import com.pfa.lilkre.entities.dto.PaymentRequest;
import okhttp3.*;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseBody;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${appToken}")
    private String appToken;
    @Value("${appSecret}")
    private String appSecret;
    @Value("${developerTrackingId}")
    private String developerTrackingId;


    @PostMapping("/makePayment")
    @ResponseBody
    public ResponseEntity<String> makePayment(
            @RequestParam Long amount,
            PaymentRequest paymentRequest) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
       /* System.out.println("mediaType" + mediaType);
       System.out.println("amount" + amount);*/
        String jsonData = "{" +
                "\"app_token\": \"" + appToken
                + "\",\"app_secret\": \"" + appSecret
                + "\",\"accept_card\": \"true\"," +
                "\"amount\": " + amount + "," +
                "\"success_link\": \"" + paymentRequest.getSuccess_link() +
                "\",\"fail_link\": \"" + paymentRequest.getFail_link() + "\"," +
                "\"session_timeout_secs\": " + paymentRequest.getSession_timeout_secs()
                + ",\"developer_tracking_id\": \"" + developerTrackingId + "\"}";
        // System.out.println("jsonData" + jsonData);
        RequestBody body = RequestBody.create(mediaType, jsonData);
        // System.out.println("body" + body);
        Request request = new Request.Builder()
                .url("https://developers.flouci.com/api/generate_payment")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return ResponseEntity.status(response.code()).body(response.body().string());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }

    }

    @PostMapping("/verifyPayment/{id}")
    @ResponseBody
    public ResponseEntity<String> verifyPayment(@PathVariable("id") String paymentId) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://developers.flouci.com/api/verify_payment/" + paymentId;

        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("apppublic", appToken)
                .addHeader("appsecret", appSecret)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return ResponseEntity.ok(response.body().string());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }


}
