package collection.hashmap;

/**
 * Created by lengzefu on 2019/3/7.
 */
public class HashMap<K,V> extends AbstractMap<K, V> implements Map<K,V>{

    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; //初始容量16
    static final int MAXIMUM_CAPACITY = 1 << 30; //最大容量
    static final float DEFAULT_LOAD_FACTOR = 0.75f; //默认负载因子

    Node<K,V>[] table;
    int size; //map中键值对的数量
    public int size() {
        return size;
    }

    public V get(Object key) {
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        if((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n-1) & hash]) != null){
            if(first.hash == hash && ((k = first.key) == key ||
                    (key != null) && key.equals(k))){
                return first; //先查看第一个节点
            }
            if((e = first.next) != null){
                do {
                    if(e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                }while ((e = e.next) != null);
            }
        }
        return null;
    }

    /**
     * 通过异或运算，将高位参与到运算中，使元素尽可能分布均匀
     * @param key
     * @return
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public V put(K k, V v) {
        return super.put(k, v);
    }

    /**
     * HashMap中的基本元素，节点
     * @param <K>
     * @param <V>
     */
    static class Node<K,V> implements Map.Entry<K,V>{
        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        public Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }
    }
}
