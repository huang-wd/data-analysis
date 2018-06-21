package edu.bjtu.analysis.Hbase;

import edu.bjtu.analysis.data.ArticleInfo;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

public class ConsumerHBase {
    protected static Configuration configuration;
    protected static Connection connection;
    protected static Admin admin;
    //初始化，建立连接
    public  void init() {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "master,slaver1,slaver2,slaver3,slaver4");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        try {
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void close() {
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
    //（1）创建表
    public  void createTable(String tableName, String[] fields) throws IOException {
        init();
        TableName tablename = TableName.valueOf(tableName);
        if (admin.tableExists(tablename)) {
            System.out.println("table is exists!");
            admin.disableTable(tablename);
            admin.deleteTable(tablename);
        }
        HTableDescriptor hTableDescriptor = new HTableDescriptor(tablename);
        for (String str : fields) {
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(str);
            hTableDescriptor.addFamily(hColumnDescriptor);
        }
        admin.createTable(hTableDescriptor);
        close();
    }
    //列出所有表
    public void listTables() throws IOException {
        HTableDescriptor[] hTableDescriptors = admin.listTables();
        for (HTableDescriptor hTableDescriptor : hTableDescriptors) {
            System.out.println("表名:" + hTableDescriptor.getNameAsString());
        }
    }
    //（2）向列添加数据
    public void saveToHBase(ArticleInfo articleInfo) throws IOException{
        init();
        Table table = connection.getTable(TableName.valueOf("article_info"));
        String[] column_fields = {"info:aid", "info:type", "info:status", "info:is_top", "info:domain"};
        String[] values = {articleInfo.getAid(),articleInfo.getType().toString(),articleInfo.getStatus().toString(),articleInfo.getIsTop().toString(),articleInfo.getDomain()};
        for (int i = 0; i!= column_fields.length; i++) {
            Put put = new Put(articleInfo.getAid().getBytes());
            String[] cols = column_fields[i].split(":");
            put.addColumn(cols[0].getBytes(), cols[1].getBytes(), values[i].getBytes());
            table.put(put);
        }
        table.close();
        close();
    }
    public  void addRecord(String tableName, String row, String[] fields, String[] values) throws IOException {
        init();
        Table table = connection.getTable(TableName.valueOf(tableName));
        for (int i = 0; i!= fields.length; i++) {
            Put put = new Put(row.getBytes());
            String[] cols = fields[i].split(":");
            put.addColumn(cols[0].getBytes(), cols[1].getBytes(), values[i].getBytes());
            table.put(put);
        }
        table.close();
        close();
    }
    public static void main(String[] args) {
        ConsumerHBase consumerHBase = new ConsumerHBase();
        //用户基本信息表-列族
        String[] column_families1 = {"user_basic", "user_edu", "user_skill", "user_interest"};
        //文章基本信息表-列族
        String[] column_families2 = {"info"};
        //用户行为表-列族
        String[] column_families3 = {"behavior"};
        //用户基本信息表-列族-列
        String[] column_fields1 = {"user_basic:uid", "user_basic:sex", "user_basic:status", "user_basic:level", "user_edu:degree", "user_edu:school", "user_edu:major", "user_skill:skill", "user_interest:interest"};
        //文章基本信息表-列族-列
        String[] column_fields2 = {"info:aid", "info:type", "info:status", "info:is_top", "info:domain"};
        //用户行为表-列族-列
        String[] column_fields3 = {"behavior:uid", "behavior:aid", "behavior:behavior", "behavior:behavior_time"};

        String[] values1 = {"2015001", "Zhangsan", "Male", "23"};
        String[] values2 = {"123001", "Math", "2.0"};
        String[] values3 = {"2015001", "123001", "86"};
        try {
            consumerHBase.init();
            consumerHBase.createTable("user_info", column_families1);
            consumerHBase.createTable("article_info", column_families2);
            consumerHBase.createTable("user_behavior", column_families3);
            //hbaseTable.addRecord("Student_Course","2015001_123001",column_fields1,values1);
            //hbaseTable.addRecord("Student_Course","2015001_123001",column_fields2,values2);
            //hbaseTable.addRecord("Student_Course","2015001_123001",column_fields3,values3);
            consumerHBase.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

