package com.magic3.magic3.token;

import com.magic3.magic3.model.Usera;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenGenerator {
    public String generateToken(Usera usera){
       String token = UUID.randomUUID().toString()+"_"+ System.currentTimeMillis()+ "_60000";
        usera.setToken(token);
        return token;
    }
}
