package edu.bjtu.analysis;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author huangweidong
 * @date 2018/06/18
 */
public class KafkaProducer {
    public final static char split = '\001';

    public static final String[][] fileDefinitions = {
            {"D:\\数据仓库课程实验三数据/articleInfo/articleInfo", "t_article_info"},
            {"D:\\数据仓库课程实验三数据/userBasic/userBasic", "t_user_basic"},
            {"D:\\数据仓库课程实验三数据/userBehavior/userBehavior", "t_user_behavior"},
            {"D:\\数据仓库课程实验三数据/userEdu/userEdu", "t_user_edu"},
            {"D:\\数据仓库课程实验三数据/userInterest/userInterest", "t_user_interest"},
            {"D:\\数据仓库课程实验三数据/userSkill/userSkill", "t_user_skill"}
    };

    public static Producer<String, String> producer;

    public static void main(String[] args) throws Exception {
        initKafka();
        for (String[] fileDefinition : fileDefinitions) {
            readFile(fileDefinition);
        }
        closeKafka();

    }

    private static void readFile(String[] dataDefinition) throws Exception {
        try (LineIterator it = FileUtils.lineIterator(new File(dataDefinition[0]), "UTF-8")) {
            while (it.hasNext()) {
                String line = it.nextLine();
                // Class clazz = Class.forName(dataDefinition[1]);
                //Constructor c = clazz.getConstructor(String.class);
                try {
                    // sendMsg(dataDefinition[2], JSON.toJSONString(c.newInstance(line)));
                    sendMsg(dataDefinition[1], line);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void initKafka() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.31.42.22:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
    }

    private static void closeKafka() {
        producer.close();
    }

    private static void sendMsg(String topic, String msg) {
        try {
            RecordMetadata metadata = producer.send(new ProducerRecord<>(topic, msg)).get();
            System.out.println(metadata.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
