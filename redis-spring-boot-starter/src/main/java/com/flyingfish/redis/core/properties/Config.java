package com.flyingfish.redis.core.properties;

public class Config {
    private Integer maxIdle = 50;
    private Integer maxTotal = 100;
    private Integer timeout = 5000;
    private Integer maxWaitMillis = 3000;
    private Boolean testOnBorrow = true;
    public Integer getMaxIdle() {
        return maxIdle;
    }
    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }
    public Integer getMaxTotal() {
        return maxTotal;
    }
    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }
    public Integer getTimeout() {
        return timeout;
    }
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
    public Integer getMaxWaitMillis() {
        return maxWaitMillis;
    }
    public void setMaxWaitMillis(Integer maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }
    public Boolean getTestOnBorrow() {
        return testOnBorrow;
    }
    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }
    @Override
    public String toString() {
        return "Config [maxIdle=" + maxIdle + ", maxTotal=" + maxTotal + ", timeout=" + timeout + ", maxWaitMillis="
                + maxWaitMillis + ", testOnBorrow=" + testOnBorrow + "]";
    }
}
