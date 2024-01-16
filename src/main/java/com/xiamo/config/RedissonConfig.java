package com.xiamo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: AceXiamo
 * @ClassName: RedissonConfig
 * @Date: 2023/3/16 11:52
 */
@Configuration
public class RedissonConfig {

    @Value(value = "${spring.redis.host}")
    private String host;
    @Value(value = "${spring.redis.port}")
    private int port;
    @Value(value = "${spring.redis.database}")
    private int database;
    @Value(value = "${spring.redis.password}")
    private String password;

    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        String address = "redis://" + host + ":" + port;
        singleServerConfig.setAddress(address);
        singleServerConfig.setDatabase(database);
        singleServerConfig.setPassword(password);
        return Redisson.create(config);
    }

}
