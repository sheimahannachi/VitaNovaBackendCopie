package com.example.vitanovabackend.FTP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.ftp.gateway.FtpOutboundGateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;

@Configuration
@EnableIntegration
public class IntegrationConfig {
/*
    @Bean
    public MessageChannel uploadedFilesChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessagingTemplate messagingTemplate() {
        return new MessagingTemplate(uploadedFilesChannel());
    }

*/
}





