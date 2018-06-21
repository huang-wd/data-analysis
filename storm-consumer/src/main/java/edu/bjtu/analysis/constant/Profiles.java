package edu.bjtu.analysis.constant;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by baohehe
 */
public class Profiles {
    private static Logger logger = LoggerFactory.getLogger(Profiles.class);

    private static final String PROPERTIES_FILE_NAME = "application.properties";

    public static String MYSQL_URL;
    public static String MYSQL_USER;
    public static String MYSQL_PASSWORD;
    public static String MYSQL_DRIVER;

    public static String TOPIC;

    //kafka对应的zk地址
    public static String KAFKA_ZK_HOSTS;

    //记录Spout读取进度所用的zookeeper的host
    public static List<String> ZK_SERVERS_PROGRESS;
    //记录进度用的zookeeper的端口
    public static Integer ZK_PORT_PROGRESS;

    //进度信息记录于zookeeper的哪个路径下
    public static String ZK_ROOT_PROGRESS;

    /**
     * storm flush definition
     * 统计信息先在内存累计,缓冲一段时间,等达到预定的阈值后才更新数据库
     * 其中阈值分为几类:
     * 1.统计的单个到达了指定数量
     * 2.盛放统计结果的容器值
     * 3.统计时间间隔
     */

    public static int FLUSH_MAX_COUNT;
    public static int FLUSH_MAX_SIZE;
    //被统计的信息时间间隔
    public static int FLUSH_MAX_WAIT_TIME;
    public static final String Separator = "##";

    public static final String KeyValueSeparator = "==";


    public static int TICK_TUPLE_FREQ_SECS;

    static {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties().setFileName(PROPERTIES_FILE_NAME));
        try {
            Configuration config = builder.getConfiguration();

            //kafka
            TOPIC = config.getString("kafka.storm.topic");
            KAFKA_ZK_HOSTS = config.getString("kafka.zookeeper.hosts");

            //storm消费kafka记录进度的zk
            ZK_SERVERS_PROGRESS = Arrays.asList(config.getStringArray("storm.kafka.progress.zookeepers.hosts"));
            ZK_PORT_PROGRESS = config.getInt("storm.kafka.progress.zookeeper.port");
            ZK_ROOT_PROGRESS = config.getString("storm.kafka.progress.zookeeper.root-path");

            //storm flush
            FLUSH_MAX_COUNT = config.getInt("flush.max.count");
            FLUSH_MAX_SIZE = config.getInt("flush.max.size");
            FLUSH_MAX_WAIT_TIME = config.getInt("flush.max.wait.time") * 1000;

            TICK_TUPLE_FREQ_SECS = config.getInt("tick.tuple.freq.secs");
        } catch (ConfigurationException cex) {
            logger.error("解析配置文件出错", cex);
        }
    }


}
