package edu.bjtu.analysis.hbase;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.bjtu.analysis.util.HBaseUtils;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import static org.apache.hadoop.hbase.client.ConnectionFactory.createConnection;

public class StormKafkaToHBase extends Thread{
    Pattern p = Pattern. compile("省公司鉴权接口url\\[(.*)]\\,响应时间\\[([0-9]+)\\],当前时间\\[([0-9]+)\\]" );
    private ConsumerConnector consumerConnector ;
    protected static Configuration configuration;
    protected static Connection connection;
    protected static Admin admin;
    public StormKafkaToHBase() {
        Properties props = new Properties();
        props.put( "zookeeper.connect", "master:2181,slaver1:2181,slaver2:2181,salver3:2181,slaver4:2181" );
        // 设置consumer组
        props.put( "group.id", "jf-group" );
        ConsumerConfig config = new ConsumerConfig(props);
        this.consumerConnector = Consumer.createJavaConsumerConnector( config);
    }
    //初始化，建立连接
    public  void init() {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "master,slaver1,slaver2,slaver3,slaver4");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        try {
            connection = createConnection(configuration);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //关闭连接
    public  void close() {
        try {
            if (admin != null) {
                admin.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put("t_article_info" , 1);//每次从topic专题中获取一条记录
        Map<String, List<KafkaStream< byte[], byte[]>>> createMessageStreams = consumerConnector.createMessageStreams( topicCountMap);

        HBaseUtils hbase = new HBaseUtils();
        while (true ) {
            // 从kafka 的专题中获取信息
            KafkaStream< byte[], byte []> kafkaStream = createMessageStreams.get("t_article_info" ).get(0);
            ConsumerIterator< byte[], byte []> iterator = kafkaStream .iterator();
            if (iterator .hasNext()) {
                MessageAndMetadata< byte[], byte []> mm = iterator .next();
                String v = new String(mm.message());
                Matcher m = p.matcher( v);
                if (m .find()) {
                    String aid  = m.group(1);
                    String type = m.group(2);
                    String isTop = m.group(3);
                    String status = m.group(4);
                    String domain = m.group(5);
                    String[] column_fields1 = {"info:aid", "info:type", "info:isTop", "info:status", "info:domain"};
                    String[] values = {aid,type,isTop,status,domain};
                    System. out.println(Thread.currentThread().getId()+ "=>"+aid + "->" + type + "->" + isTop );
                    //原始数据保持到HBase中，http://hn.auth.com->2000->1444274868019，rowkey为auth+日期
                    try{
                        hbase.addRecord("article_info","aid",column_fields1,values);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }

    }
    public static void main(String[] args) {
        StormKafkaToHBase stormKafkaToHBase = new StormKafkaToHBase();
        //stormKafkaToHBase.init();
        stormKafkaToHBase.start();
        //stormKafkaToHBase.close();
    }
}
