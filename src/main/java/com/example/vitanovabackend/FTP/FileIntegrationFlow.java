package com.example.vitanovabackend.FTP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ftp.gateway.FtpOutboundGateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
@IntegrationComponentScan
public class FileIntegrationFlow {
/*
    @Autowired
    private MessageChannel inputChannel;

    @Autowired
    private FtpOutboundGateway ftpOutboundGateway;

    @ServiceActivator(inputChannel = "inputChannel")
    public void handleFile(Message<File> message) {
        ftpOutboundGateway.handleMessage(message);
    }

    @MessagingGateway(defaultRequestChannel = "inputChannel")
    public interface FileGateway {
        void upload(File file);
    }
*/

}
