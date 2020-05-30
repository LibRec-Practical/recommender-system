package util.kafka;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.PartitionInfo;
import org.apache.spark.Partition;
import org.springframework.beans.factory.annotation.Autowired;
import service.service.PersonalizedRecommenderService;
import util.config.Configs;

import java.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class KafkaUtil {
    private static AdminClient adminClient;
    private static Properties properties;
    // kafka相关参数
    private static HashMap<String, Object> producerParams = new HashMap<>();
    private static HashMap<String, Object> consumerParams = new HashMap<>();


    static {
        properties = new Properties();
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, Configs.KAFKA_BROKERS);
        // producer
        producerParams.put("bootstrap.servers", Configs.KAFKA_BROKERS);
        producerParams.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerParams.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // consumer
        consumerParams.put("bootstrap.servers", Configs.KAFKA_BROKERS);
        consumerParams.put("group.id", Configs.KAFKA_CONSUMER_GROUP);
        consumerParams.put("auto.offset.reset", "latest");
        consumerParams.put("auto.commit.interval.ms", "1000");
        consumerParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    private KafkaUtil() {
    }

    public static void createTopicIfNotExist(String topic) {
        try {
            adminClient = AdminClient.create(properties);
            if (null != adminClient.describeTopics(Arrays.asList(topic))) {
                System.out.println("主题 " + topic + " 已存在");
                return;
            }
            NewTopic newTopic = new NewTopic(topic, 2, (short) 2);
            CreateTopicsResult createTopicsResult = adminClient.createTopics(Arrays.asList(newTopic));
            createTopicsResult.all().get();
            adminClient.close();
            System.out.println("创建主题成功：" + topic);
        } catch (Exception e) {
            System.err.println("创建主题 " + topic + " 失败");
            e.printStackTrace();
        }
    }

    public static KafkaConsumer<String, String> getCommentTopicConsumer(){
        consumerParams.put("group.id","mj_test_kfk");
        consumerParams.put("auto.offset.reset","earliest");
        consumerParams.put("enable.auto.commit",false);
        return new KafkaConsumer<String, String>(consumerParams);
    }

    public static void deleteTopic(String topic) {
        try {
            adminClient = AdminClient.create(properties);
            DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Arrays.asList(topic));
            deleteTopicsResult.all().get();
            adminClient.close();
            System.out.println("创建主题成功：" + topic);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static KafkaProducer<String, String> getProducer() {
        return new KafkaProducer<>(producerParams);
    }

    public static KafkaConsumer<String, String> getConsumer() {
        return new KafkaConsumer<>(consumerParams);
    }

    public static void main(String[] args){
    }
}