package edu.bjtu.analysis.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;


import java.io.IOException;
import java.sql.Connection;

import static org.apache.hadoop.hbase.client.ConnectionFactory.createConnection;

public class HBaseUtils {
    public static long ts;
    protected static Configuration configuration;
    protected static org.apache.hadoop.hbase.client.Connection connection;
    protected static Admin admin;

    //初始化，建立连接
    public static void init() {
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
    public static void close() {
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
    public  static void createTable(String tableName, String[] fields) throws IOException {
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
    }
    //（2）向列添加数据
    public static void addRecord(String tableName, String row, String[] fields, String[] values) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        for (int i = 0; i!= fields.length; i++) {
            Put put = new Put(row.getBytes());
            String[] cols = fields[i].split(":");
            put.addColumn(cols[0].getBytes(), cols[1].getBytes(), values[i].getBytes());
            table.put(put);
        }
        table.close();
    }
    //（3）浏览表某一列数据
    public static void scanColumn(String tableName,String family, String column,boolean isFamily) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        if (isFamily)
            scan.addFamily(Bytes.toBytes(family));
        else
            scan.addColumn(Bytes.toBytes(family),Bytes.toBytes(column));
        ResultScanner scanner = table.getScanner(scan);
        for (Result result = scanner.next(); result != null; result = scanner.next()) {
            showCell(result);
        }
        table.close();
    }

    public static void showCell(Result result) {
        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            System.out.print("行键:" + new String(CellUtil.cloneRow(cell)) + " ");
            System.out.print("时间戳:" + cell.getTimestamp() + " ");
            System.out.print("列族:" + new String(CellUtil.cloneFamily(cell)) + " ");
            System.out.print("列名:" + new String(CellUtil.cloneQualifier(cell)) + " ");
            System.out.print("值:" + new String(CellUtil.cloneValue(cell)) + " ");
            System.out.println(" ");
        }
    }
    // 获取表的所有数据
    public static void getData(String tableName) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            showCell(result);
        }
    }
}
