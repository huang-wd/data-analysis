package edu.bjtu.analysis.bolt.count;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import edu.bjtu.analysis.constant.Profiles;
import edu.bjtu.analysis.dto.CountValue;
import edu.bjtu.analysis.spout.ShutdownSpout;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.storm.Config;
import org.apache.storm.Constants;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by huangweidong on 2017/9/9.
 */
public abstract class BaseCountBolt extends BaseBasicBolt {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Splitter.MapSplitter mapSplitter;

    protected HashMap<String, CountValue> countMap = null;

    private List<String> cols;

    private String SQL;


    /**
     * 指定数据需要保存到的表名
     *
     * @return
     */
    abstract String getTableName();

    /**
     * 指定数据库需要保存的字段
     *
     * @return
     */
    abstract List<String> getCols();

    protected void init() {
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        countMap = Maps.newHashMap();
        cols = getCols();
        SQL = getSql();
        mapSplitter = Splitter.on(Profiles.Separator).withKeyValueSeparator(Profiles.KeyValueSeparator);
        init();

    }

    /**
     * 设置定时任务,每隔 @see Profiles.TICK_TUPLE_FREQ_SECS 秒执行一次
     *
     * @return
     */
    @Override
    public Map<String, Object> getComponentConfiguration() {
        Config conf = new Config();
        conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, Profiles.TICK_TUPLE_FREQ_SECS);
        return conf;
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        //判断是否是关闭通知
        if (ShutdownSpout.SHUTDOWN_STREAM_ID.equalsIgnoreCase(input.getSourceStreamId())) {
            topologyShutdownHook(input, collector);
            return;
        }

        //判断是否是定时任务通知
        if (Constants.SYSTEM_COMPONENT_ID.equalsIgnoreCase(input.getSourceComponent())
                && Constants.SYSTEM_TICK_STREAM_ID.equalsIgnoreCase(input.getSourceStreamId())) {
            timerHook(input, collector);
            return;
        }

        msgReceivedHook(input, collector);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }


    /**
     * 消息数 累加
     *
     * @param input
     * @param collector
     * @return
     */
    public CountValue msgReceivedHook(Tuple input, BasicOutputCollector collector) {
        String key = input.getStringByField("key");
        logger.debug("接收到消息:{}", key);
        if (countMap.containsKey(key)) {
            CountValue v = countMap.get(key);
            v.incr(1);
            return countMap.put(key, v);
        } else {
            long t = System.currentTimeMillis();
            CountValue v = new CountValue(1, t);
            return countMap.put(key, v);
        }
    }

    /**
     * 拓扑关闭时 通知该方法  需要在拓扑创建时指定关闭spout
     */
    public void topologyShutdownHook(Tuple input, BasicOutputCollector collector) {
        logger.info("接收到关闭通知");
    }

    /**
     * bolt 定时任务  定时回调该方法
     *
     * @param input
     * @param collector
     */
    public void timerHook(Tuple input, BasicOutputCollector collector) {
        logger.info("接收到定时任务通知");
        Map<String, CountValue> map = Maps.newHashMap();

        long now = System.currentTimeMillis();
        Iterator<Map.Entry<String, CountValue>> it = countMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, CountValue> entry = it.next();
            CountValue value = entry.getValue();
            //超过指定时间的kv会被写入DB
            if (now - value.getFirstTime() > Profiles.FLUSH_MAX_WAIT_TIME) {
                map.put(entry.getKey(), entry.getValue());
                it.remove();
            }
        }
    }


    //`count`,`update_time`
    public Object[] getParams(Map<String, String> map, int count) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        int paramCount = map.size() + 4;
        Object[] params = new Object[paramCount];
        for (int i = 0; i < map.size(); i++) {
            params[i] = map.get(cols.get(i));
        }
        Date now = new Date();
        params[map.size()] = count;
        params[map.size() + 1] = now;
        params[map.size() + 2] = count;
        params[map.size() + 3] = now;

        return params;
    }

    public String getSql() {
        if (CollectionUtils.isEmpty(cols)) {
            logger.warn("cols is empty");
            return "";
        }
        StringBuffer placeholder = new StringBuffer();
        for (int i = 0; i < cols.size(); i++) {
            placeholder.append("?,");
        }
        placeholder.append("?,?");

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ")
                .append(getTableName())
                .append("(")
                .append(Joiner.on(",").join(cols))
                .append(",count,update_time)values(")
                .append(placeholder)
                .append(")")
                .append("ON DUPLICATE KEY UPDATE ")
                .append("count=count+? ,update_time=?");
        logger.info("生成sql:{}", sql.toString());
        return sql.toString();
    }

    private Map<String, String> parseKey(String key) {
        Map<String, String> splitMap;
        try {
            splitMap = mapSplitter.split(key);
            return splitMap;
        } catch (Exception e) {
            splitMap = null;
        }
        return splitMap;
    }


}
