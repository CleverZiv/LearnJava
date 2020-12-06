package leetcode.heap;

import java.util.Arrays;

/**
 * @Classname Question743
 * @Date 2020/8/8 11:09
 * @Autor lengzefu
 */
public class Question743 {
    public static void main(String[] args) {

    }
    private static int networkDelayTime(int[][] times, int N, int K) {
        /**
         * 首先要理解题中给出的二维数组是怎么表示节点和距离信息的，从题干中，我们知道times的结构如下：
         * 2 1 1
         * 2 3 1
         * 3 4 1
         * 即time[i]表示一组节点距离关系，如time[1]表示节点2（time[1][0]）到节点1（time[1][1]）之间的距离是1（time[1][2]）
         */
        //首先将题中的二维数组转换为领接表
        int[][] graph = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                graph[i][j] = -1;
            }
        }
        for (int[] t : times) {
            graph[t[0]][t[1]] = t[2];
        }
        //保存每个节点的最短路径，该题要求的就是当每个节点都存在最短路径时，最短路径中最大的值
        int[] distance = new int[N + 1];
        Arrays.fill(distance, -1);
        //初始化distance为K到各个节点的距离
        for (int i = 1; i < N + 1; i++) {
            distance[i] = graph[K][i];
        }
        distance[K] = 0;
        //判断该点是否已经以最短路径到达过
        boolean[] visited = new boolean[N + 1];
        visited[K] = true;
        //开始遍历邻接表
        for (int i = 1; i < N + 1; i++) {
            //第一层遍历(内层遍历)是为了找到与K距离最小的点,可以是多个
            int minDistance = Integer.MAX_VALUE;
            int minIndex = 1;
            for (int j = 1; j < N + 1; j++) {
                if (!visited[j] && distance[j] != -1 && distance[j] < minDistance) {
                    minIndex = j;
                    minDistance = distance[j];
                }
            }
            visited[minIndex] = true;
            //根据刚刚找到的最短距离节点，更新所有该节点能访问到节点与K节点之间的距离
            for (int j = 1; j < N + 1; j++) {
                if (graph[minIndex][j] != -1) {
                    if (distance[j] != -1) {
                        //说明该点已经有过最短距离记录，故需要判断哪一个更短
                        distance[j] = Math.min(distance[j], distance[minIndex] + graph[minIndex][j]);
                    } else {
                        //第一次访问到
                        distance[j] = distance[minIndex] + graph[minIndex][j];
                    }
                }
            }

        }
        int res = 0;
        //遍历distance，找到最短距离中的最大值
        for(int i = 1; i < N+1; i++) {
            if(distance[i] == -1) {
                return -1;
            }
            res = Math.max(distance[i], res);
        }
        return res;

    }
}
