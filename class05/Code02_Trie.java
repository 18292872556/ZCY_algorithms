package class05;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 雪雪子
 * @Date: 2023/08/17/23:01
 * @Description: 用哈希表实现前缀树
 * 思路分析，数组中用结点后跟节点类型数组长度为26来描述
 * 哈希表用<Integer,Node>key表示path路径，如a路径b路径等可能有很多个结点，怎么表达后续结点呢
 * 用哈希表，每个结点的nexts建立一张表即可，路径到对应节点
 */
public class Code02_Trie {
     static class Trie{
        class Node{
            public int pass;
            public int end;
            public HashMap<Character, Node> nexts;
            public Node(){
                pass = 0;
                end = 0;
                nexts = new HashMap<Character, Node>();
            }
        }
        private Node root;
        public Trie(){
            root = new Node();
        }

        //插入字符串
        public void insert(String word){
            if(word == null){
                return;
            }
            Node node = root;
            node.pass++;
            char[] str = word.toCharArray();
            for(int i = 0; i < str.length; i++){
                if(!node.nexts.containsKey(str[i])){
                    node.nexts.put(str[i],new Node());
                }
                node.nexts.get(str[i]).pass++;
                node = node.nexts.get(str[i]);
            }
            node.end++;

        }
        //删除字符串一次
        public void erase(String word){
            if(countWordsEqualTo(word) > 0){
                char[] str = word.toCharArray();
                Node node = root;
                node.pass--;
                for(int i = 0; i < str.length; i++){
                    if(--node.nexts.get(str[i]).pass == 0){//断链
                        node.nexts = null;
                        break;
                    }else{
                        node = node.nexts.get(str[i]);//pass已经--
                    }
                }
                node.end--;
            }
        }
        //查找字符串是否存在，有几个
        public int countWordsEqualTo(String word){
            if(word == null){
                return 0;
            }
            char[] str = word.toCharArray();
            Node node = root;
            for(int i = 0; i < str.length; i++){
                if(!node.nexts.containsKey(str[i])){
                    return 0;
                }
                node = node.nexts.get(str[i]);
            }
            return node.end;
        }
        //查找以pre为开头的字符串有几个
        public int countWordsStartingWith(String pre){
            if(pre == null){
                return 0;
            }
            char[] str = pre.toCharArray();
            Node node = root;
            for(int i = 0; i < str.length; i++){
               if(!node.nexts.containsKey(str[i])){
                    return 0;
               }else{
                   node = node.nexts.get(str[i]);
               }
            }
            return node.pass;
        }

    }
    public static void main(String[] args){
        //前缀树===========================================================================
        System.out.println("哈希表前缀树===========================================================================");
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
}
