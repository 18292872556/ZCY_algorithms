package class02;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/05/12/15:46
 * @Description:
 */
public class HashMapAndSortedMap {
    public static class Node{
        public int value;
        public Node(int v){
            value = v;
        }
    }
    public static class Zuo{
        public int value;
        public Zuo(int v){
            value = v;
        }
    }
    public static void main(String[] args){
        //HashMap存包装类对象
        //-----------查看containsKey（key）查键key是否存在，包装类是必须同一个对象还是key对象的值相同即算相同，算键存在-------------------------------------
        System.out.println("=============================");
        System.out.println("1.HashMap存包装类对象,查看containsKey（key）查键key是否存在，包装类是必须同一个对象还是key对象的值相同即算相同，算键存在");
        HashMap<Integer, String> test = new HashMap<>();
        Integer a = 19000000;
        Integer b = 19000000;
        System.out.println(a==b);

        test.put(a,"我是3");
        //containsKey(b)查看是否存在key==b
        // 查一下与key同包装类值就存在，还是必须为同一个包装类对象
        System.out.println(""+test.containsKey(b));


        //HashMap存自定义类对象
        //---------查看containsKey(key)查键key是否存在，自定义类是必须为同一个对象才能找到存在，还是对象的值相同即算相同，算键存在-----------------------------------------------
        System.out.println("=============================");
        Zuo z1 = new Zuo(1);
        Zuo z2 = new Zuo(1);//两个自定义类型对象同值
        HashMap<Zuo, String>  test2 = new HashMap<>();
        test2.put(z1,"我是z1");
        System.out.println("2.HashMap存了z1对象为键，z2的值与z1一样，看z2的键值对是否存在"+test2.containsKey(z2));//存了z1，z2的值与z1一样，看z2是否存在
        //---------查看HashMap键相同的情况下重复添加情况-------------------------------------------------
        System.out.println("=============================");
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1000000,"我是1000000");
        map.put(2,"我是2");
        map.put(3,"我是3");
        map.put(4,"我是4");
        map.put(5,"我是5");
        map.put(6,"我是6");
        map.put(1000000,"我是1000000");

        System.out.println(map.containsKey(1));
        System.out.println(map.containsKey(10));

        System.out.println(map.get(4));//根据key得到值
        System.out.println(map.get(10));
        map.put(4,"他是4");//键存在，重复put将更新键值对的值
        System.out.println(map.get(4));

        map.remove(4);//移除这组键值对
        System.out.println("//3.为了检测HashMap,put的两次键相同的情况下是重复添加还是只更新value,重复添加相同的键，不同的值。再删除键对应键值对，看是否还有该键的键值对存在");
        System.out.println(map.get(4));
        //查看删除一个key为4的是否还存在4的键值对，

        //------HashSet----------------------------------------------------
        System.out.println("=============================");
        System.out.println("4.HashSet的使用");
        HashSet<String> set = new HashSet<>();
        set.add("abc");//添加
        System.out.println(set.contains("abc"));//是否存在
        set.remove("abc");//移除

        //哈希表、增、删、改、查，在使用时，O(1)
        System.out.println("=============================");
        System.out.println("5.比较两Integer对象是否相同");
        Integer a1 = 78;
        Integer a2 = 78;
        System.out.println("a1.equals(a2):"+a1.equals(a2));
        System.out.println("a1 == a2:"+(a1 == a2));

        System.out.println("=============================");
        HashMap<Node,String> node = new HashMap<>();
        System.out.println("两Node对象Node1=Node2,put两个键值对查看size");
        Node n1 = new Node(34);
        Node n2 = n1;
        node.put(n1,"我是n1");
        node.put(n2,"我是n2");
        System.out.println("size:"+node.size());

        System.out.println("=============================");
        System.out.println("有序表测试TreeMap");
        TreeMap<Integer, String> treemap = new TreeMap<>();
        treemap.put(1,"我是1");
        treemap.put(2,"我是2");
        treemap.put(3,"我是3");
        treemap.put(4,"我是4");
        treemap.put(5,"我是5");
        System.out.println(treemap.containsKey(4));
        System.out.println(treemap.containsKey(10));

        System.out.println(treemap.get(4));
        treemap.put(4,"我是4,4");
        System.out.println(treemap.get(4));

        treemap.remove(4);
        System.out.println(treemap.get(4));

        System.out.println("冷门用法");
        System.out.println(treemap.firstKey());
        System.out.println(treemap.lastKey());
        System.out.println(treemap.floorKey(4));
        System.out.println(treemap.ceilingKey(4));

    }
}
