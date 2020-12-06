package leetcode.greedy;

/**
 * @Classname Questtion1518
 * @Date 2020/8/29 15:32
 * @Autor lengxuezhang
 */
public class Questtion1518 {

    public static void main(String[] args) {
        int res = numWaterBottles(9,3);
        System.out.println(res);
    }

    public static int numWaterBottles(int numBottles, int numExchange) {
        int i = 0;
        while(i < numBottles) {
            i++;
            if(i%numExchange == 0) {
                numBottles++;
            }
        }
        return numBottles;
    }
}
