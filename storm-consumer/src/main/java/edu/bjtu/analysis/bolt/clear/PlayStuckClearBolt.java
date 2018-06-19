package edu.bjtu.analysis.bolt.clear;


import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import edu.bjtu.analysis.bolt.count.PlayStuckCountBolt;
import edu.bjtu.analysis.constant.Profiles;
import edu.bjtu.analysis.dto.UserBehavior;
import edu.bjtu.analysis.util.TimeKeyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by huangweidong on 2017/9/8.
 * pv
 */
public class PlayStuckClearBolt extends BaseBasicBolt {

    private Joiner.MapJoiner mapJoiner;

    private static final Logger logger = LoggerFactory.getLogger(PlayStuckClearBolt.class);

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        mapJoiner = Joiner.on(Profiles.Separator).withKeyValueSeparator(Profiles.KeyValueSeparator);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("key"));
    }

    public void execute(Tuple input, BasicOutputCollector collector) {
        UserBehavior userBehavior = (UserBehavior) input.getValueByField("UserBehavior");
        logger.debug("接收到消息：{}", userBehavior.toString());
        String key = generateKey(userBehavior);

        if (StringUtils.isEmpty(key)) {
            logger.error("生成key失败:{}", userBehavior.toString());
            return;
        }
        collector.emit(new Values(key));
    }


    private String generateKey(UserBehavior userBehavior) {
        if (userBehavior == null) {
            return null;
        }

        String timeKeyDay = TimeKeyUtils.timeKeyFormaterDay(String.valueOf(userBehavior.getEventTime()));
        String timeKeyMonth = TimeKeyUtils.timeKeyFormaterMonth(String.valueOf(userBehavior.getEventTime()));

        if (StringUtils.isEmpty(userBehavior.getEventTime()) || StringUtils.isEmpty(timeKeyDay) || StringUtils.isEmpty(timeKeyMonth)) {
            return null;
        }

        String itemId = StringUtils.defaultIfEmpty(userBehavior.getItemId(), "未知");
        String itemType = StringUtils.defaultIfEmpty(userBehavior.getItemType(), "未知");
        String platform = StringUtils.defaultIfEmpty(userBehavior.getPlatform(), "未知");
        String userId = StringUtils.defaultIfEmpty(userBehavior.getUserId(), "未知");
        String udid = StringUtils.defaultIfEmpty(userBehavior.getUdid(), "未知");
        String ip = StringUtils.defaultIfEmpty(userBehavior.getIp(), "未知");

        String key;

        //"item_id", "item_type", "platform", "event_time", "event_day", "event_month", "user_id", "udid", "ip"
        Map<String, String> totalKeyMap = Maps.newLinkedHashMap();
        totalKeyMap.put(PlayStuckCountBolt.cols.get(0), itemId);
        totalKeyMap.put(PlayStuckCountBolt.cols.get(1), itemType);
        totalKeyMap.put(PlayStuckCountBolt.cols.get(2), platform);
        totalKeyMap.put(PlayStuckCountBolt.cols.get(3), userBehavior.getEventTime());
        totalKeyMap.put(PlayStuckCountBolt.cols.get(4), timeKeyDay);
        totalKeyMap.put(PlayStuckCountBolt.cols.get(5), timeKeyMonth);
        totalKeyMap.put(PlayStuckCountBolt.cols.get(6), userId);
        totalKeyMap.put(PlayStuckCountBolt.cols.get(7), udid);
        totalKeyMap.put(PlayStuckCountBolt.cols.get(8), ip);
        key = mapJoiner.join(totalKeyMap);


        return key;
    }
}
