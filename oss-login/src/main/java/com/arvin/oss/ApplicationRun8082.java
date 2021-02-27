package com.arvin.oss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName: ApplicationRun8082
 * @Description: TODO
 * @Author: Arvin @hand-china.com
 * @Date: 2020/12/1
 * @Since version-1.0
 **/
@SpringBootApplication
@MapperScan("com.arvin.oss.mapper")
public class ApplicationRun8082 {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun8082.class,args);
    }
}
