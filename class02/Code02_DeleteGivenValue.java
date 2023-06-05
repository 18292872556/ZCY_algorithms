package class02;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/05/12/15:42
 * @Description: 删除链表指定值结点
 */
public class Code02_DeleteGivenValue {
    public static class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public static Node removeValue(Node head, int num){
        //先来到第一个不需要删的结点位置
        while(head != null && head.value == num){
            head = head.next;
        }
        //走到这里head可能为null也可能不等于num
        Node pre = head;
        Node cur = head;
        while(cur != null){//pre不可能是要删除的结点，循环1不可能是，循环n也不可能，一直删cur,pre不移动。cur不用删，pre才移动
            cur = pre.next;//cur可能null
            if(cur != null && cur.value == num){//第一次进来cur肯定不进这个
                pre.next = cur.next;
            }else{
                pre = pre.next;
            }

        }
        return head;
    }

    //free
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
    public static void main(String[] args) {
        //测试一下，随机生成的话不一定有的删，所以这里自己大概测一下
        //那老师都没写测试部分
        //1.1.1.2.1.2.1.1.2.2.1
        Node head1 = new Node(1);
        Node cur = head1;
        cur.next = new Node(1);
        cur = cur.next;
        cur.next = new Node(1);
        cur = cur.next;
        cur.next = new Node(2);
        cur = cur.next;
        cur.next = new Node(1);
        cur = cur.next;
        cur.next = new Node(2);
        cur = cur.next;
        cur.next = new Node(1);
        cur = cur.next;
        cur.next = new Node(1);
        cur = cur.next;
        cur.next = new Node(2);
        cur = cur.next;
        cur.next = new Node(2);
        cur = cur.next;
        cur.next = new Node(1);
        cur = cur.next;


        printLinkedList(head1);
        head1 = removeValue(head1, 1);
        printLinkedList(head1);

        //11111
        Node head2 = new Node(1);
        cur = head2;
        cur.next = new Node(1);
        cur = cur.next;
        cur.next = new Node(1);
        cur = cur.next;
        cur.next = new Node(1);
        cur = cur.next;
        cur.next = new Node(1);

        printLinkedList(head2);
        head2 = removeValue(head2, 1);
        printLinkedList(head2);

        //221111
        Node head3 = new Node(2);
        cur = head3;
        cur.next = new Node(2);
        cur = cur.next;
        cur.next = new Node(1);
        cur = cur.next;
        cur.next = new Node(1);
        cur = cur.next;
        cur.next = new Node(1);
        cur = cur.next;
        cur.next = new Node(1);

        printLinkedList(head3);
        head3 = removeValue(head3, 1);
        printLinkedList(head3);

        //2341
        Node head4 = new Node(2);
        cur = head1;
        cur.next = new Node(3);
        cur = cur.next;
        cur.next = new Node(4);
        cur = cur.next;
        cur.next = new Node(1);

        printLinkedList(head4);
        head4 = removeValue(head4, 1);
        printLinkedList(head4);
        //1
        Node head5 = new Node(1);
        printLinkedList(head5);
        head5 = removeValue(head5, 1);
        printLinkedList(head5);


    }

}
