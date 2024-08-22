package com.example.helloworld.controller;

import com.example.helloworld.Entity.UserDetails;
import com.example.helloworld.Service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HelloWorldController {

    @Autowired
    private UserDetailService userDetailService;

    SecretKey myDesKey= null;
    Cipher desCipher;

    private static SecretKeySpec secretKey;
    private static byte[] key;

    private static final String ALGORITHM = "AES";

    @GetMapping("/hello")
    public String sendGreetings() {
        return "Hello, World!";
    }

    @PostConstruct
    public void postConstruct() throws NoSuchAlgorithmException, NoSuchPaddingException {
        prepareSecreteKey("TEX_HACKATHON");
    }



    // Save operation
    @GetMapping("/encrypt")
    public List<UserDetails> encryptPassword()
    {
        List<UserDetails> userDetails= userDetailService.fetchUserDetailsList();

        List<UserDetails> userDetailsList = userDetails.stream().map(e -> encryptData(e)).collect(Collectors.toList());
        userDetailsList.forEach(e-> userDetailService.saveUserDetails(e));
        return userDetailsList;
    }

    @GetMapping("/showUserDetails")
    public List<UserDetails> decryptPassword() throws NoSuchAlgorithmException {
        List<UserDetails> userDetails= userDetailService.fetchUserDetailsList();


        List<UserDetails> userDetailsList = userDetails.stream().map(e -> decryptPassword(e)).collect(Collectors.toList());
        return userDetailsList;
    }

    public UserDetails decryptPassword(UserDetails userDetails) {

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            userDetails.setUserPassword(new String(cipher.doFinal(Base64.getDecoder().decode(userDetails.getUserPassword()))));

        } catch (Exception e) {
            System.out.println("Exception"+e);
        }
        return userDetails;
    }
    public UserDetails encryptData(UserDetails userDetails) {

        String encryptedString  = null;
        String password = userDetails.getUserPassword();

        try {

       /*     // Creating byte array to store string
            byte[] text = password.getBytes();

            // Encrypting text
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            byte[] textEncrypted = desCipher.doFinal(text);*/

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            userDetails.setUserPassword(Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes("UTF-8"))));

        } catch (Exception e) {
            System.out.println("Exception"+e);
        }

        return userDetails;
    }

    public void prepareSecreteKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


}
