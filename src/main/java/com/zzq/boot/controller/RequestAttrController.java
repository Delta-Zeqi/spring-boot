package com.zzq.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RequestAttrController {

    @GetMapping("/goto")
    public String goToPage(HttpServletRequest request) {
        request.setAttribute("msg", "成功了....");
        request.setAttribute("code", 200);
        return "forward:/success"; //转发到 /success请求
    }

    /**
     * 请求转发是同一次请求
     * @param msg
     * @param code
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/success")
    public Map success(@RequestAttribute("msg") String msg,
                          @RequestAttribute("code") Integer code,
                          HttpServletRequest request) {
        Object msg1 = request.getAttribute("msg");
        Map<String ,Object> map = new HashMap<>();
        map.put("requestMethod_msg", msg1);
        map.put("annotation_msg", msg);
        return map;
    }
}
