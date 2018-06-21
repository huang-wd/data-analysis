package edu.bjtu.analysis.topology;

import edu.bjtu.analysis.Application;
import edu.bjtu.analysis.bolt.UserBehaviorBolt;
import edu.bjtu.analysis.spout.ShutdownSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

/**
 * @author huangweidong
 */
public class UserBehaviorTopology {

    public final static char split = '\001';

    public static final String TOPOLOGY_NAME = "user-behavior-topology";

    private static final String kafkaSpoutId = "user-behavior-storm-kafka-spout";

    private static final String datasourceSpoutName = "user-behavior-datasource-spout";

    private static final String userBehaviorBoltName = "user-behavior-event-filter-bolt";

    private static final String shutdownSpoutName = "user-behavior-shutdown-spout";


    public static StormTopology create() {
        ShutdownSpout shutdownSpout = new ShutdownSpout();

        UserBehaviorBolt userBehaviorBolt = new UserBehaviorBolt();

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout(datasourceSpoutName, Application.createKafkaSpout(kafkaSpoutId), 1);

        builder.setSpout(shutdownSpoutName, shutdownSpout);

        builder.setBolt(userBehaviorBoltName, userBehaviorBolt, 1)
                .shuffleGrouping(datasourceSpoutName)
                .allGrouping(shutdownSpoutName, ShutdownSpout.SHUTDOWN_STREAM_ID);

        return builder.createTopology();
    }

    /**
     * 本地方式运行,方便调试
     *
     * @param args
     */
    public static void main(String[] args) {
        Config config = new Config();
        config.setNumWorkers(1);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(TOPOLOGY_NAME, config, create());
    }

}
