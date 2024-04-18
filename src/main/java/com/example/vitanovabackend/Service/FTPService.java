package com.example.vitanovabackend.Service;


import com.example.vitanovabackend.DAO.Entities.FTPServe;
import com.example.vitanovabackend.DAO.Repositories.FTPServeRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.ftp.gateway.FtpOutboundGateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.integration.ftp.gateway.FtpOutboundGateway;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class FTPService implements FTPIService {
/*
    @Autowired
    private FtpOutboundGateway ftpOutboundGateway;



    public void uploadFile(File file) {
        Message<File> message = MessageBuilder.withPayload(file).build();
        ftpOutboundGateway.handleMessage(message);
    }*/
}