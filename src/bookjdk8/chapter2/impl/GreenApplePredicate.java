package bookjdk8.chapter2.impl;

import bookjdk8.chapter2.Apple;
import bookjdk8.chapter2.ApplePredicate;

/**
 * @Classname GreenApplePredicate
 * @Date 2020/5/29 0:55
 * @Autor lengzefu
 */
public class GreenApplePredicate implements ApplePredicate {
    @Override
    public boolean filter(Apple apple) {
        if("green".equals(apple.getColor())){
            return true;
        }
        return false;
    }
}
