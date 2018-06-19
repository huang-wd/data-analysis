package edu.bjtu.analysis.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by huangweidong on 2017/8/21.
 */
public class CountValue implements Serializable {
    private int count;
    private long firstTime;

    public CountValue(int count, long firstTime) {
        this.count = count;
        this.firstTime = firstTime;
    }

    public int incr(int v) {
        count = count + v;
        return count;
    }

    public int getCount() {
        return count;
    }

    public long getFirstTime() {
        return firstTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
