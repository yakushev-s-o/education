package devcolibri.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollectionList {
    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        list.add(256);
        list.add(512);
        list.add(1024);
        list.add(2048);

        list.remove(0);
        System.out.println(list.get(0));

        for (int n: list
             ) {
            System.out.println(n);
        }

        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        boolean empty = list.isEmpty();
        System.out.println(empty);
    }
}
