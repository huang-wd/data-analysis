package edu.bjtu.analysis;

import edu.bjtu.analysis.constant.Profiles;
import edu.bjtu.analysis.spout.KafkaMessageScheme;
import edu.bjtu.analysis.topology.UserBehaviorTopology;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;

/**
 * @author huangweidong
 */
public class Application {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Invalid parameter.");
            System.out.println("Parameter should be topology's name");
            System.out.println(UserBehaviorTopology.TOPOLOGY_NAME);
            System.exit(-1);
        }

        StormTopology stormTopology = null;
        switch (args[0]) {
            case UserBehaviorTopology.TOPOLOGY_NAME:
                stormTopology = UserBehaviorTopology.create();
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
            System.out.println(UserBehaviorTopology.TOPOLOGY_NAME);
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

        kafkaConfig.scheme = new KeyValueSchemeAsMultiScheme(new StringKeyValueScheme());

        return new KafkaSpout(kafkaConfig);
    }
}
