package com.heima.common.zookeeper.sequence;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZkSequence {

    RetryPolicy retryPolicy = new ExponentialBackoffRetry(500, 3);

    DistributedAtomicLong distributedAtomicLong;


    public ZkSequence(String sequenceName, CuratorFramework client) {
        distributedAtomicLong = new DistributedAtomicLong(client, sequenceName, retryPolicy);
    }

    /**
     * 生成序列
     * @return
     */
    public Long sequence() throws Exception {
        AtomicValue<Long> sequence = this.distributedAtomicLong.increment();
        if  (sequence.succeeded()) {
            return sequence.postValue();
        } else {
            return null;
        }
    }
}
