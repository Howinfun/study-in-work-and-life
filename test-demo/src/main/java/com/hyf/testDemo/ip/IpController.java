package com.hyf.testDemo.ip;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/ip")
@RestController
public class IpController {

    @GetMapping("/")
    public IpVo getIp(HttpServletRequest request){
        String ip1 =  IpUtils.getIpAddress(request);
        IpVo ip2 =  IpUtil.getIpVo(ip1);
        ip2.setSourceIp(ip1);
        return ip2;
    }
}
