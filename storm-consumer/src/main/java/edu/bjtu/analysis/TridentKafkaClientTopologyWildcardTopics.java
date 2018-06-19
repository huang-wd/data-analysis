package edu.bjtu.analysis;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.regex.Pattern;

import static org.apache.storm.kafka.spout.KafkaSpoutConfig.FirstPollOffsetStrategy.EARLIEST;

/**
 * @author huangweidong
 * @date 2018/06/18
 */
public class TridentKafkaClientTopologyWildcardTopics extends TridentKafkaClientTopologyNamedTopics {
    private static final Pattern TOPIC_WILDCARD_PATTERN = Pattern.compile("test-trident(-1)?");

    @Override
    protected KafkaSpoutConfig<String, String> newKafkaSpoutConfig(String bootstrapServers) {
        return KafkaSpoutConfig.builder(bootstrapServers, TOPIC_WILDCARD_PATTERN)
                .setProp(ConsumerConfig.GROUP_ID_CONFIG, "kafkaSpoutTestGroup")
                .setProp(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 200)
                .setRecordTranslator((r) -> new Values(r.value()), new Fields("str"))
                .setRetry(newRetryService())
                .setOffsetCommitPeriodMs(10_000)
                .setFirstPollOffsetStrategy(EARLIEST)
                .setMaxUncommittedOffsets(250)
                .build();
    }

    public static void main(String[] args) throws Exception {
        new TridentKafkaClientTopologyWildcardTopics().run(args);
    }
}
