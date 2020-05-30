package recommendation.mf;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import util.calculation.Matrix;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * 方法名基本都能看懂，就不挨个翻译了
 *  其中增量学习的方法名都会带Incr后缀
 */
@Component
public class NonNegativeMatrixFactorization {

    // 一些参数

    private Matrix V;

    private Matrix W, H;

    private int row, column;

    private double alpha = 1.0, beta = 1.0;

    private double eps = 1e-3;

    private int base;

    private int round = 100;

    // 下面四个类按名字来看就行，用来转换id和对应坐标的
    // 比如userIdToColumnMap就是用户id转对应列号，itemIdToRowMap就是商品id转对应行号，另外两个就是反过来
    // 评分矩阵是商品-用户的，行号代表商品，列号代表用户
    private HashMap<Integer, Integer> userIdToColumnMap = new HashMap<>();

    private HashMap<Integer, Integer> columnToUserIdMap = new HashMap<>();

    private HashMap<Integer, Integer> itemIdToRowMap = new HashMap<>();

    private HashMap<Integer, Integer> rowToItemIdMap = new HashMap<>();

    private static class Node {
        public Integer iid;
        public Double rate;
        public Node(int iid, double rate) {
            this.iid = iid;
            this.rate = rate;
        }
    }

    public Matrix getW() {
        return W;
    }

    public Matrix getH() {
        return H;
    }

    public NonNegativeMatrixFactorization() {}

    public NonNegativeMatrixFactorization(Matrix v) {
        this.V = v;
        this.row = v.getRow();
        this.column = v.getColumn();
    }

    @PostConstruct
    public void loadData() throws IOException {
        this.row = 20;
        this.column = 10;
        V = new Matrix(row, column);
        try {
            File file = new File("D:\\PROJECT\\recommender-system\\src\\main\\resources\\localTrainData\\ratings.txt");
            InputStreamReader inputStream = new InputStreamReader(new FileInputStream(file));
            BufferedReader reader = new BufferedReader(inputStream);
            String line;
            int count = 0;
            int maxuid = 1;
            int maxiid = 1;
            double maxrating = 0;
            while((line = reader.readLine()) != null) {
                String[] nums = line.split(" ");
                ++count;
//                System.out.println(count +  " user id: " + nums[0] + " item id: " + nums[1] + " rating: " + nums[2]);
                int uid = Integer.parseInt(nums[0]);
                int iid = Integer.parseInt(nums[1]);
                double rate = Double.parseDouble(nums[2]);
                maxuid = Math.max(maxuid, uid);
                maxiid = Math.max(maxiid, iid);
                maxrating = Math.max(maxrating, rate);
                if(uid > column) {
                    break;
                }
                if(iid <= row) {
                    if(!userIdToColumnMap.containsKey(uid)) {
                        columnToUserIdMap.put(uid - 1, uid);
                        userIdToColumnMap.put(uid, uid - 1);
                        rowToItemIdMap.put(iid - 1, iid);
                        itemIdToRowMap.put(iid, iid - 1);
                        V.num[iid - 1][uid - 1] = rate;
                    }
                }
            }
//            System.out.println("maxuid: " + maxuid + " maxiid: " + maxiid + " maxrating: " + maxrating);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("V:");
        V.print();
    }

    public void trainIncr(double[] vK) {
        trainIncr(vK, this.eps);
    }

    /**
     * 给的数据应该包含一列评分数据，格式看items注解
     * @param userId 用户id
     * @param items ["item1的id, rate1", "item2的id, rate2", ...]
     */
    public void trainIncr(Integer userId, ArrayList<String> items) {
        if(!userIdToColumnMap.containsKey(userId)) {
            userIdToColumnMap.put(userId, columnToUserIdMap.size());
            columnToUserIdMap.put(columnToUserIdMap.size(), userId);
        }
        double[] vK = new double[V.getRow()];
        // 设置一个默认的喜好
        vK[0] = 3.0;
        for (String item: items) {
            // 商品id和评分拆开
            String[] strings = item.split(" ");
            int itemId = Integer.valueOf(strings[0]);
            itemId = itemIdToRowMap.get(itemId);
            double rate = Double.valueOf(strings[1]);
            vK[itemId] = rate;
        }
        trainIncr(vK);

    }

    /**
     * train with new data
     * @param vK 矩阵V新添加的一列，对应某个用户对商品的所有评分
     * @param eps limit to stop iteration
     */
    public void trainIncr(double[] vK, double eps) {
        double[] hK = new double[H.getRow()];
        for(int i = 0; i < hK.length; ++i) {
            hK[i] = H.num[i][H.getColumn() - 1];
        }
        Matrix HT = H.transpose();
        double temp = 0;
        for(int count = 1; count <= round; ++count) {
            Matrix a = new Matrix(vK.length, hK.length);
            for (int i = 0; i < vK.length; ++i) {
                for (int j = 0; j < hK.length; ++j) {
                    a.num[i][j] = vK[i] * hK[j];
                }
            }
            a = a.add(V.multiply(HT)); // a: V.row * H.row(which is base)
            Matrix b = new Matrix(hK.length, hK.length);
            for (int i = 0; i < hK.length; ++i) {
                for (int j = 0; j < hK.length; ++j) {
                    b.num[i][j] = hK[i] * hK[j];
                }
            }
            b = W.multiply(b).add(W.multiply(H).multiply(HT));
            Matrix WT = W.transpose();
            double[] c = WT.multiply(vK);
            double[] d = WT.multiply(W).multiply(hK);
            for(int i = 0; i < W.getRow(); ++i) {
                for(int j = 0; j < W.getColumn(); ++j) {
                    W.num[i][j] *= a.num[i][j] / (b.num[i][j] + alpha);
                }
            }
            for(int i = 0; i < hK.length; ++i) {
                hK[i] *= c[i] / (d[i] + beta);
            }
            // balabala
            if((temp = lossIncr(vK, hK)) < eps) {
                break;
            }
            System.out.println("train(incr) round: " + count + " loss: " + temp);
        }
        H = H.addColumn(hK);
        V = V.addColumn(vK);
//        System.out.println("W:");
//        W.print();
//        System.out.println("H:");
//        H.print();
    }

    public void train() {
        train(this.eps);
    }

    public void train(double eps) {
        base = Math.max((row * column) / (row + column), 1);
        W = new Matrix(row, base, true);
        H = new Matrix(base, column, true);
        double temp = 0;
        for(int count = 1; count <= round; ++count) {
            Matrix WT = W.transpose();
            Matrix a = WT.multiply(V);
            Matrix b = WT.multiply(W).multiply(H);
            Matrix HT = H.transpose();
            Matrix c = V.multiply(HT);
            Matrix d = W.multiply(H).multiply(HT);
            for(int i = 0; i < base; ++i) {
                for(int j = 0; j < column; ++j) {
                    H.num[i][j] *= a.num[i][j] / b.num[i][j];
                }
            }
            for(int i = 0; i < row; ++i) {
                for(int j = 0; j < base; ++j) {
                    W.num[i][j] *= c.num[i][j] / d.num[i][j];
                }
            }
            if((temp = loss()) < eps) {
                break;
            }
            System.out.println("train round: " + count + " loss: " + temp);
        }
//        System.out.println("W:");
//        W.print();
//        System.out.println("H:");
//        H.print();

    }

    private double loss() {
        return V.substract(W.multiply(H)).norm2Pow() * 0.5 + alpha * W.norm1() + beta * H.norm1();
    }

    private double lossIncr(double[] vK, double[] hK) {
        double answer = 0;
        double[] temp = W.multiply(hK);
        for(int i = 0; i < vK.length; ++i) {
            answer += 0.5 * Math.pow(vK[i] - temp[i], 2);
        }
        for(int i = 0; i < hK.length; ++i) {
            answer += beta * hK[i];
        }
        return answer + loss();
    }

    /**
     * 获取目标用户的推荐结果并返回
     *     返回前将商品id转换成原id
     * @param uid 用户id
     * @return
     */
    public ArrayList<Integer> recommend(int uid) {
        uid = userIdToColumnMap.get(uid);
        Matrix result = W.multiply(H);
        ArrayList<Node> recommend = new ArrayList<>();
        for(int i = 0; i < V.getRow(); ++i) {
            if(0 == V.num[i][uid - 1]) {
                recommend.add(new Node(i + 1, result.num[i][uid - 1]));
            }
        }
        Collections.sort(recommend, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                int answer = o1.rate.compareTo(o2.rate);
                if(0 == answer) {
                    return o1.iid.compareTo(o2.iid);
                } else {
                    return answer;
                }
            }
        });
        ArrayList<Integer> iidList = new ArrayList<>();
        for(Node node: recommend) {
            iidList.add(node.iid);
        }
        for(int i = 0; i < iidList.size(); ++i) {
            iidList.set(i, rowToItemIdMap.get(iidList.get(i)));
        }
        return iidList;
    }

}
