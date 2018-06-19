package edu.bjtu.analysis;


import static org.apache.storm.kafka.spout.KafkaSpoutConfig.FirstPollOffsetStrategy.EARLIEST;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.spout.Func;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.KafkaSpoutRetryExponentialBackoff;
import org.apache.storm.kafka.spout.KafkaSpoutRetryExponentialBackoff.TimeInterval;
import org.apache.storm.kafka.spout.KafkaSpoutRetryService;
import org.apache.storm.kafka.spout.trident.KafkaTridentSpoutOpaque;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

/**
 * @author huangweidong
 * @date 2018/06/18
 */
public class TridentKafkaClientTopologyNamedTopics {
    private static final String TOPIC_1 = "test-trident";
    private static final String TOPIC_2 = "test-trident-1";
    private static final String KAFKA_LOCAL_BROKER = "localhost:9092";

    private KafkaTridentSpoutOpaque<String, String> newKafkaTridentSpoutOpaque(KafkaSpoutConfig<String, String> spoutConfig) {
        return new KafkaTridentSpoutOpaque<>(spoutConfig);
    }

    private static final Func<ConsumerRecord<String, String>, List<Object>> JUST_VALUE_FUNC = new JustValueFunc();

    /**
     * Needs to be serializable.
     */
    private static class JustValueFunc implements Func<ConsumerRecord<String, String>, List<Object>>, Serializable {

        @Override
        public List<Object> apply(ConsumerRecord<String, String> record) {
            return new Values(record.value());
        }
    }

    protected KafkaSpoutConfig<String, String> newKafkaSpoutConfig(String bootstrapServers) {
        return KafkaSpoutConfig.builder(bootstrapServers, TOPIC_1, TOPIC_2)
                .setProp(ConsumerConfig.GROUP_ID_CONFIG, "kafkaSpoutTestGroup_" + System.nanoTime())
                .setProp(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 200)
                .setRecordTranslator(JUST_VALUE_FUNC, new Fields("str"))
                .setRetry(newRetryService())
                .setOffsetCommitPeriodMs(10_000)
                .setFirstPollOffsetStrategy(EARLIEST)
                .setMaxUncommittedOffsets(250)
                .build();
    }

    protected KafkaSpoutRetryService newRetryService() {
        return new KafkaSpoutRetryExponentialBackoff(new TimeInterval(500L, TimeUnit.MICROSECONDS),
                TimeInterval.milliSeconds(2), Integer.MAX_VALUE, TimeInterval.seconds(10));
    }

    public static void main(String[] args) throws Exception {
        new TridentKafkaClientTopologyNamedTopics().run(args);
    }

    protected void run(String[] args) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException, InterruptedException {
        final String brokerUrl = args.length > 0 ? args[0] : KAFKA_LOCAL_BROKER;
        System.out.println("Running with broker url " + brokerUrl);

        Config tpConf = new Config();
        tpConf.setDebug(true);
        tpConf.setMaxSpoutPending(5);

        // Producers
//        StormSubmitter.submitTopology(TOPIC_1 + "-producer", tpConf, KafkaProducerTopology.newTopology(brokerUrl, TOPIC_1));
//        StormSubmitter.submitTopology(TOPIC_2 + "-producer", tpConf, KafkaProducerTopology.newTopology(brokerUrl, TOPIC_2));
        // Consumer
        StormSubmitter.submitTopology("topics-consumer", tpConf,
                TridentKafkaConsumerTopology.newTopology(newKafkaTridentSpoutOpaque(newKafkaSpoutConfig(brokerUrl))));
    }
}
