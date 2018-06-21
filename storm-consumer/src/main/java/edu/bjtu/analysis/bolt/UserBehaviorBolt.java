package edu.bjtu.analysis.bolt;

import com.alibaba.fastjson.JSON;
import kafka.utils.Json;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangweidong
 */
public class UserBehaviorBolt extends BaseBasicBolt {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final Map<String, String> classMap = new HashMap<String, String>() {
        {
            put("article_info", "edu.bjtu.analysis.data.ArticleInfo");
            put("user_basic", "edu.bjtu.analysis.data.UserBasic");
            put("user_behavior", "edu.bjtu.analysis.data.UserBehavior");
            put("user_edu,", "edu.bjtu.analysis.data.UserEdu");
            put("user_interest", "edu.bjtu.analysis.data.UserInterest");
            put("user_skill", "edu.bjtu.analysis.data.UserSkill");
        }
    };

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("UserBehavior"));
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        Object msg = input.getValueByField("str");
        logger.debug("接收到消息：{}", JSON.toJSONString(msg));
        if (msg instanceof Map) {
            Map<String, String> map = (Map<String, String>) msg;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                Class clazz = null;
                try {
                    clazz = Class.forName(classMap.get(entry.getKey()));
                    Constructor c = clazz.getConstructor(String.class);
                    Object obj = c.newInstance(entry.getValue());
                    System.out.println(JSON.toJSONString(obj));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
