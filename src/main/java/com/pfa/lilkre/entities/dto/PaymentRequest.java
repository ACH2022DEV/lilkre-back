package com.pfa.lilkre.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private int session_timeout_secs = 1200;
    private String success_link = "http://localhost:4200/success";
    private String fail_link = "http://localhost:4200/failed";

}


