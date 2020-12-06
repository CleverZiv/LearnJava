package bookjdk8.chapter2.impl;

import bookjdk8.chapter2.Apple;
import bookjdk8.chapter2.ApplePredicate;

/**
 * @Classname RedAndWeightApplePredicate
 * @Date 2020/5/29 0:57
 * @Autor lengzefu
 */
public class RedAndWeightApplePredicate implements ApplePredicate {
    @Override
    public boolean filter(Apple apple) {
        if("red".equals(apple.getColor()) && apple.getWeight() > 150) {
            return true;
        }
        return false;
    }
}
