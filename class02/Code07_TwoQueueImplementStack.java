package class02;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/05/12/15:45
 * @Description:两个队列组成一个栈
 */
public class Code07_TwoQueueImplementStack<T> {
    public static class TwoQueueStack<T>{
        public Queue<T> queue;
        public Queue<T> help;
        public TwoQueueStack(){
            queue = new LinkedList<T>();
            help = new LinkedList<T>();
        }
        public void push(T value){
            queue.offer(value);
        }
        public T pop(){
            while(queue.size() > 1){
                help.offer(queue.poll());
            }
            Queue<T> tmp = help;
            help = queue;
            queue = tmp;
            return help.poll();
        }
        public T peek(){
            while(queue.size() > 1){
                help.offer(queue.poll());
            }
            return queue.peek();
        }
        public boolean isEmpty(){
            return queue.isEmpty();
        }
    }

    public static void main(String[] args){
        System.out.println("test begin");
        TwoQueueStack<Integer> myStack = new TwoQueueStack<>();
        Stack<Integer> test = new Stack<>();
        int testTime = 100;//测试次数
        int max = 10000;//入队列的最大值
        for (int i = 0; i < testTime; i++) {
            if (myStack.isEmpty()) {
                if (!test.empty()) {
                    System.out.println("Oops,两栈一空一不空");
                }
                int num1 = (int) (Math.random() * (max + 1));
                test.push(num1);
                myStack.push(num1);
            }
            if(Math.random() > 0.75){//入两栈
                int num2 = (int)(Math.random()*(max+1));
                test.push(num2);
                myStack.push(num2);
            }else if(Math.random() > 0.5){//判断两栈顶是否同
                if(!test.peek().equals(myStack.peek())){//比较Integer对象
                    //此处注意不==和equals，因为return的是Integer对象类型
                    System.out.println("Oops,两栈顶不同"+test.peek()+","+myStack.peek());
                }
            }else if(Math.random() > 0.25){//判断出栈是不是同
                if(!test.pop().equals(myStack.pop())){//比较Integer对象用equals,因为对于包装类是比较内容
                    System.out.println("Oops,两出栈的值不同");
                }
            }else{//判断是否都为空，或都不为+
                if(test.empty() != myStack.isEmpty()){//比较boolean基本类型
                    System.out.println("Oops，两栈一空，一不空");
                }
            }

        }
        System.out.println("test finish!");
    }
}
