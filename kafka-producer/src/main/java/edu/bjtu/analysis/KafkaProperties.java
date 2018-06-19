package edu.bjtu.analysis;

/**
 * @author huangweidong
 * @date 2018/06/18
 */
public class KafkaProperties {
    public static final String TOPIC = "bhhtest";
    public static final String KAFKA_SERVER_URL = "172.31.42.22";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int CONNECTION_TIMEOUT = 100000;
    public static final String TOPIC2 = "topic2";
    public static final String TOPIC3 = "topic3";
    public static final String CLIENT_ID = "SimpleConsumerDemoClient";

    private KafkaProperties() {}
}
