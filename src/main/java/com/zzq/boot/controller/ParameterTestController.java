package com.zzq.boot.controller;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张泽奇
 * @version 1.0
 */
@RestController
public class ParameterTestController {

    /**
     * @GetMapping("/car/{id}/owner/{username}") 浏览器发送 /car/1 就是第一辆汽车
     * @PathVariable 作用：将路径上的变量绑定到方法的参数上，
     *               如果使用的是 Map<String, String> 类型的方法参数，会将路径上的所有变量名和变量值填充到Map中
     *               {"pv":{"id":"3","username":"lisi"},"name":"lisi","id":3}
     * @RequestHeader 作用：获取请求头。@RequestHeader("User-Agent") 获取请求头中的User-Agent的信息
     *                如果方法的参数是 Map<String, String>, MultiValueMap<String, String>, or HttpHeaders 类型的，
     *                就是获取所有请求头的名称和值
     * @RequestParam 作用：获取请求参数，即 路径?之后的内容
     *               也可以使用  Map<String, String> or MultiValueMap<String, String> and a parameter name is not specified
     *               将请求路径之后的所有请求参数，封装到方法的参数中
     * @CookieValue 获取name对应的cookie的值，如果方法中的参数是javax.servlet.http.Cookie类型的话，将对应的cookie的完整信息保存
     * @return
     */
    @GetMapping("/car/{id}/owner/{username}")
    public Map<String,Object> getCar(@PathVariable("id") Integer id,
                                     @PathVariable("username") String name,
                                     @PathVariable Map<String, String> pv,
                                     @RequestHeader("User-Agent") String userAgent,
                                     @RequestHeader Map<String, String> headers,
                                     @RequestParam("age") Integer age,
                                     @RequestParam("interests") List<String> interests,
                                     @RequestParam Map<String, String> params,
                                     @CookieValue(name = "_ga", required = false) String _ga,
                                     @CookieValue(name = "_ga", required = false) Cookie cookie){
        Map<String, Object> map = new HashMap<>();
        //map.put("id",id);
        //map.put("name", name);
        //map.put("pv",pv);
        //map.put("userAgent", userAgent);
        //map.put("headers", headers);
        map.put("age",age);
        map.put("interests", interests);
        map.put("params", params);
        map.put("_ga",_ga);
        System.out.println(cookie);

        return map;
    }
}
