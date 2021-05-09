package com.heima;

import io.mycat.config.model.rule.RuleAlgorithm;
import io.mycat.route.function.AbstractPartitionAlgorithm;

public class HeiMaBurstRuleAlgorithm extends AbstractPartitionAlgorithm implements RuleAlgorithm {

    // 单组数据容量
    Long volume;
    // 单组DN节点数量
    Integer step;
    // 分片模
    Integer mod;

    /**
     * 数据ID-桶ID
     *
     * @param columnValue 数据ID-桶ID 分片ID =（dataId/volume）* step +分表ID/mod
     * @return
     */
    @Override
    public Integer calculate(String columnValue) {

        if (columnValue != null) {
            String[] temp = columnValue.split("-");
            if (temp.length == 2) {
                try {
                    Long dataId = Long.valueOf(temp[0]);
                    Long burstId = Long.valueOf(temp[1]);
                    int group = (int) (dataId / volume) * step;
                    int pos = group + (int) (burstId % mod);
                    System.out.println("HEIMA RULE INFO [" + columnValue + "]-[{" + pos + "}]");
                    return pos;
                } catch (Exception e) {
                    System.out.println("HEIMA RULE INFO [" + columnValue + "]-[{" + e.getMessage() + "}]");
                }
            }
        }

        return 0;
    }

    /**
     * 范围计算
     *
     * @param beginValue
     * @param endValue
     * @return
     */
    public Integer[] calculateRange(String beginValue, String endValue) {
        if (beginValue != null && endValue != null) {
            Integer begin = calculate(beginValue);
            Integer end = calculate(endValue);
            if (begin == null || end == null) {
                return new Integer[0];
            }
            if (end >= begin) {
                int len = end - begin + 1;
                Integer[] re = new Integer[len];
                for (int i = 0; i < len; i++) {
                    re[i] = begin + i;
                }
                return re;
            }
        }
        return new Integer[0];
    }


    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getMod() {
        return mod;
    }

    public void setMod(Integer mod) {
        this.mod = mod;
    }
}
