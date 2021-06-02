package com.shop.common.config.mysql;


import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mysql.core")
@PropertySource("classpath:mysql-core-jdbc.properties")
@MapperScan(basePackages = "com.shop.model.mappers", sqlSessionFactoryRef = "mysqlCoreSessionFactory")
public class MysqlCoreConfig {
    /**
     * 这些变量是自动注入的，如：jdbcUrl 对应 mysql-core-jdbc.properties.mysql.core.jdbc-url
     */
    String jdbcUrl;
    String jdbcUserName;
    String jdbcPassword;
    String jdbcDriver;
    String rootMapper;//mapper文件在classpath下存放的根路径
    String aliasesPackage;//别名包

    /**
     * 设置一个数据库的连接池
     */
    @Bean("mysqlCoreDataSource")
    public DataSource mysqlCoreDataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(this.getJdbcUserName());
        dataSource.setPassword(this.getRealPassword());
        dataSource.setJdbcUrl(this.getJdbcUrl());
        dataSource.setDriverClassName(this.getJdbcDriver());
//        //最大连接数
//        dataSource.setMaximumPoolSize(50);
//        //最小连接数
//        dataSource.setMinimumIdle(5);
        return dataSource;
    }

    /**
     * 获取真实的密码，配置上密码是倒着写的
     *
     * @return
     */
    public String getRealPassword() {
        String jdbcPassword = this.getJdbcPassword();
        String reverse = StringUtils.reverse(jdbcPassword);
        return reverse;
    }

    /**
     * 整合mybatis
     * @param dataSource
     * @return
     */
    @Bean("mysqlCoreSessionFactory")
    public SqlSessionFactoryBean mysqlCoreSessionFactory(@Qualifier("mysqlCoreDataSource") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        // 数据源
        factoryBean.setDataSource(dataSource);
        //别名
        factoryBean.setTypeAliasesPackage(this.getAliasesPackage());
        //mapper文件存储的位置
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(this.getMapperFilePath());
        factoryBean.setMapperLocations(resources);

        //开启驼峰标识  user_name  --  》 userName
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        factoryBean.setConfiguration(configuration);
        return factoryBean;
    }

    /**
     * 拼接mapper.xml文件的存储位置
     */
    public String getMapperFilePath(){
        return new StringBuffer("classpath:").append(this.getRootMapper()).append("/**/*.xml").toString();
    }

}
