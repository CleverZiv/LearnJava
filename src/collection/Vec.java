package collection;

import java.util.ListIterator;
import java.util.Vector;

/**
 * Created by lengzefu on 2018/10/25.
 */
public class Vec {
    public static void main(String[] args){
        try{
            Vec vec = new Vec();
            //测试迭代器的remove方法修改集合结构会不会触发checkForComodification异常
            vec.ItrRemoveTest();
            System.out.println("-------------------------");
            //测试集合的remove方法修改集合结构会不会触发checkForComodification异常
            vec.ListRemoveTest();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //测试迭代器的remove方法修改集合结构会不会触发checkForComodification异常
    private void ItrRemoveTest() {
        Vector list = new Vector<>();
        list.add("1");
        list.add("2");
        list.add("3");
        ListIterator itr = list.listIterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
            //迭代器的remove方法修改集合结构
            itr.remove();
        }
    }

    //测试集合的remove方法修改集合结构会不会触发checkForComodification异常
    private void ListRemoveTest() {
        Vector list = new Vector<>();
        list.add("1");
        list.add("2");
        list.add("3");
        ListIterator itr = list.listIterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
            //集合的remove方法修改集合结构
            list.remove("3");
        }
    }

}
