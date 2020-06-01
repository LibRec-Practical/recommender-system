package service.streaming.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import service.service.PersonalizedRecommenderService;
import util.config.Configs;
import util.hbase.HBaseUtils_mjs;
import util.hbase.HbaseUtil;
import util.kafka.KafkaUtil;
import util.redis.RedisUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// import util.hbase.HbaseUtil;

@Component
public class MessageConsumer {
    //@Autowired
    //PersonalizedRecommenderService personalizedRecommenderService;
    private static PersonalizedRecommenderService personalizedRecommenderService = new PersonalizedRecommenderService();
    private static KafkaConsumer<String, String> consumer = KafkaUtil.getCommentTopicConsumer();

//    private static KafkaConsumer<String, String> consumer_test;

    static {
//        HashMap<String, Object> consumerParams = new HashMap<>();
//        consumerParams.put("bootstrap.servers", Configs.KAFKA_BROKERS);
//        consumerParams.put("group.id", "xlc_test");
//        consumerParams.put("auto.offset.reset", "latest");
//        consumerParams.put("auto.commit.interval.ms", "1000");
//        consumerParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        consumerParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        consumer_test = new KafkaConsumer<>(consumerParams);
//        consumer_test.subscribe(Arrays.asList("xlc_test"));
//        consumer.subscribe(Arrays.asList("librecmall.comment_r1p1"));
        consumer.subscribe(Arrays.asList("librecmall.footprint_r1p1"));
    }

    private Jedis jedis = RedisUtil.getConnection();


    @Scheduled(initialDelay = 2000, fixedRate = 10000)
    private void consume() {
        ConsumerRecords<String, String> records = consumer.poll(100);
        Pipeline pipeline = jedis.pipelined();

//            这里是李思宽的代码
        if (!records.isEmpty()){
            System.out.println("<<<Kafka实时轮询 消费数据>>>");
        }
        for (ConsumerRecord<String, String> record : records) {
            String recordJson = record.value();
            System.out.println(recordJson);
            JSONObject footprintJsonObject = JSON.parseObject(recordJson);
            String msg = footprintJsonObject.getString("msg");
            JSONObject msgJsonObject = JSON.parseObject(msg);
            int id = msgJsonObject.getInteger("id"); // 可以拿来当offset记录
            int userId = msgJsonObject.getInteger("userId");
            String goodId = msgJsonObject.getString("goodsId");
            String key = userId + "_" + goodId;

            try {
//                System.out.println("key: " + key + " value: " + jedis.incr(key));
                pipeline.incr(key);
//                jedis.incr(key);
//                HBaseUtils_mjs.add("librec_rec_res_pop",
//                        Integer.toString(row),
//                        "items",
//                        "rec_item_rate",
//                        Double.toString());
            } catch (Exception e) {
                System.err.println("something went wrong at item " + key);
//                e.printStackTrace();
//                return;
            }
        }
        pipeline.sync();


//            这里是杰生的代码
//            for (ConsumerRecord<String, String> record : records) {
//                String recordJson = record.value();
//                JSONObject commentJsonObject = JSON.parseObject(recordJson);
//                String msg = commentJsonObject.getString("msg");
//                JSONObject msgJsonObject = JSON.parseObject(msg);
//                String goodId = msgJsonObject.getString("valueId");
//                String star = msgJsonObject.getString("star");
//                int userId = msgJsonObject.getInteger("userId");
//                String goodAndRating = "["+goodId+","+star+"],";
//                if (userRatingsMap.containsKey(userId)){
//                    StringBuilder ratingsBuilder = userRatingsMap.get(userId);
//                    ratingsBuilder.append(goodAndRating);
//                }else{
//                    StringBuilder ratingsBuilder = new StringBuilder("[");
//                    ratingsBuilder.append(goodAndRating);
//                    userRatingsMap.put(userId,ratingsBuilder);
//                }
//            }
//            if (userRatingsMap.size() > 0){
//                for (Integer userId: userRatingsMap.keySet()){
//                    StringBuilder ratingsBuilder = userRatingsMap.get(userId);
//                    ratingsBuilder.deleteCharAt(ratingsBuilder.length() - 1);
//                    ratingsBuilder.append("]");
//                    String arrJson = ratingsBuilder.toString();
//                    JSONArray ratingsJSONArray = JSONObject.parseArray(arrJson);
//                    System.out.println(arrJson);
//
//                    personalizedRecommenderService.updateRecommend("IncrFromKafka",userId,ratingsJSONArray);
//                }
//            }


//        ConsumerRecords<String, String> consumerRecords = consumer.poll(100);
//         Pipeline pipeline = jedis.pipelined();
//        System.out.println("定时消费数据");
//       for (ConsumerRecord<String, String> record : consumerRecords) {
//            String value = "unknown";
//            try {
//                value = record.value();
//                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), value);
//                System.out.println("key: " + value + " value: " + jedis.incr(value));
//                 pipeline.incr(value);
//                jedis.incr(value);
//                HbaseUtil.insertData(Configs.HBASE_TABLE_NAME, Configs.HBASE_FAMILY_NAME, Configs.HBASE_QUALIFIER, value, String.valueOf(1));
//            } catch (Exception e) {
//                System.err.println("something went wrong at item " + value);
//                e.printStackTrace();
//                return;
//            }
//        }
        //pipeline.sync();

    }


//
//    @Scheduled(initialDelay = 2000, fixedRate = 10000)
//    private void consumeTest() {
// 测试Kafka broker
//    }
}

