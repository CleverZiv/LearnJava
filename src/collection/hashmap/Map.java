package collection.hashmap;

/**
 * Created by lengzefu on 2019/3/7.
 * 接口一般只定义方法
 */
public interface Map<K,V> {
    /*******第一阶段********/
    /**
     * 返回map中的键值对总数目，
     * 如果键值对数目大于Integer.MAX_VALUE，则返回Integer.MAX_VALUE
     * @return
     */
    int size();

    /**
     * 返回键对应的值，如果不存在该键值对则返回null
     * 当此map允许值为null时，返回null就不代表一定不存在该键值对
     * @param key
     * @return
     */
    V get(Object key);

    /**
     * 之前不存在则添加，存在则覆盖value
     * @param key
     * @param value
     * @return
     */
    V put(K key, V value);

    /**
     * 定义map中存储的元素结构
     * @param <K>
     * @param <V>
     */
    interface Entry<K,V>{
        /**
         * 返回此entry相关联的key
         * @return
         */
        K getKey();

        /**
         * 返回此entry相关联的value
         * @return
         */
        V getValue();
    }
}
