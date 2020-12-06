package juc;

/**
 * Created by lengzefu on 2018/9/20.
 */
public class Human {
    private static int age;//静态成员变量
    private String name;//非静态成员变量

    //构造方法
    public Human(int age, String name){
        this.age = age;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        Human.age = age;
    }

    //静态方法
    private static void sing(){
        System.out.println("我可以唱歌哦");
    }
    //非静态方法
    private void dance(){
        System.out.println("我可以跳舞哦");
    }

    //内部类
    class Cat{
        private String color = "黄色";
        private void say(){
            int age = Human.this.age++;
            System.out.println(age);
            String name = Human.this.getName();
            System.out.println(name);
            System.out.println("我是一只猫");
        }

    }
    //静态内部类
    static class Dog{
        private static String color = "黑色";
        private void say(){
            int age = Human.getAge();
            System.out.println(age);
            //String name = Human.name; 静态内部类不能直接访问外部类的非静态成员变量
            System.out.println("我是一只狗");
        }

    }

    public static void main(String[] args){
        Human h = new Human(18,"晴天");
        Cat cat = h.new Cat();
        Dog dog = new Human.Dog();
        //Cat cat = Human.new Cat(); ----不被允许
        //Dog dog = h.new Dog(); ---不被允许
        /******************************/
        cat.say();
        dog.say();

        String color = h.new Cat().color;
    }

}
