package collection.hashmap;

/**
 * Created by lengzefu on 2019/3/7.
 */
public abstract class AbstractMap<K,V> implements Map<K,V>{

    /**
     * 唯一的构造函数。（对于子类构造函数的调用，通常是隐式的。）
     */
    protected AbstractMap(){
    }
    /**
     * 实现接口Map的方法，采用的是重写
     * @return
     */
    public int size(){
        return 0;
    }

    public V get(Object key){
        return null;
    }

    public V put(K k, V v){
        return null;
    }

    public static class SimpleEntry<K,V> implements Entry<K,V>{

        @Override
        public K getKey() {
            return null;
        }

        @Override
        public V getValue() {
            return null;
        }
    }
}
