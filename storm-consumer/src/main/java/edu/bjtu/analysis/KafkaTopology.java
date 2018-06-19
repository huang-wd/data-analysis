package edu.bjtu.analysis;

import edu.bjtu.analysis.bolt.JsonBolt;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

import java.util.Arrays;

/**
 * @author huangweidong
 * @date 2018/06/18
 */
public class KafkaTopology {
    public final static char split = '\001';

    public static final String[][] fileDefinitions = {
            {"src/main/resources/数据仓库课程实验三数据/articleInfo/articleInfo", "edu.bjtu.analysis.data.ArticleInfo", "t_article_info"},
            {"src/main/resources/数据仓库课程实验三数据/userBasic/userBasic", "edu.bjtu.analysis.data.UserBasic", "t_user_basic"},
            {"src/main/resources/数据仓库课程实验三数据/userBehavior/userBehavior", "edu.bjtu.analysis.data.UserBehavior", "t_user_behavior"},
            {"src/main/resources/数据仓库课程实验三数据/userEdu/userEdu", "edu.bjtu.analysis.data.UserEdu", "t_user_edu"},
            {"src/main/resources/数据仓库课程实验三数据/userInterest/userInterest", "edu.bjtu.analysis.data.UserInterest", "t_user_interest"},
            {"src/main/resources/数据仓库课程实验三数据/userSkill/userSkill", "edu.bjtu.analysis.data.UserSkill", "t_user_skill"}
    };


    private static final String TOPOLOGY_NAME = "SPAN-DATA-TOPOLOGY";
    private static final String KAFKA_SPOUT_ID = "kafka-stream";
    private static final String JsonProject_BOLT_ID = "jsonProject-bolt";

    public static void main(String[] args) throws Exception {
//        String zks = "132.122.252.51:2181";
//        String topic = "span-data-topic";
//        String zkRoot = "/kafka-storm";
////        BrokerHosts brokerHosts = new ZkHosts(zks);
//        KafkaSpoutConfig kafkaSpoutConfig = new KafkaSpoutConfig();
////        kafkaSpoutConfig kafkaSpoutConfig = new kafkaSpoutConfig(brokerHosts, topic, zkRoot, KAFKA_SPOUT_ID);
////        spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
////        spoutConf.zkServers = Arrays.asList(new String[]{"132.122.252.51"});
////        spoutConf.zkPort = 2181;
//        JsonBolt jsonBolt = new JsonBolt();
//
//        TopologyBuilder builder = new TopologyBuilder();
////        builder.setSpout(KAFKA_SPOUT_ID, new KafkaSpout(spoutConf));
//        builder.setBolt(JsonProject_BOLT_ID, jsonBolt).shuffleGrouping(
//                KAFKA_SPOUT_ID);
//
//        Config config = new Config();
//        config.setNumWorkers(1);
//        if (args.length == 0) {
//            LocalCluster cluster = new LocalCluster();
//            cluster.submitTopology(TOPOLOGY_NAME, config,
//                    builder.createTopology());
//            Utils.waitForSeconds(100);
//            cluster.killTopology(TOPOLOGY_NAME);
//            cluster.shutdown();
//        } else {
//            StormSubmitter.submitTopology(args[0], config,
//                    builder.createTopology());
//        }
    }
}
