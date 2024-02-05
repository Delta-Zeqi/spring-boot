package com.zzq.boot.controller;

import com.zzq.boot.bean.Person;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.util.UrlPathHelper;

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
     * 数据绑定：页面提交的请求数据（GET、POST）都可以和对象属性进行绑定
     * @param person
     * @return
     */
    @PostMapping("/saveuser")
    public Person saveUser(Person person) {
        return person;
    }


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

    @PostMapping("/save")
    public Map postMethod(@RequestBody String content){
        Map<String, Object> map = new HashMap<>();
        map.put("content", content);
        return map;
    }

    //1.语法： href="/cars/sell;low=34;brand=byd,audi,yd"
    //2.SpringBoot默认是禁用了矩阵变量的功能
    //      手动开启: 原理。
    //      对于路径的处理。
    //      使用 WebMvcAutoConfiguration --->
    //      configurePathMatch(PathMatchConfigurer configurer) --->UrlPathHelper进行解析
    //      removeSemicolonContent（移除分号内容），设置为 false 让其支持矩阵变量的。
    // 所以需要我们自定义
    //3.矩阵变量必须有url路径变量才能被解析
    //@GetMapping("/cars/sell") //这样写，在自定义了 UrlPathHelper 之后，也是不起作用的，会报错”找不到路径“
    //原因：矩阵变量是绑定在路径中的，不能直接写 sell
    @GetMapping("/cars/{path}")
    public Map carsSell(@MatrixVariable("low") Integer low,
                        @MatrixVariable("brand") List<String> brands,
                        @PathVariable("path") String path){
        Map<String, Object> map = new HashMap<>();
        map.put("low",low);
        map.put("brand", brands);
        map.put("path", path); //真实的路径：就是 /cars/sell
        return map;
    }

    //@MatrixVariable可以获取路径中的矩阵变量，那么也引来了另一个问题
    //href="/boss/1;age=20/2;age=10" 不同路径中有同名的矩阵变量怎么办
    //@MatrixVariable 中的pathVar可以解决
    /*@MatrixVariable源码：
    *The name of the URI path variable where the matrix variable is located,
    * if necessary for disambiguation (e.g. a matrix variable with the same
    * name present in more than one path segment).
    * String pathVar() default ValueConstants.DEFAULT_NONE;
    * */
    @GetMapping("/boss/{bossId}/{empId}")
    public Map getBoss(@MatrixVariable(value = "age", pathVar = "bossId") Integer bossAge,
                       @MatrixVariable(value = "age", pathVar = "empId") Integer empAge){
        Map<String, Object> map = new HashMap<>();
        map.put("bossAge", bossAge);
        map.put("empAge", empAge);
        return map;
    }



}
