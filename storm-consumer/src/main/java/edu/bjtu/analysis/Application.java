package edu.bjtu.analysis;

import edu.bjtu.analysis.constant.Profiles;
import edu.bjtu.analysis.spout.KafkaMessageScheme;
import edu.bjtu.analysis.topology.PlayStuckTopology;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;

/**
 * Created by huangweidong on 2017/8/26.
 */
public class Application {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Invalid parameter.");
            System.out.println("Parameter should be topology's name");
            System.out.println(PlayStuckTopology.TOPOLOGY_NAME);
            System.exit(-1);
        }

        StormTopology stormTopology = null;
        switch (args[0]) {
            case PlayStuckTopology.TOPOLOGY_NAME:
                stormTopology = PlayStuckTopology.create();
                break;
            default:
                break;
        }

        if (stormTopology != null) {
            Config conf = new Config();
            conf.setNumWorkers(3);
            conf.setNumAckers(0);
            conf.setMessageTimeoutSecs(60);
            StormSubmitter.submitTopology(args[0], conf, stormTopology);
        } else {
            System.out.println("topology name error!!!");
            System.out.println("Parameter should be topology's name");
            System.out.println(PlayStuckTopology.TOPOLOGY_NAME);
        }
    }

    public static KafkaSpout createKafkaSpout(String kafkaSpoutId) {
        //kafka所在zookeeper地址
        BrokerHosts brokerHosts = new ZkHosts(Profiles.KAFKA_ZK_HOSTS);
        SpoutConfig kafkaConfig = new SpoutConfig(brokerHosts, Profiles.TOPIC, Profiles.ZK_ROOT_PROGRESS, kafkaSpoutId);
        //指定记录KafkaSpout读取进度所用的zookeeper的host
        kafkaConfig.zkPort = Profiles.ZK_PORT_PROGRESS;
        kafkaConfig.zkServers = Profiles.ZK_SERVERS_PROGRESS;

        //从头开始消费
        //kafkaConfig.forceFromStart = true;

        kafkaConfig.scheme = new SchemeAsMultiScheme(new KafkaMessageScheme());
        return new KafkaSpout(kafkaConfig);
    }
}
