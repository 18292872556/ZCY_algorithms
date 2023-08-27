package class05;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 雪雪子
 * @Date: 2023/08/16/15:36
 * @Description: 前缀树建立思路：
 * 设置结点Node类，int属性pass记录被通过几次，到达也算通过一次。int属性end作为几个字符串的结尾，在这个节点结束一次加1
 * Node[] 属性nests,数组大小26，因为假设都是小写字母最多26个。nests[i]等于i字母的路，如0为a，1为b，2为c
 * 如果.nests[i]==null没有赋值就没有路不存在
 */
public class Code01_Trie {

    //该前缀树的路用数组实现
    static class Trie{//前缀树类
         class Node{//结点类
             public int pass;
             public int end;
             public Node[] nexts;

             public Node(){
                 pass = 0;
                 end = 0;
                 nexts = new Node[26];//假设全是小写字母，最多26个
             }

         }

         private Node root;//起始结点

        public Trie(){//前缀树的构造方法，一个起始结点
            root  = new Node();
        }

        //前缀树操作-----------------------
        public  void insert(String word){//插入数组中字符串时，插入前缀树节点
            //字符串转字符数组
            //更新每个经过的节点的pass，最后更新一次end
            //用引用node遍历，从root开始
            //path代表路径坐标，下一次走哪个坐标结点
            //循环走，i不超过字符数组大小，检查str[i]是什么字母-'a‘生成ascii码，即node.nexts【path]为下一位坐标
            //如果下一条路不存在，新建pass++，存在则pass++，node走向这个下一位
            if(word == null){
                return;
            }
            char[] str = word.toCharArray();
            Node node = root;//用来遍历
            node.pass++;//起始节点开始走有经过，pass++
            int path = 0;
            for(int i = 0; i < str.length; i++){//遍历当前字符串转化的字符数组
                path = str[i] - 'a';
                if(node.nexts[path] == null){//若该条路不存在
                    node.nexts[path] = new Node();//新建这条路
                }
                node.nexts[path].pass++;//走到这个节点了相当于经过一次，所以pass++
                node = node.nexts[path];
            }
            node.end++;//走完最后一个节点

        }
        //擦除字符串，难点，只删一次应该是
        public void erase(String word){
            //删除前首先检查是否存在调search，不存在不进来,若存在沿途pass--，尾部end--
            //考虑特殊情况，一般删到中间pass为0说明不用继续往下删了，因为下面结点的pass删下去一定都为1删为0
            //这时，让上一个节点的nexts指向null即可
            if(countWordsEqualTo(word) > 0){
                char[] str = word.toCharArray();
                Node node = root;
                int path = 0;
                for(int i = 0; i < str.length; i++){
                    path = str[i] - 'a';
//                    if(node.nexts[path] == null){//应该不会有这种可能，除非要查的字符串不存在。但不存在就不会进来
//                        new RuntimeException("出错");
//                    }
                    if(--node.pass == 0){//说明直接删的断链了，无需往下走了
                        node.nexts = null;//其后数组全部都为空
                        break;
                    }else{
                        node = node.nexts[path];
                    }
                }
                node.end--;
            }
        }
        //查找word在前缀树中是否存在,返回个数
        //走到结尾看end是不是不为0,否则即使走到了也只是前缀而已
        public int countWordsEqualTo(String word){
            if(word == null){
                return 0;
            }
            char[] str = word.toCharArray();
            Node node = root;
            int path = 0;
            for(int i= 0; i < str.length; i++){
                path = str[i] - 'a';
                if(node.nexts[path] == null){
                    return 0;
                }
                node = node.nexts[path];
            }
            //结束时，刚走完字符串路径
            return node.end;
        }
        //查找以什么字符串开头的字符串有几个。例如”abd“”abc“都以”ab“开头
        //只需要走到这个前缀开头的结尾（说明已经通向这个节点经过几次就几个这个开头），返回该节点的pass即可
        public int countWordsStartingWith(String pre){
            if(pre == null){
                return 0;
            }
            char[] str = pre.toCharArray();
            Node node = root;
            int path = 0;
            for(int i = 0; i < str.length; i++){
                path = str[i] - 'a';
                if(node.nexts[path] == null){
                    return 0;
                }
                node = node.nexts[path];
            }
            return node.pass;
        }
    }

    public static void main(String[] args) {
        //用哈希表实现功能1找字符串加入了几次================================================================
        //因为课上讲找字符串加入几次这个功能哈希表也能做，复习哈希表
        //HashSet不保证存入顺序，排序按这个值的hashcode排序，不可重复指hashcode不同
        //可以存储多种不同的数据类型，就一个哈希表里。集合元素可以是null
        HashSet set = new HashSet();
        set.add("abc");
        set.add("abc");
        System.out.println(set);//很明显这个无法重复加入

        //HashMap可以实现“找字符串加入了几次”这个功能1，应该是<String,Integer>值代表出现几次，最小是1
        //每次加入键值对前用map.contains(key）判断是否包含相同的key
        //再map.get(key)拿到old value ,map.replace(key,new value)替换新值
        HashMap<String, Integer> map = new HashMap<>();
//        map.put("abc", 1);
//        map.put("abc", 2);//这样输入也会直接刷新原键值对的值，无法起到计数功能，疑问那怎么完成功能1？
        //自己写一个put方法，每次put前先判断存不存在，再加入改value
        myPut(map, "abc");
        myPut(map, "abc");
        myPut(map, "abc");
        myPut(map,"abd");
        myPut(map,"kst");//完成！
        System.out.println(map);

        //前缀树===========================================================================
        System.out.println("前缀树===========================================================================");
        Trie trie1 = new Trie();

        trie1.insert("abc");
        trie1.insert("abc");
        trie1.insert("abcde");
        trie1.insert("abd");
        trie1.insert("ksty");
        trie1.insert("bac");
        trie1.insert("abd");
        trie1.insert("abda");
        trie1.insert("abdb");
        trie1.insert(null);
        System.out.println("abc存在几个："+trie1.countWordsEqualTo("abc"));
        trie1.countWordsEqualTo(null);
        System.out.println("abdb存在几个："+trie1.countWordsEqualTo("abdb"));
        System.out.println("以abc为前缀的有几个："+trie1.countWordsStartingWith("abc"));
        System.out.println("以ab为前缀的有几个："+trie1.countWordsStartingWith("ab"));
        trie1.countWordsStartingWith(null);
        System.out.println("擦除abc：");
        trie1.erase("abc");
        trie1.erase(null);
        System.out.println("abc几个："+trie1.countWordsEqualTo("abc"));




    }
    private static void myPut(HashMap<String, Integer> map, String str){
        if(map.containsKey(str)){
            int value = map.get(str);
            map.put(str, value + 1);
        }else{
            map.put(str, 1);
        }
    }
}
