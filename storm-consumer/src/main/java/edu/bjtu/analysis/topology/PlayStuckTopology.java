package edu.bjtu.analysis.topology;

import edu.bjtu.analysis.Application;
import edu.bjtu.analysis.bolt.clear.PlayStuckClearBolt;
import edu.bjtu.analysis.bolt.count.PlayStuckCountBolt;
import edu.bjtu.analysis.bolt.filter.PlayStuckFilterBolt;
import edu.bjtu.analysis.spout.ShutdownSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created by baohehe
 */
public class PlayStuckTopology {

    public static final String TOPOLOGY_NAME = "play-stuck-topology";

    private static final String kafkaSpoutId = "play-stuck-storm-kafka-spout";

    private static final String datasourceSpoutName = "play-stuck-datasource-spout";

    private static final String eventFilterBoltName = "play-stuck-event-filter-bolt";

    private static final String clearBoltName = "play-stuck-clear-bolt";
    private static final String countBoltName = "play-stuck-count-bolt";

    private static final String shutdownSpoutName = "play-stuck-shutdown-spout";


    public static StormTopology create() {
        ShutdownSpout shutdownSpout = new ShutdownSpout();

        PlayStuckFilterBolt eventFilterBolt = new PlayStuckFilterBolt();

        PlayStuckClearBolt clearBolt = new PlayStuckClearBolt();
        PlayStuckCountBolt countBolt = new PlayStuckCountBolt();

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout(datasourceSpoutName, Application.createKafkaSpout(kafkaSpoutId), 1);

        builder.setSpout(shutdownSpoutName, shutdownSpout);

        builder.setBolt(eventFilterBoltName, eventFilterBolt, 1)
                .shuffleGrouping(datasourceSpoutName);

        builder.setBolt(clearBoltName, clearBolt, 1)
                .shuffleGrouping(eventFilterBoltName);

        builder.setBolt(countBoltName, countBolt, 2)
                .fieldsGrouping(clearBoltName, new Fields("key"))
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
