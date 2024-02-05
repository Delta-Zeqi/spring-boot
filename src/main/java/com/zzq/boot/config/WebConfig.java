package com.zzq.boot.config;

import com.zzq.boot.bean.Pet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

/**
 * @author 张泽奇
 * @version 1.0
 */
@Configuration(proxyBeanMethods = false)
public class WebConfig /*implements WebMvcConfigurer*/ {

    //扩展点：将_method 这个属性名改成自己想要的
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        hiddenHttpMethodFilter.setMethodParam("_m");
        return hiddenHttpMethodFilter;
    }

    /**
     * 对于使用矩阵变量，要把 UrlPathHelper 的 removeSemicolonContent 设置为 false
     * 但是 SpringBoot 中没有与之对应的配置
     * 所以需要我们自己自定义规则，根据 SpringBoot 官方文档的提示
     * 不用@EnableWebMvc注解。使用 @Configuration + WebMvcConfigurer 自定义规则
     * 那么就衍生出了 两种方式解决这个问题
     * 1. 直接让 这个配置类 实现 WebMvcConfigurer 接口，重写 configurePathMatch 方法
     * 2. 使用 @Bean 将重写了 configurePathMatch 方法的 WebMvcConfigurer 类型的实例 返回，将其注册到 Spring IOC 容器中
     *
     * 为什么 我们可以只实现接口中 的一个方法就行？
     * 复习javase 基础知识：java8 中接口可以写 默认方法 了，所以不用在实现的时候，必须将所有的方法实现了
     */

    //这个就是第一种方式：配置类 实现 WebMvcConfigurer, 重写 configurePathMatch 方法
    /*@Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        //不移除 ; 后面的内容。矩阵变量功能就可以生效了
        urlPathHelper.setRemoveSemicolonContent(false);
        //将 url路径小帮手 放入配置中
        configurer.setUrlPathHelper(urlPathHelper);
    }*/

    //第二种方式：使用 @Baen 将 重写之后的 WebMvcConfigurer 注册到 容器中
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                UrlPathHelper urlPathHelper = new UrlPathHelper();
                urlPathHelper.setRemoveSemicolonContent(false);
                configurer.setUrlPathHelper(urlPathHelper);
            }

            /**
             * 推理：在SpringBoot启动的时候，会将 WebMvcConfigurer 重写过的方法的注册到组件中
             * 在初始化
             * @param registry
             */
            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(new Converter<String, Pet>() {
                    @Override
                    public Pet convert(String source) {
                        if (!StringUtils.isEmpty(source)){
                            Pet pet = new Pet();
                            String[] petProperties = source.split(",");
                            pet.setName(petProperties[0]);
                            pet.setAge(Integer.parseInt(petProperties[1]));
                            return pet;
                        }
                        return null;
                    }
                });
            }
        };
    }
}
