package com.eatwhat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// @MapperScan("com.eatwhat.mapper")
@SpringBootApplication
@EnableTransactionManagement // 开启注解方式的事务管理
@EnableCaching
@Slf4j
public class EatWhatApplication {
    public static void main(String[] args) {
        SpringApplication.run(EatWhatApplication.class, args);
        log.info("server started");
    }

    /* @Bean
    public DataSource dataSource() {
        // 配置你的数据源，例如使用 HikariCP
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/eatwhat_take_out");
        config.setUsername("root");
        config.setPassword("");
        return new HikariDataSource(config);
    }

    @Bean
    public SqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    } */
}