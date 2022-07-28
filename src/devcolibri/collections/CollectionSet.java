package devcolibri.collections;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CollectionSet {

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("string1");
        set.add("string2");
        set.add("string3");
        set.add("string4");

        System.out.println(set);

        set.remove("string2");

        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}
