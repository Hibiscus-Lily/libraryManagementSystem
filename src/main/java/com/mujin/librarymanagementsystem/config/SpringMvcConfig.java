package com.mujin.librarymanagementsystem.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.mujin.librarymanagementsystem.controller.interceptor.Admin_BookInterceptor;
import com.mujin.librarymanagementsystem.controller.interceptor.Admin_UserInterceptor;
import com.mujin.librarymanagementsystem.controller.interceptor.OrdinaryUser_BookInterceptor;
import com.mujin.librarymanagementsystem.controller.interceptor.OrdinaryUser_UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * 创建SpringMvc的配置文件，加载Controller对应的Bean
 */
@Controller
@ComponentScan("com.mujin.librarymanagementsystem.controller")
@EnableWebMvc

public class SpringMvcConfig implements WebMvcConfigurer {
    private Admin_BookInterceptor admin_bookInterceptor;
    private Admin_UserInterceptor admin_userInterceptor;
    private OrdinaryUser_BookInterceptor ordinaryUser_bookInterceptor;
    private OrdinaryUser_UserInterceptor ordinaryUser_userinterceptor;

    @Autowired

    public void setAdmin_bookInterceptor(Admin_BookInterceptor admin_bookInterceptor) {
        this.admin_bookInterceptor = admin_bookInterceptor;
    }

    @Autowired

    public void setAdmin_userInterceptor(Admin_UserInterceptor admin_userInterceptor) {
        this.admin_userInterceptor = admin_userInterceptor;
    }

    @Autowired

    public void setOrdinaryUser_bookInterceptor(OrdinaryUser_BookInterceptor ordinaryUser_bookInterceptor) {
        this.ordinaryUser_bookInterceptor = ordinaryUser_bookInterceptor;
    }

    @Autowired
    public void setOrdinaryUser_personalInformation_interceptor(OrdinaryUser_UserInterceptor ordinaryUser_userinterceptor) {
        this.ordinaryUser_userinterceptor = ordinaryUser_userinterceptor;
    }

    /**
     * 消息内容转换配置
     * 配置fastJson返回json转换
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //调用父类的配置
        WebMvcConfigurer.super.configureMessageConverters(converters);
        //创建fastJson消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //修改配置返回内容的过滤
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        fastConverter.setSupportedMediaTypes(getSupportedMediaTypes()); // 支持的MediaType
        fastConverter.setDefaultCharset(StandardCharsets.UTF_8); // 默认字符集
        //将fastjson添加到视图消息转换器列表内
        converters.add(fastConverter);

    }

    /**
     * 配置支持的媒体类型
     */
    private List<MediaType> getSupportedMediaTypes() {
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(new MediaType("application", "*+json"));
        return supportedMediaTypes;
    }

    /**
     * 设置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(admin_userInterceptor).addPathPatterns("/admin/user/**");
        registry.addInterceptor(admin_bookInterceptor).addPathPatterns("/admin/book/**");
        registry.addInterceptor(ordinaryUser_userinterceptor).addPathPatterns("/commonuser/user/**");
        registry.addInterceptor(ordinaryUser_bookInterceptor).addPathPatterns("/commonuser/book/**");
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 设置允许请求方式
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                // 是否允许证书（cookies）
                .allowCredentials(true)
                .exposedHeaders("token")
                // 预请求的结果能被缓存多久
                .maxAge(3600)
                // 设置允许的请求头
                .allowedHeaders("*");
    }
}
