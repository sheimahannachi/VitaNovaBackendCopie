package com.example.vitanovabackend.Service;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
@Service
public class MiscService {
    //key for username encryption for url
    private final String key="VitaNovaVitaNovaVitaNova";
    public String decrypt(String encryptedText) {
        try {
            byte[] encryptedBytes = DatatypeConverter.parseBase64Binary(encryptedText);

            DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey secretKey = keyFactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            String decryptedText = new String(decryptedBytes);

            return decryptedText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    }


