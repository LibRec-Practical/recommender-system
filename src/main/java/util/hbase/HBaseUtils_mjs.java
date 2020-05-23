package util.hbase;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;


public class HBaseUtils_mjs {
    static Configuration conf;
    static Connection connection = null;
    static Admin admin = null;
    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "172.16.29.65");
        try {
            connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close() throws IOException {
        if (!connection.isClosed()){
            admin.close();
            connection.close();
        }
    }

    // 插入数据
    @SuppressWarnings("all")
    public static void add(String tableName,String rowKey,String family,String qualifier,String value) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        System.out.println("adding");
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
        table.put(put);
        System.out.println("added: "+value);
        //System.out.println("===========");
    }



    public static void main(String args[]) throws IOException {
        String colFamily = "Value";
        String qualifier = "fullString";
        add("Normal","testKey",colFamily,qualifier,"testVal");
    }
}

