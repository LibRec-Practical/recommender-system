package recommendation.fm;

public class Sigmoid {
    public static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }
}