package edu.bjtu.analysis.bolt.filter;

import com.alibaba.fastjson.JSON;
import edu.bjtu.analysis.dto.UserBehavior;
import edu.bjtu.analysis.dto.Message;
import org.apache.commons.lang.StringUtils;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;

/**
 * Created by huangweidong on 2017/9/9.
 */
public abstract class BaseEventFilterBolt extends BaseBasicBolt {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected abstract void filter(BasicOutputCollector collector, Message message, UserBehavior userBehavior, String msgStr);

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("UserBehavior"));
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String msg = input.getStringByField("Message");
        logger.debug("接收到消息：{}", msg);
        Message message;

        try {
            message = JSON.parseObject(msg, Message.class);
        } catch (Exception e) {
            logger.error("解析Message错误:{}", msg, e);
            return;
        }

        UserBehavior userBehavior;

        try {
            userBehavior = JSON.parseObject(message.getRequestBody(), UserBehavior.class);
            if (userBehavior != null && StringUtils.isNotBlank(userBehavior.getEventTime())) {
                userBehavior.setEventTime(URLDecoder.decode(userBehavior.getEventTime(), "UTF-8"));
            }
        } catch (Exception e) {
            logger.error("解析UserBehavior出错:{}", message.getRequestBody(), e);
            return;
        }

        filter(collector, message, userBehavior, msg);
    }


}
