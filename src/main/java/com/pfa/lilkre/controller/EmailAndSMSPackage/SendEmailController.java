package com.pfa.lilkre.controller.EmailAndSMSPackage;

import com.pfa.lilkre.entities.dto.Code_Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.*;

import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/Email")
@CrossOrigin(origins = "*")
public class SendEmailController {

    @Value("${api-key}")
    private String api_Key;

    @PostMapping("/senEmail")
    public void senEmail(Code_Info code_Info) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        // Configure API key authorization: api-key
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(api_Key); // Remplacez YOUR_API_KEY par votre clé API Sendinblue

        try {
            TransactionalEmailsApi api = new TransactionalEmailsApi();
            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();

            // Configuration de l'expéditeur
            SendSmtpEmailSender sender = new SendSmtpEmailSender();
            sender.setEmail("abdaziz.charfi@gmail.com");
            sender.setName("Charfi Abdelaziz");
            sendSmtpEmail.setSender(sender);
            // Configuration des destinataires
            List<SendSmtpEmailTo> toList = new ArrayList<>();
            SendSmtpEmailTo to = new SendSmtpEmailTo();
            to.setEmail(code_Info.getEmail());
            to.setName(code_Info.getNomDestinateur());
            toList.add(to);
            sendSmtpEmail.setTo(toList);
            String confirmationEmail = EmailTemplate.generateConfirmationEmail(String.valueOf(code_Info.getCode()), code_Info.getNomDestinateur());
            // Configuration du contenu HTML
            sendSmtpEmail.setHtmlContent(confirmationEmail);

            //pour utiliser le template id
            //    sendSmtpEmail.setTemplateId(TEMPLATE_ID); // Replace TEMPLATE_ID with your actual template ID
            // Configuration de l'objet de l'e-mail
            sendSmtpEmail.setSubject("Votre code de vérification de M.Maison est " + code_Info.getCode());

            // Envoi de l'e-mail transactionnel
            CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
