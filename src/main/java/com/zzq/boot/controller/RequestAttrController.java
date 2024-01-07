package com.zzq.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/params")
    public String testParam(Map<String,Object> map,
                            Model model,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        map.put("mapParams", "World map");
        model.addAttribute("modelParams", "model Param");
        request.setAttribute("requestParams", "requestMessage");
        response.addCookie(new Cookie("responseParams","responseCookie"));
        return "forward:/success";
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
    public Map success(@RequestAttribute(value = "msg" , required = false) String msg,
                          @RequestAttribute(value = "code", required = false) Integer code,
                          HttpServletRequest request) {
        Object msg1 = request.getAttribute("msg");
        Map<String ,Object> map = new HashMap<>();
        map.put("requestMethod_msg", msg1);
        map.put("annotation_msg", msg);

        // 保存 /params 请求中的参数的值
        map.put("params.map", request.getAttribute("mapParams"));
        map.put("params.model", request.getAttribute("modelParams"));
        map.put("params.request", request.getAttribute("requestParams"));


        return map;
    }
}
