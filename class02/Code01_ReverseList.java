package class02;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/05/11/19:18
 * @Description:用到的方法
 * 1.创建List集合，有索引，可通过索引访问，索引从0开始。按添加顺序排列
 * ArrayList<Node> list = new ArrayList<>();
 * list.add(head);添加元素
 * list.get(0).next;访问索引0的下一位
 * list.size();返回list的长度
 */
public class Code01_ReverseList {
    public static class Node {
        public int value;
        public Node next;
        public Node(int data){
            value = data;
        }
    }
    public static class DoubleNode {
        public int value;
        public DoubleNode last;
        public DoubleNode next;
        public DoubleNode(int data){
            value = data;
        }
    }
    public static Node reverseLinkedList(Node head){
        if(head == null){
            return null;
        }
        //思路:用三个指针，前两个新建链，第三个记录下一对要换倒序的结点位置
        Node pre = head;
        Node cur = head.next;
        Node after = cur;
        head.next = null;//记录完前两个结点位置后，头作为尾
        while(cur != null){//mid为空，说明pre来到最后一位，不用倒序
            after = cur.next;
            cur.next = pre;
            pre = cur;
            cur = after;
        }
        return pre;
    }
    //双链表反转
    public static DoubleNode reverseDoubleList(DoubleNode head){
        if(head == null){
            return null;
        }
        DoubleNode pre = head;
        DoubleNode cur = head.next;
        DoubleNode newHead = pre;//因为结束时pre和cur都null
        while(cur != null){//最后一次进入循环
            cur = pre.next;//最后一次进入循环为null
            pre.next = pre.last;
            pre.last = cur;

            newHead = pre;
            pre = cur;//最后一次为null
        }
        return newHead;
    }

    //相当于比较器，利用list的反转链表方式
    //检查单链表反转是否成功，具体方法：创建list<Node>存储链表，用索引访问方式反转链表。返回新的头结点
    public static Node testReverseLinkedList(Node head){
        if(null == head){
            return null;
        }
        //创建list
        ArrayList<Node> list = new ArrayList<>();
        while(head != null){
            list.add(head);
            head = head.next;
        }

        //通过索引访问链表结点并反转
        list.get(0).next = null;
        for(int i = 1; i < list.size(); i++){
            list.get(i).next = list.get(i-1);
        }
        return list.get(list.size()-1);
    }
    //相当于比较器
    //检查双链表反转是否成功，具体方法：创建list<Node>存储链表，用索引访问方式反转链表
    public static DoubleNode testReverseDoubleList(DoubleNode head){
        if(null == head){
            return null;
        }
        ArrayList<DoubleNode> list = new ArrayList<>();
        while(head != null){
            list.add(head);
            head = head.next;
        }
        for(int i = 0; i < list.size(); i++){
            DoubleNode cur = list.get(i).next;
            list.get(i).next = list.get(i).last;
            list.get(i).last = cur;
        }
        return list.get(list.size()-1);
    }
    //生成一个长度，值随机的单链表，len最大长度，value最大值
    public static Node generateRandomLinkedList(int len, int value){
        int size = (int)(Math.random()*(len+1));//因为方法本身产生的是[0,1)。取整型就是[0,1-1]
        if(0 == size){
            return null;
        }
        Node head = new Node((int)(Math.random()*(value+1)));
        Node pre = head;
        for(int i = 1; i < size; i++){//创建size-1个结点，因为head已经创建
            Node cur = new Node((int)(Math.random()*(value+1)));
            pre.next = cur;
            pre = pre.next;
        }
        pre.next = null;
        return head;
    }
    //生成一个长度，值随机的双链表，len最大长度，value最大值
    public static DoubleNode generateRandomDoubleList(int len, int value){
        int size = (int)(Math.random()*(len+1));
        if(0 == size){
            return null;
        }
        DoubleNode head = new DoubleNode((int)(Math.random()*(value+1)));
        DoubleNode pre = head;
        head.last = null;
        for(int i = 1; i < size; i++){
            DoubleNode cur = new DoubleNode((int)(Math.random()*(value)));
            pre.next = cur;
            cur.last = pre;
            pre = pre.next;
        }
        pre.next = null;
        return head;
    }
    //用一个ArrayList存储单链表的值，返回List
    public static List<Integer> getLinkedListOriginOrder(Node head){
        ArrayList<Integer> list = new ArrayList<>();
        while(head != null){
            list.add(head.value);
            head = head.next;
        }
        return list;
    }

    //用一个ArrayList存储双链表的值，返回list
    public static List<Integer> getDoubleListOriginOrder(DoubleNode head){
        ArrayList<Integer> dlist = new ArrayList<>();
        while(head != null){
            dlist.add(head.value);
            head = head.next;
        }
        return dlist;
    }

    //检查倒序是否成功，具体操作：倒序遍历list与链表正序比较，不同则说明链表倒序出错
    public static boolean checkLinkedListReverse(List<Integer> oringin, Node head){
        int N = oringin.size();
        for(int i = N-1; i >= 0; i--){//倒序遍历oringin
            if(! oringin.get(i).equals(head.value)){//Integer和int对比，自动装包？基本数据类型包装类和String类可以用值参数自动装箱
                return false;
            }
            head = head.next;
        }
        return true;
    }
    //检查倒序是否成功，具体操作：倒序遍历list与链表正序比较，正序遍历list与链表倒序比较，不同则说明链表倒序出错
    public static boolean checkDoubleListReverse(List<Integer> oringin, DoubleNode head){
        DoubleNode end = null;
        int N = oringin.size();
        for(int i = N-1; i >= 0; i--){
            if(oringin.get(i) != head.value){//Integer和int对比
                return false;
            }
            end = head;
            head = head.next;
        }
        //end在结点末尾
        for(int i = 0; i < N; i++){
            if(!oringin.get(i).equals(end.value)){
                return false;
            }
            end = end.last;
        }
        return true;
    }
    //free
    //打印单链表value
    public static void printLinkedList(Node head){
        if(head == null){
            System.out.println("空链表");
            return;
        }
        System.out.print("该单链表：");
        while(head != null){
            System.out.print(head.value+",");
            head = head.next;
        }
        System.out.println();
    }
    //打印双链表value
    public static void printDoubleList(DoubleNode head){
        if(head == null){
            System.out.println("空链表");
            return;
        }
        DoubleNode end = head;
        System.out.print("双链表正向： ");
        while(head != null){
            System.out.print(head.value+",");
            end = head;
            head = head.next;
        }
        System.out.println();
        System.out.print("双链表反向： ");
        while(end != null){
            System.out.print(end.value+",");
            end = end.last;
        }
        System.out.println();
    }

    public static void main(String[] args){
        int testTime = 10;//测试次数
        int len = 15;//链表最大长度
        int value = 100;//链表最大值
        for(int i = 0; i < testTime; i++){
            System.out.println("第"+(i+1)+"次测试");
            //1.生成单链，生成对应list，反转链表，通过遍历list检查反转的是否正确
            Node head1 = generateRandomLinkedList(len, value);
            List<Integer> list1 = getLinkedListOriginOrder(head1);

            System.out.println("1.单链表反转前");
            printLinkedList(head1);
            head1 = reverseLinkedList(head1);
            System.out.println("单链表反转后");
            printLinkedList(head1);

            if(!checkLinkedListReverse(list1,head1)){
                System.out.println("Oops1：单链表反转错误");
            }

            //2.生成单链，生成对应list,用系统list索引写法反转链表，通过遍历list反转的是否正确
            Node head2 = generateRandomLinkedList(len,value);
            List<Integer> list2 = getLinkedListOriginOrder(head2);

            System.out.println("2.单链表反转前");
            printLinkedList(head2);
            head2 = testReverseLinkedList(head2);
            System.out.println("单链表反转后");
            printLinkedList(head2);

            if(!checkLinkedListReverse(list2,head2)){
                System.out.println("Oops2：单链表list法反转错误");
            }
            //3.生成双链表，生成对应list,反转链表，通过遍历list检查反转的是否正确
            DoubleNode head3 = generateRandomDoubleList(len,value);
            List<Integer> list3 = getDoubleListOriginOrder(head3);

            System.out.println("3.双链表反转前");
            printDoubleList(head3);
            head3 = reverseDoubleList(head3);
            System.out.println("双链表反转后");
            printDoubleList(head3);

            if(!checkDoubleListReverse(list3,head3)){
                System.out.println("Oops3：双链表反转错误");
            }

            //4.生成双链表，生成对应list,用系统list索引写法反转链表，通过遍历list反转的是否正确
            DoubleNode head4 = generateRandomDoubleList(len,value);
            List<Integer> list4 = getDoubleListOriginOrder(head4);

            System.out.println("4.双链表反转前");
            printDoubleList(head4);
            head4 = testReverseDoubleList(head4);
            System.out.println("双链表反转后");
            printDoubleList(head4);

            if(!checkDoubleListReverse(list4,head4)){
                System.out.println("Oops4：双链表list法反转错误");
            }
            System.out.println();
        }
        System.out.println("test finish!");
    }
}
