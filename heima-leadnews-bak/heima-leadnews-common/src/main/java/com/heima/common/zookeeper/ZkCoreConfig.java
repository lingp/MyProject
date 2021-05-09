package com.heima.common.zookeeper;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "zk")
@PropertySource("classpath:zookeeper.properties")
public class ZkCoreConfig {

    String host;
    String sequencePath;

    /**
     * 这是最快的数据库连接池
     * @return
     */
    @Bean
    public ZookeeperClient zookeeperClient(){
        return new ZookeeperClient(this.host,this.sequencePath);
    }

}
