package com.li.auth.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {


    @Bean
    public GroupedOpenApi userApi() { // 创建了一个api接口的分组
        return GroupedOpenApi.builder()
                .group("auth") // 分组名称
                .pathsToMatch("/**") // 接口请求路径规则
                .packagesToScan("com.li.auth")
                .build();
    }


    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info() // 基本信息配置
                        .title("授权中心接口文档") // 标题
                        .description("授权中心服务...") // 描述Api接口文档的基本信息
                        .version("v1.0.0") // 版本
                        // 设置OpenAPI文档的联系信息，包括联系人姓名为"patrick"，邮箱为"patrick@gmail.com"。
                        .contact(new Contact().name("授权中心").email("shihai@li.com"))
                        // 设置OpenAPI文档的许可证信息，包括许可证名称为"Apache 2.0"，许可证URL为"http://springdoc.org"。
                        .license(new License().name("授权中心").url("http://li.shihai.com/"))
                );
    }

}
