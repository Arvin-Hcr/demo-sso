package com.aaa.lee.sso.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/11/16 11:49
 * @Description
 **/
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

    private String nodes;
    private Integer maxAttmpts;
    private Integer expire;
    private Integer commandTimeout;

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public Integer getMaxAttmpts() {
        return maxAttmpts;
    }

    public void setMaxAttmpts(Integer maxAttmpts) {
        this.maxAttmpts = maxAttmpts;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    public Integer getCommandTimeout() {
        return commandTimeout;
    }

    public void setCommandTimeout(Integer commandTimeout) {
        this.commandTimeout = commandTimeout;
    }
}
