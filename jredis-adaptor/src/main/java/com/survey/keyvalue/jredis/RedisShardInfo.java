/*
 * Copyright (c) 2011 Yoyo Systems. All rights reserved.
 *
 * $Id$
 */
package com.survey.keyvalue.jredis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;

public class RedisShardInfo {
    public String toString() {
        StringBuilder sb = new StringBuilder("<Redis master='");
        sb.append(masterHost);
        sb.append(":");
        sb.append(masterPort);
        sb.append("' slave='");
        sb.append(slaveHost);
        sb.append(":");
        sb.append(slavePort);
        sb.append("'/>");
        return sb.toString();
    }

    private int timeout;
    private String shardName;
    private String masterHost;
    private int masterPort;
    private String slaveHost;
    private int slavePort;

    public String getMasterHost() {
        return masterHost;
    }

    public int getMasterPort() {
        return masterPort;
    }

    public String getSlaveHost() {
        return slaveHost;
    }

    public int getSlavePort() {
        return slavePort;
    }

    public RedisShardInfo(String shardName, String masterHost, String slaveHost) {
        this(shardName, masterHost, Protocol.DEFAULT_PORT, slaveHost, Protocol.DEFAULT_PORT);
    }

    public RedisShardInfo(String shardName, String masterHost, int masterPort, String slaveHost, int slavePort) {
        this(shardName, masterHost, masterPort, slaveHost, slavePort, 15000);
    }

    public RedisShardInfo(String shardName, String masterHost, int masterPort, String slaveHost, int slavePort,
            int timeout) {
        this.shardName = shardName;
        this.masterHost = masterHost;
        this.masterPort = masterPort;
        this.slaveHost = slaveHost;
        this.slavePort = slavePort;
        this.timeout = timeout;
    }

    public String getShardName() {
        return shardName;
    }

    public int getTimeout() {
        return timeout;
    }

    public Jedis[] createResources() {
        Jedis[] result = new Jedis[2];
        result[0] = new Jedis(masterHost, masterPort, timeout);
        result[1] = new Jedis(slaveHost, slavePort, timeout);
        return result;
    }
}
