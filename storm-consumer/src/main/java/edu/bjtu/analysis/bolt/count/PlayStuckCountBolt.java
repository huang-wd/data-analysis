package edu.bjtu.analysis.bolt.count;

import java.util.Arrays;
import java.util.List;


/**
 * Created by huangweidong on 2017/9/8.
 * 播放卡顿统计
 */
public class PlayStuckCountBolt extends BaseCountBolt {

    public static final List<String> cols = Arrays.asList("item_id", "item_type", "platform", "event_time", "event_day", "event_month", "user_id", "udid", "ip");

    @Override
    String getTableName() {
        return "storm_play_stuck";
    }

    @Override
    List<String> getCols() {
        return cols;
    }
}
