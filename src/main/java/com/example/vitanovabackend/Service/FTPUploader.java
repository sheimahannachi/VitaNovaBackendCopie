package com.example.vitanovabackend.Service;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

@Service
public class FTPUploader {
    final String server = "192.168.208.22";
    final int port = 21;
  final  String username = "ftpuser";
   final String password = "hammeda";
    final String remoteDirPath = "/home/ftpuser/images";
    final String LocalDirPath = "C:\\Users\\hamad\\OneDrive\\Desktop\\FTPFOLDER";


    public boolean checkFTPConnectivity() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            return ftpClient.login(username, password);
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Connection failed
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void Upload(String imageFileName){
        FTPClient ftpClient = new FTPClient();
        try {
            // Connect and login to the FTP server
            ftpClient.connect(server);
            ftpClient.login(username, password);
            System.out.println(ftpClient.printWorkingDirectory());
            // Change working directory
            ftpClient.changeWorkingDirectory(remoteDirPath);

            // Upload a file
            InputStream inputStream = new FileInputStream(LocalDirPath+"//"+imageFileName);
            ftpClient.storeFile(imageFileName, inputStream);

            // List the files in the current directory
            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile file : files) {
                System.out.println("File: " + file.getName());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                // Logout and disconnect from the FTP server
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }



    public InputStream downloadFile(String remoteFileName) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(server, port);
        ftpClient.login(username, password);
        ftpClient.changeWorkingDirectory(remoteDirPath);

        FTPFile[] files = ftpClient.listFiles(remoteFileName);
        if (files.length == 0) {
            throw new FileNotFoundException("File not found: " + remoteDirPath+"/"+remoteFileName);
        }
        return ftpClient.retrieveFileStream(remoteFileName);
    }
    }

