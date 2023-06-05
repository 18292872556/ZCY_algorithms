package class02;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/05/12/15:44
 * @Description:
 */
public class Code03_DoubleEndsQueueToStackAndQueue {
    public static class DNode<T> {
        public T value;
        public DNode<T> last;
        public DNode<T> next;

        public DNode(T data) {
            value = data;
        }
    }

    public static class DoubleEndsQueue<T> {//用双链表实现队列和栈的类
        public DNode<T> head;
        public DNode<T> tail;

        //头插
        public void addFromHead(T value) {
            DNode<T> cur = new DNode<T>(value);
            if (head == null) {
                head = cur;
                tail = cur;

            } else {
                cur.next = head;
                head.last = cur;
                head = cur;
            }
        }

        //尾插
        public void addFromBottom(T value) {
            DNode<T> cur = new DNode<T>(value);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                cur.last = tail;
                tail.next = cur;
                tail = cur;
            }
        }

        //头删，返回删除的值，考虑特殊情况即头尾结点的变化
        public T popFromHead() {
            if (null == head) {
                System.out.println("空链表没得删");
                return null;//虽然类型不固定但也可以用null返回
            }
            DNode<T> cur = head;//用来标记原头
            if (head == tail) {//只剩一个结点
                head = null;
                tail = null;
            } else {
                head = head.next;
                cur.next = null;

            }
            return cur.value;
        }

        //尾删
        public T popFromBottom() {
            if (null == head) {
                System.out.println("空链表不可删");
                return null;
            }
            DNode<T> cur = tail;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                tail = tail.last;
                cur.last = null;
            }
            return cur.value;
        }

        public boolean isEmpty() {
            return head == null;
        }

    }

    public static class MyStack<T> {//用双链表做栈
        private DoubleEndsQueue<T> queue;

        public MyStack() {
            queue = new DoubleEndsQueue<T>();
        }

        //进栈，相当于头插
        public void push(T value) {
            queue.addFromHead(value);
        }

        //出栈，相当于头删
        public T pop() {
            return queue.popFromHead();
        }

        //判断栈是否空
        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    public static class MyQueue<T> {
        private DoubleEndsQueue<T> queue;

        private MyQueue() {
            queue = new DoubleEndsQueue<T>();
        }

        //入队列,相当于尾插
        public void push(T value) {
            queue.addFromBottom(value);
        }

        //出队列，相当于头删
        public T poll() {
            return queue.popFromHead();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    public static boolean isEquals(Integer o1, Integer o2){
        if(o1 == null && o2 != null){
            return false;
        }
        if(o1 != null && o2 == null){
            return false;
        }
        if(o1 == null && o2 == null){
            return true;
        }
        return o1.equals(o2);
    }
    //for test
    public static void main(String[] args) {
        //测试思路
        //双层循环，外层循环测试次数,内层循环入栈/出栈（入队/出队）次数
        int testTime = 100000;//测试次数
        int oneTestDataNum = 100;//值操作次数
        int value = 10000;//最大值
        for(int i = 0; i < testTime; i++){
            MyStack<Integer> myStack = new MyStack<>();
            Stack<Integer> stack = new Stack<>();
            MyQueue<Integer> myQueue = new MyQueue<>();
            Queue<Integer> queue = new LinkedList<>();
            for(int j = 0; j < oneTestDataNum; j++){
                int nums = (int)(Math.random()*value);//此次内循环要给栈操作的随机数
                if(stack.isEmpty()){//如果栈空，则不能出栈只能进栈
                    stack.push(nums);
                    myStack.push(nums);
                }else{//出栈进栈都可，为保持平衡一半一半
                    if(Math.random() < 0.5){
                        stack.push(nums);
                        myStack.push(nums);
                    }else{//出栈，比较出栈数是否相同
                        if(!isEquals(stack.pop(),myStack.pop())){
                            System.out.println("oops! stack");
                        }
                    }
                }
                int numq = (int)(Math.random()*value);//此次内循环要给栈操作的随机数
                if(queue.isEmpty()){//如果栈空，则不能出栈只能进栈
                    queue.offer(numq);
                    myQueue.push(numq);
                }else{//出栈进栈都可，为保持平衡一半一半
                    if(Math.random() < 0.5){
                        queue.offer(numq);
                        myQueue.push(numq);
                    }else{//出栈，比较出栈数是否相同
                        if(!isEquals(queue.poll(),myQueue.poll())){
                            System.out.println("oops! queue");
                        }
                    }
                }

            }
        }
        System.out.println("finish！！");


    }

}
