package edu.bjtu.analysis.spout;

import org.apache.storm.kafka.StringScheme;
import org.apache.storm.tuple.Fields;

/**
 * KafkaSpout如何去解码数据，生成Storm内部传递数据
 */
public class KafkaMessageScheme extends StringScheme {
    @Override
    public Fields getOutputFields() {
        return new Fields("Message");
    }
}
