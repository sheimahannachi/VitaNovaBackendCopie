package com.example.vitanovabackend.Service;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;
import java.util.Optional;

@Service
public class FTPUploader {
    final String server = "192.168.218.133";
    final int port = 21;
    final String username = "ftpuser";
    final String password = "hammeda";
    final String remoteDirPath = "/home/ftpuser/uploads";
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


    public boolean uploadImageToFTP(File imageFile) {
        FTPClient ftpClient = new FTPClient();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(imageFile);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = outputStream.toByteArray();
            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);

            // Upload the encoded image as a text file to FTP server
            ftpClient.connect(server, port);
            if (ftpClient.login(username, password)) {
                ByteArrayInputStream textStream = new ByteArrayInputStream(encodedImage.getBytes());
                ftpClient.storeFile(remoteDirPath + "/" + imageFile.getName() + ".txt", textStream);
                textStream.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public Optional<File> downloadImageFromFTP(String filename) {
                FTPClient ftpClient = new FTPClient();
                OutputStream outputStream = null;
                try {
                    ftpClient.connect(server, port);
                    if (ftpClient.login(username, password)) {
                        outputStream = new FileOutputStream(LocalDirPath + "/" + filename+".txt");
                        ftpClient.retrieveFile(remoteDirPath + "/" + filename + ".txt", outputStream);
                        System.out.println("downloading file : "+ remoteDirPath+"/"+filename+".txt");
                        return Optional.of(new File(LocalDirPath + "/" + filename+".txt"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        ftpClient.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return Optional.empty();
            }



            public byte[] decodeImageFromFile(File textFile) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(textFile));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();
                    byte[] decodedBytes = Base64.getDecoder().decode(stringBuilder.toString());
                    return decodedBytes;
                } catch (IOException e) {
                    e.printStackTrace();
        }
        return null;
    }
}




