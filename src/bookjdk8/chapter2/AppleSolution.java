package bookjdk8.chapter2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @Classname AppleSolution
 * @Date 2020/5/29 0:21
 * @Autor lengzefu
 */
public class AppleSolution {

    public static void main(String[] args) {
        List<Apple> inventory = new ArrayList<>();
        inventory.add(new Apple("green", 100));
        inventory.add(new Apple("red", 110));
        inventory.add(new Apple("green", 152));
        inventory.add(new Apple("red", 190));
        inventory.add(new Apple("red", 90));
        inventory.add(new Apple("green", 302));

        //使用匿名类
        List<Apple> result = filterAppleWithPredicate(inventory, new ApplePredicate() {
            @Override
            public boolean filter(Apple apple) {
                if("red".equals(apple.getColor()) && apple.getWeight() > 150) {
                    return true;
                }
                return false;
            }
        });
        System.out.println(result.size());

        //但使用匿名类还是不够简洁，于是Java8给出了目前为止最简洁的方式，也是行为参数化的最好体现，直接传递所需要
        //传递的代码，而不需要依附于任何的类或对象
        List<Apple> result2 = filterAppleWithPredicate(inventory, (Apple apple) -> apple.getColor().equals("red"));
        //代码非常简洁，但有一个问题，如何拼接多个条件？


    }

    /**
     * 给一堆苹果，筛选出其中全部为绿颜色的苹果。
     * 注意考虑代码的灵活性和简洁性
     */

    /**
     * 方法1:
     * 缺点：假设此时又需要筛选红颜色的苹果，那么需要再重新写一个filterRedApple的方法
     * 两种方法唯一的不同只在于"green".equals(apple.getColor())换成了"red".equals(apple.getColor())
     *
     * @param inventory
     * @return
     */

    public static List<Apple> filterGreenApple1(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }


    /**
     * 方法2：
     * 该方法将所需要筛选的“颜色”作为参数，进行传递了，避免了筛选不同颜色的苹果时，需要重复写方法
     *
     * @param inventory
     * @param color
     * @return
     */
    public static List<Apple> filterColorApple(List<Apple> inventory, String color) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if (color.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 那此时，如果我们需要筛选重量大于150g的苹果呢？方法2就不能再被使用了
     * 那只能重新写一个关于筛选重量的方法
     */

    /**
     * 方法3：
     * 能实现需求，但关于遍历这块的代码是复制的，造成了代码的重复
     * 能否实现一个方法，可以把这些筛选功能都包含进去呢？
     *
     * @param inventory
     * @param weight
     * @return
     */
    public static List<Apple> filterWeightApple(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if (weight < apple.getWeight()) {
                result.add(apple);
            }
        }
        return result;
    }


    /**
     * @param inventory
     * @param color     需要筛选的颜色
     * @param weight    需要筛选的重量
     * @param flag      需要筛选的属性，true表示筛选颜色，flase表示筛选重量
     * @return
     *
     * 非常糟糕的设计！！！
     */
    public static List<Apple> filterApples(List<Apple> inventory, String color, int weight, boolean flag) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if (flag && color.equals(apple.getColor()) ||
                    !flag && weight < apple.getWeight()) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 至此，我们希望站在一个更高的层次对这个问题进行抽象：有一堆苹果，按照某种方式对其进行筛选（方式任选）。
     * 也就是说，按照什么方式筛选是由用户决定的
     */

    /**
     * 通过创建不同的ApplePredicate实现类的对象，可以为该方法赋予无限的灵活性
     * 缺点：每次都要为一种类型的比较创建一个ApplePredicate的实现类，然后再获得这个实现类的对象，代码不简洁
     * @param inventory
     * @param applePredicate
     * @return
     */
    public static List<Apple> filterAppleWithPredicate(List<Apple> inventory, ApplePredicate applePredicate) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if (applePredicate.filter(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 对抗代码的不简洁，采用匿名类，体现的具体的使用上，详见Main函数
     */

    /**
     * 引出lambda后，其实还可以再往上抽象：给定一堆物品（苹果或梨、沙子、水泥等等），根据某一方式筛选
     *
     * 这就用到了泛型，方法定义如下
     */

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        return null;
    }

}
