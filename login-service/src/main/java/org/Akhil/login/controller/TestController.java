package org.Akhil.login.controller;

import org.Akhil.common.model.Wallet;
import org.Akhil.login.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class TestController {
    @Autowired
    private WalletService walletService;
    private final ConcurrentHashMap<String,String> checkMap=new ConcurrentHashMap<>();
    @PostMapping("/checking")
    public boolean checking(@RequestParam Map<String,String> map){
        for(String key:map.keySet()){
            while(checkMap.containsValue(map.get(key))){
                  System.out.println("hold");
            }
                checkMap.put(key,map.get(key));

        }
        return true;
    }
    @PostMapping("/remove")
    public void remove(@RequestParam Map<String,String> map){
        for(String key:map.keySet()){
            if(checkMap.containsValue(map.get(key))){
               checkMap.remove(key);
            }

        }
    }
    @GetMapping("/addWallet")
    public ResponseEntity<Wallet> addWallet(){
        return ResponseEntity.ok(walletService.addWallet());
    }

}
