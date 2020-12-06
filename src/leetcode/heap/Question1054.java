package leetcode.heap;


import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * @Classname Question1054
 * @Date 2020/8/8 16:03
 * @Autor lengzefu
 */
public class Question1054 {
    public static void main(String[] args) {

    }

    /**
     * 在一个仓库里，有一排条形码，其中第 i 个条形码为 barcodes[i]。
     * 请你重新排列这些条形码，使其中两个相邻的条形码 不能 相等。 你可以返回任何满足该要求的答案，此题保证存在答案。
     *
     * @param barcodes
     * @return
     */
    private static int[] rearrangeBarcodes(int[] barcodes) {
        /**
         * 只需要保证相邻的条形码不相同，且可以返回任何满足要求的答案。
         * 如何保证相邻元素不相同呢？假设我每次只写入两个数，如果我能保证这两个数属于不同类型的数，就可以保证相邻的数不相同。
         * 写入的第一个数是数量最多的数，写入的第二个数是数量第二多的数
         * 通过哈希表存放不同元素和元素的数量，通过优先队列实现对元素的排序
         */
        HashMap<Integer, Integer> map = new HashMap<>();
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return map.get(o2) - map.get(o1);
            }
        });
        //遍历条形码数组，放入map中
        for(int code : barcodes) {
            if(map.containsKey(code)) {
                map.put(code, map.get(code)+1);
            }else {
                map.put(code, 1);
            }
        }
        int[] res = new int[barcodes.length];
        int index = 0;
        queue.addAll(map.keySet());
        while(queue.size() > 1) {
            int mostCommon = queue.poll();
            res[index++] = mostCommon;
            int mostCommonNum = map.get(mostCommon);
            if(mostCommonNum > 1) {
                map.put(mostCommon, mostCommonNum-1);
                queue.add(mostCommon);
            }
            int secondCommon = queue.poll();
            res[index++] = secondCommon;
            int secondCommonNum = map.get(secondCommon);
            if(secondCommonNum > 1) {
                map.put(secondCommon, secondCommonNum-1);
                queue.add(secondCommon);
            }
        }
        if(!queue.isEmpty()) {
            res[index++] = queue.poll();
        }
        return res;
    }

}
