package util;

public class Configs {

    public static final String MASTER_HOST = "172.16.29.65";
    public static final String SLAVE_1_HOST = "172.16.29.64";
    public static final String SLAVE_2_HOST = "172.16.29.66";
    /**
     * kafka
     */
//    public static final String KAFKA_BROKERS = "localhost:2181";
//    public static final String KAFKA_BROKERS = "master:2181,slave1:2181,slave2:2181";
    /*
    172.16.29.65 master
    172.16.29.64 slave1
    172.16.29.66 slave2
     */
    public static final String KAFKA_BROKERS = "172.16.29.65:9092,172.16.29.64:9092,172.16.29.66:9092";
    public static final String ZOOKEEPER_BROKERS = "172.16.29.65:2181,172.16.29.64:2181,172.16.29.66:2181";
    public static final String KAFKA_TOPIC = "xlc";
//    public static final String KAFKA_MASTER = "local[1]";
    public static final String KAFKA_MASTER = "spark://" + MASTER_HOST + ":7077";
    public static final String KAFKA_PRODUCER_GROUP = "xlc_producer";
    public static final String KAFKA_CONSUMER_GROUP = "xlc_consumer";

    /**
     * HBase
     */
    public static final String HBASE_TABLE_NAME = "xlc";
    public static final String HBASE_FAMILY_NAME = "count";
    public static final String HBASE_QUALIFIER = "items";
    public static final String HBASE_MASTER = MASTER_HOST + ":60000";
    public static final String HBASE_QUORUM = MASTER_HOST + ":60000";

}
