package org.Akhil.cart.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@FeignClient(name="LOGIN-SERVICE",url="http://localhost:9081")
public interface LoginClient {
    @PostMapping("/checking")
    public boolean checking(@RequestParam Map<String,String> map);
    @PostMapping("/remove")
    public void remove(@RequestParam Map<String,String> map);

}
