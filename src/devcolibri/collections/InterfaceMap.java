package devcolibri.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InterfaceMap {
    public static void main(String[] args) {

        Map<String, Integer> map  = new HashMap<String, Integer>();
        map.put("key1", 1);
        map.put("key2", 2);
        map.put("key3", 3);
        map.put("key4", 4);
        map.put("key5", 5);

        System.out.println(map);

        Integer result = map.get("key2");
        System.out.println(result);

        map.remove("key3");

        Set<String> strings = map.keySet();
        for (String i: strings
             ) {
            System.out.println(i);
        }

        for (Integer i: map.values()
             ) {
            System.out.println(i);
        }

    }

}
