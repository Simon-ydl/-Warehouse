package com.yjs.api;

import com.yjs.dataSource.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@ComponentScan(basePackages = {"com.yjs"})
@MapperScan({"com.yjs.dao.mapper"})
@EnableEurekaClient             //Eureka客户端
@SpringBootApplication
@EnableTransactionManagement  //开启事务回滚
@Import(DynamicDataSourceRegister.class) //开启多数据源配置
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
