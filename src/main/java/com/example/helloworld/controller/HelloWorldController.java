package com.example.helloworld.controller;

import com.example.helloworld.Entity.UserDetails;
import com.example.helloworld.Service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HelloWorldController {

    @Autowired
    private UserDetailService userDetailService;

    @GetMapping("/hello")
    public String sendGreetings() {
        return "Hello, World!";
    }



    // Save operation
    @GetMapping("/encrypt")
    public List<UserDetails> encryptPassword()
    {
        List<UserDetails> userDetails= userDetailService.fetchUserDetailsList();
        return userDetails.stream().map(e-> encryptData(e)).collect(Collectors.toList());
    }


    public UserDetails encryptData(UserDetails userDetails) {

        String encryptedString  = null;
        String password = userDetails.getUserPassword();

        try {
            // Generating objects of KeyGenerator &
            // SecretKey
            KeyGenerator keygenerator
                    = KeyGenerator.getInstance("DES");
            SecretKey myDesKey = keygenerator.generateKey();

            // Creating object of Cipher
            Cipher desCipher;
            desCipher = Cipher.getInstance("DES");

            // Creating byte array to store string
            byte[] text = password.getBytes();

            // Encrypting text
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            byte[] textEncrypted = desCipher.doFinal(text);
            encryptedString = new String(textEncrypted);
            userDetails.setUserPassword(encryptedString);

        } catch (Exception e) {
            System.out.println("Exception");
        }

        return userDetails;
    }


}
