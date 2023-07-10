package class03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 雪雪子
 * @Date: 2023/07/5/2:20
 * @Description: 双向链表的随机快速排序
 * 和课上讲的数组的经典快速排序在算法上没有区别
 * 但是coding需要更小心，区别在于每次分区排三个区时，传入没有右边界值。
 * why?因为第一次进行process是在整个的区间范围上L到R，所以走到null停止，
 * 值得注意的是，partition在排序三个区时，会先把遍历到的节点孤立取出，再分别链接三个区符合的节点
 * 所以返回后的三个区一定是断开的，这也就表明，下一次在其中一个分区上分割区间时只需要确定头即可，
 * 因为新的L到R处一定为null
 * ---------要复习的语法---------------------------------------------------
 * 1.比较器 public static class NodeComp implements Comparator<Node>
 * 2.StingBuilder
 */
public class Code08_DoubleLinkedListQuickSort {
    //静态类结点
    public static class Node {
        public int value;
        public Node last;
        public Node next;

        public Node(int v) {
            value = v;
        }
    }

    //双向链表快排，调用process返回.h
    public static Node quickSort(Node h){
        if (null == h) {
            return null;
        }
        if (h.next == null) {
            return h;//一个数不用排序
        }

        Node tail = h;
        int size = 1;
        while (tail.next != null) {
            size++;
            tail = tail.next;
        }
        return process(h, tail, size).h;
    }

    public static class HeadTail {//类头尾
        public Node h;
        public Node t;

        public HeadTail(Node head, Node tail) {
            h = head;
            t = tail;
        }
    }


    /**
     * @Description: 递归，随机选一个节点来划分区域，接收partition方法返回的=区左右端点，然后再排序左右区间
     * L……R是一个双向链表的头和尾
     * L的last指针指向null,R的next指针指向null,也就是说L的左边没有，R的右边也没节点
     * 就是一个正常的双向链表，一共有N的节点
     * 将这一段用随机快排的方式排好序
     * 返回排好序之后的双向链表的头和尾（HeadTail)
     * @Param: L 双链表需要排序的区间左端点
     * @Param R 双链表需要排序的区间右端点
     * @Param N 区间长度
     * @return: class03.Code08_DoubleLinkedListQuickSort.HeadTail
     */
    public static HeadTail process(Node L, Node R, int N) {//传入头尾和长度，长度作用，生成范围随机数
        //怎么判断区间有效
        if(L == null){
            return null;
        }
        if (L == R) {//说明单节点或无结点的区间
            return new HeadTail(L, R);
        }
        //整型randomIndex 取一个0到N的随机数做下标
        //Node型randomNode从L开始根据随机数遍历到随机位置，即双链表从头按0算坐标
        //把随机节点从原来的环境里分离出来
        //比如 a(L) -> b -> c -> d(R) 如果randomNode = c,那么调整之
        //a(L) -> b -> d(R),c会被挖出来，randomNode = c,让randomNode成为一个孤立节点
        int randomIndex = (int) (Math.random() * N);//[0,N-1]链表长度N
        Node randomNode = L;
        //根据生成的[0,N]上的随机数，定位对应节点，链表也默认坐标从0开始
        while (randomIndex-- > 0) {//索引0的话不用进去，本身就在L//从坐标位置到0，链表从坐标0的L到随机位置
            randomNode = randomNode.next;
        }

        //挖出该随机节点,特殊情况，该节点在头尾明明，没有last或next
        if (randomNode == L || randomNode == R) {
            if (randomNode == L) {
                L = randomNode.next;
                L.last = null;

            } else {
                randomNode.last.next = null;
            }
        } else {
            randomNode.last.next = randomNode.next;
            randomNode.next.last = randomNode.last;
        }
        randomNode.next = null;
        randomNode.last = null;

        //调用partition，接收返回的三个区分类节点
        // 不太懂必要性，感觉返回HeadTail类型也可以，毕竟L,R固定。而且不传区间长？
        //说明，HeadTail不太好，因为只能返回一组头尾，即=区头尾，无法确定<区和>区头尾，还得遍历找到
        //而且排序时也不方便，还得三个区全排完再链接到一起，更麻烦，没必要
        Info info = partition(L, randomNode);//分区，只根据传入的左端点和随机结点做划分
        HeadTail lht = process(info.lh, info.lt, info.ls);//给左侧<区排序,返回左侧区间排序完成后的头尾
        HeadTail rht = process(info.rh, info.rt, info.rs);//给右侧>区排序

        //链接排好序的三段//如果为null呢，取next或last会出错
        //= 区肯定不可能为空，起码有划分值randomNode那一位
        if (lht != null) {//左侧<区不为空，链接=区
            lht.t.next = info.eh;
            info.eh.last = lht.t;
        }
        if (rht != null) {
            info.et.next = rht.h;
            rht.h.last = info.et;
        }
        Node h = lht != null ? lht.h : info.eh;//确定最终链接的头尾，若某侧为空，用=区的
        Node t = rht != null ? rht.t : info.et;

        return new HeadTail(h, t);
    }

    public static class Info {//类
        public Node lh;//<区头
        public Node lt;//<区尾
        public int ls;//<区长度
        public Node rh;//>区
        public Node rt;
        public int rs;
        public Node eh;//=区
        public Node et;
        //为啥不给=区规定长度，因为排过无需再次排序

        public Info(Node lH, Node lT, int lS, Node rH, Node rT, int rS, Node eH, Node eT) {
            lh = lH;
            lt = lT;
            ls = lS;
            rh = rH;
            rt = rT;
            rs = rS;
            eh = eH;
            et = eT;
        }

    }

    //会先把遍历到的节点孤立取出，再分别链接三个区符合的节点
    public static Info partition(Node L, Node pivot) {
        //Info info = new Info(null, null, 0, null, null, 0, null, null);
        //判断区间不存在或为空情况
        if (L == null) {
            return null;
        }
        //判断单节点情况，因为pivot是孤立节点，还要排序
        Node lh = null;
        Node lt = null;
        int ls = 0;
        Node rh = null;
        Node rt = null;
        int rs = 0;
        Node eh = pivot;
        Node et = pivot;

        Node l = L;//起别名对象遍历，当前遍历到的节点需要先独立
        while (l != null) {
            Node tmp = l.next;//记录未遍历的新头
            l.last = null;
            l.next = null;
            if (l.value < pivot.value) {
                ls++;//确定进<区
                if (lh == null) {
                    lh = l;//作为<区头,尾
                    lt = l;
                } else {//<区尾部跟上
                    lt.next = l;
                    l.last = lt;
                    lt = l;
                }
            } else if (l.value > pivot.value) {
                rs++;//确定进>区
                if (rh == null) {
                    rh = l;//作为<区头,尾
                    rt = l;
                } else {//<区尾部跟上
                    rt.next = l;
                    l.last = rt;
                    rt = l;
                }
            } else {//=区无需统计长度，因为不需要再次排序，就不需要确定总长度生成随机数
                et.next = l;
                l.last = et;
                et = l;
            }
            l = tmp;
        }
        //排序完返回
        return new Info(lh, lt, ls, rh, rt, rs, eh, et);
    }

    //for test ----------------对数器------------------------------------------------------------

    public static class NodeComp implements Comparator<Node>{
        @Override
        public int compare(Node o1, Node o2) {
            return o1.value - o2.value;
        }
    }
    //比较器,排序双向链表，用ArrayList做辅助
    //新ArrayList<Node> arr,遍历head将值填入ArrayList，因为可以自动排序，排序后再根据顺序重新建立一个双向链表
    //可以用到两种ArrayList的方法
    //list.sort(new NodeComp());//排序list内存储的节点
    //Collections.sort(list, new NodeComp());
    public static Node sort(Node head){
        ArrayList<Node> list = new ArrayList<>();
        while(head != null){
            list.add(head);
            head = head.next;
        }
        list.sort(new NodeComp());//排序list内存储的节点
        // Collections.sort(list, new NodeComp());
        //根据排序好的链接新的双向链表
        Node pre = head;//此时head == null
        for(int i = 0; i < list.size(); i++){
            if(head == null){
                head = list.get(i);
                pre = head;
                head.last = null;
                continue;
            }
            Node cur = list.get(i);
            pre.next = cur;
            cur.last = pre;
            cur.next = null;
            pre = cur;//来到新的节点
        }
        return head;
    }
    //生成长度随机，值随机的双向链表，原方法是写了一个Node类型的数组
    public static Node generateRandomDList(int n, int v) {
        if(n == 0){
            return null;
        }
        int size = (int)(Math.random()*(n + 1));//[0,n]
        Node head = null;
        Node pre = head;
        for(int i = 0; i < size; i++){
            int value = (int)(Math.random()*(v + 1));
            if(head == null){
                head = new Node(value);
                pre = head;
                continue;//直接走下一层循环，避免两个节点重复
            }
            Node cur = new Node(value);
            cur.last = pre;
            pre.next = cur;
            pre = cur;
        }
        return head;
    }
    //拷贝双向链表
    public static Node cloneDList(Node head) {
        if(head == null){
            return null;
        }
        Node head2 = new Node(head.value);
        Node pre = head2;
        Node tmp = head.next;//遍历原链表
        while(tmp != null){
            Node cur = new Node(tmp.value);//创建新的节点
            pre.next = cur;
            cur.last = pre;
            pre = cur;
            tmp = tmp.next;
        }
        return head2;
    }

    //比较两个双链表是否相同，用StringBuilder,和 String 类不同的是，StringBuilder 类的对象能够被多次的修改，并且不产生新的未使用对象。
    //把链表的节点值存入，先存正序遍历，再存倒序遍历。然后toString转换为String类型返回
    public static String DListToString(Node head){
        Node cur = head;//正序遍历
        Node end = null;//倒序遍历
        StringBuilder str = new StringBuilder();
        while(cur != null){
            str.append(cur.value+" ");
            end = cur;//不断更新遍历到的尾
            cur = cur.next;
        }
        str.append(" | ");
        while(end != null){
            str.append(end.value+" ");
            end = end.last;
        }
        return str.toString();
    }
    //判断两个双向链表是否相同,用String的equals方法
    public static boolean equal(Node h1, Node h2) {
        return DListToString(h1).equals(DListToString(h2));
    }

    public static void printDList(Node head){
        while(head != null){
            System.out.print(head.value+" ");
            head = head.next;
        }
        System.out.println();
    }
    public static void main(String[] args) {

        //测试比较运算符同时比较三个
//            if(true && false || true){//&&高于||
//                System.out.println("1");
//            }
//            if(false || false || true){
//                System.out.println("2");
//            }
//            if(true || false || false){
//                System.out.println("3");
//            }
//            if(false || true || false){
//                System.out.println("4");
//            }// 1 2 3 4

        //单次测试
        // Node head1 = generateRandomDList(10, 100);

//        Node head1 = new Node(6);
//        Node pre = head1;
//        for(int i = 5; i > 0; i--){
//            Node cur = new Node(i);
//            pre.next = cur;
//            cur.last = pre;
//            pre = cur;
//        }
//        Node head2 = cloneDList(head1);
//        head1 = quickSort(head1);
//        head2 = sort(head2);
//        if (!equal(head1, head2)) {
//            System.out.println("Oops");
//        }
//        System.out.println("finish");


        //为啥这次的链表长度随机得在generateRandomDList方法外生成
        int testTime = 100;
        int n = 10;
        int v =100;
        for(int i = 0; i < testTime; i++){
            Node head1 = generateRandomDList(n, v);
            Node head2 = cloneDList(head1);
            Node head3 = cloneDList(head1);
            Node sort1 = quickSort(head1);
            Node sort2 = sort(head2);
            if(!equal(sort1, sort2)){
                System.out.println("Oops");
                System.out.printf("原双链表为：");
                printDList(head3);
                System.out.printf("sort1:");
                printDList(sort1);
                System.out.printf("sort2:");
                printDList(sort2);
            }
        }
        System.out.println("finish");


    }

}
