package collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.ServiceLoader;

/**
 * Created by lengzefu on 2018/10/18.
 */
public class ArrayLis {
    public static void main(String[] args){
        ArrayList<Integer> arrayList1 = new ArrayList<Integer>();
        arrayList1.add(1);
        arrayList1.add(2);
        arrayList1.add(3);
        arrayList1.add(4);
        arrayList1.add(5);
/*        Iterator<Integer> iter = arrayList1.iterator();
        while(iter.hasNext()){
            int next = iter.next();
            System.out.print(next + " ");
        }*/
        arrayList1.remove(2);
        ListIterator<Integer> listIter = arrayList1.listIterator();
        while(listIter.hasNext()){
            int next = listIter.next();
            System.out.print(next + " ");
        }
    }

}
