package edu.bjtu.analysis.bolt.filter;

import edu.bjtu.analysis.constant.EventType;
import edu.bjtu.analysis.dto.UserBehavior;
import edu.bjtu.analysis.dto.Message;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.tuple.Values;

/**
 * Created by huangweidong on 2017/9/8.
 * 卡顿事件上报
 */
public class PlayStuckFilterBolt extends BaseEventFilterBolt {
    @Override
    protected void filter(BasicOutputCollector collector, Message message, UserBehavior userBehavior, String msgStr) {
        if (userBehavior != null) {
            if (EventType.PLAY_STUCK.name.equalsIgnoreCase(userBehavior.getEvent())) {
                userBehavior.setIp(message.getRemote_addr());
                collector.emit(new Values(userBehavior));
            }
        }
    }
}
