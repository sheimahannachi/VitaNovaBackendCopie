package com.example.vitanovabackend.FTP;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.integration.ftp.gateway.FtpOutboundGateway;


public class FTPConfig {
    /*

    @Value("${ftp.host}")
    private String ftpHost;

    @Value("${ftp.port}")
    private int ftpPort;

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.password}")
    private String ftpPassword;

    @Value("${ftp.remoteDirectory}")
    private String remoteDirectory;

    @Bean
    public DefaultFtpSessionFactory ftpSessionFactory() {
        DefaultFtpSessionFactory factory = new DefaultFtpSessionFactory();
        factory.setHost(ftpHost);
        factory.setPort(ftpPort);
        factory.setUsername(ftpUsername);
        factory.setPassword(ftpPassword);
        return factory;
    }

    @Bean
    public FtpOutboundGateway ftpOutboundGateway(DefaultFtpSessionFactory ftpSessionFactory) {
        FtpOutboundGateway gateway = new FtpOutboundGateway(ftpSessionFactory, "put", "remoteDirectory");
        gateway.setFileExistsMode(FileExistsMode.REPLACE); // Spécifiez le mode de remplacement si nécessaire
        gateway.setOutputChannelName("uploadedFilesChannel"); // Nom du canal de sortie
        return gateway;
    }
    */
}

