package service.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.GoodsMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import pojo.Goods;
import recommendation.mf.NonNegativeMatrixFactorization;
import util.calculation.Matrix;
import util.hbase.HBaseUtils_mjs;
import util.json.JsonUtil;
import util.logger.LoggerUtil;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Component
public class PersonalizedRecommenderService {

    private static final Logger logger = Logger.getLogger(PersonalizedRecommenderService.class);

    @Autowired
    private NonNegativeMatrixFactorization nonNegativeMatrixFactorization;

    @Autowired
    private GoodsMapper goodsMapper;



    public synchronized String recommendRelated(String sessionId, Integer userId) {
        // 获取一些新商品
        List<Goods> newGoods = goodsMapper.getNewGoods();
        // 获取推荐结果
        ArrayList<Integer> recommend = nonNegativeMatrixFactorization.recommend(userId);
        int size = recommend.size();
        HashSet<Integer> set = new HashSet<>();
        for(Goods goods: newGoods) {
            set.add(goods.getId());
        }
        int index = 0;
        while(index < size && set.size() < 10) {
            set.add(recommend.get(index++));
        }
        ArrayList<Goods> goodsList = getGoods(set);
        JSONObject data = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Goods goods : goodsList) {
            JSONObject item = new JSONObject();
            item.put("id", goods.getId());
            item.put("name", goods.getName());
            item.put("brief", goods.getBrief());
            item.put("picUrl", goods.getPicUrl());
            item.put("isNew", goods.getIsNew());
            item.put("isHot", goods.getIsHot());
            item.put("counterPrice", goods.getCounterPrice());
            item.put("retailPrice", goods.getRetailPrice());
            jsonArray.add(item);
        }
        data.put("list", jsonArray);
        String result = JsonUtil.getJsonResult(0, "成功", data);
        logger.info(LoggerUtil.info(sessionId, result));
        return result;
    }

    private ArrayList<Goods> getGoods(Collection<Integer> goodIds) {
        ArrayList<Goods> result = new ArrayList<>();
        for(Integer goodsId: goodIds) {
            result.add(goodsMapper.selectByPrimaryKey(goodsId));
        }
        return result;
    }

    /**
     * 给的数据应该包含一列评分数据，格式看items注解
     * @param sessionId token
     * @param userId 用户id
     * @param items ["item1的id rate1", "item2的id, rate2", ...]
     * @return
     */
    public synchronized String updateRecommend(String sessionId, Integer userId, JSONArray items) {
        System.out.println("updating rec");
        System.out.println(items);
        System.out.println(items.size());
        ArrayList<String> arrayList = new ArrayList<>(items.size());
        for(int i = 0; i < items.size(); ++i) {
            arrayList.add("0");
        }
        for(int i = 0; i < items.size(); ++i) {
            arrayList.set(i, items.getString(i));
        }
        nonNegativeMatrixFactorization.trainIncr(userId, arrayList);
        String result = JsonUtil.getJsonResult(0, "成功");
        logger.info(LoggerUtil.info(sessionId, result));
        // 将更新后的推荐结果写入HBase
        Matrix rec_res_matrix = nonNegativeMatrixFactorization.getW().multiply(nonNegativeMatrixFactorization.getH());
        double[][] rec_res_array = rec_res_matrix.num;
        for (int row = 0;row<rec_res_matrix.getRow();row++){
            for (int col = 0;row<rec_res_matrix.getColumn();col++){
                try {
                    HBaseUtils_mjs.add("Personized_rec",
                            Integer.toString(row),
                            "items",
                            "rec_item_rate",
                            Double.toString(rec_res_array[row][col]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
