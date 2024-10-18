package org.Akhil.product.controller;

import org.Akhil.product.service.LoginClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class TestController {
    @Autowired
    private LoginClient loginClient;
    @PostMapping("/transaction2")
    public String product(@RequestBody Map<String,String> map){
        boolean flag=loginClient.checking(map);
        System.out.println("Processing");
        loginClient.remove(map);
        return "completed";
    }
}
