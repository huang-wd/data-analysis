package edu.bjtu.analysis.spout;


import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

/**
 */
public class ShutdownSpout extends BaseRichSpout {

    private static final Logger logger = LoggerFactory.getLogger(ShutdownSpout.class);

    public static final String SHUTDOWN_STREAM_ID = "Shutdown";

    private SpoutOutputCollector _collector;

    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        logger.info("shutdown spout open function called");
        _collector = collector;
    }

    public void activate() {
        logger.info("shutdown spout activate function called");
    }

    public void deactivate() {
        logger.info("shutdown deactivate to spout and bolt");
        try {
            _collector.emit(SHUTDOWN_STREAM_ID, new Values("Showdown"), UUID.randomUUID().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nextTuple() {
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer message) {
        message.declareStream(SHUTDOWN_STREAM_ID, new Fields("Showdown"));
    }

    public void ack(Object msgId) {
        logger.info("shutDown spout ack, msId " + msgId);
    }

    public void fail(Object msgId) {
        logger.error("shutDown spout fail, msId " + msgId);
    }
}
