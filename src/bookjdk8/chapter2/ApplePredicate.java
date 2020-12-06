package bookjdk8.chapter2;

/**
 * @Classname ApplePredicate
 * @Date 2020/5/29 0:54
 * @Autor lengzefu
 */
public interface ApplePredicate {

    /**
     * 某种筛选苹果的标准
     * @param apple
     * @return
     */
    boolean filter(Apple apple);
}
