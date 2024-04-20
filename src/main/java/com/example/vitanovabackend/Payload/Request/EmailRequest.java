package com.example.vitanovabackend.Payload.Request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class EmailRequest {
    private String to;
    private String subject;
    private String text;
    private byte[] attachmentData;
    private String attachmentFilename;
    private String attachmentPath;

    public EmailRequest() {
    }

    public EmailRequest(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

}
