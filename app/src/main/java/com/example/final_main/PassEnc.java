package com.example.final_main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PassEnc {
    public PassEnc(){};

    public String encryptPassword(String password){
        String encryptedpassword = null;
        try
        {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            encryptedpassword = s.toString();
            return encryptedpassword;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public boolean matchPassword(String password,String enteredPassword){
        String encPassword = encryptPassword(enteredPassword);
        if(encPassword.equals(password))return true;
        else return false;

    }

}
