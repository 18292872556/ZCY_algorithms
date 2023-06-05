package class02;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/05/12/15:45
 * @Description:两个栈实现一个队列
 */
public class Code06_TwoStacksImplementQueue {
    public static class TwoStackQueue{
        public Stack<Integer> stackPush;
        public Stack<Integer> stackPop;

        public TwoStackQueue(){
            stackPush = new Stack<Integer>();
            stackPop = new Stack<Integer>();
        }
        //核心，push栈必须一次倒完，pop栈为空的时候才能倒
        //为什么呢，因为两个栈组合成队列，就要保证入栈数的顺序不乱掉
        public void pushToPop(){
            if(!stackPop.empty()){
                return;
            }
            int size = stackPush.size();
            while(size > 0){
                stackPop.push(stackPush.pop());
                size--;
            }
        }
        public void add(int pushInt) {
            stackPush.push(pushInt);
            pushToPop();
        }
        public int poll(){
            if(stackPop.empty()){
                pushToPop();
            }
            return stackPop.pop();
        }
        public int peek(){
            if (stackPop.empty()) {
                pushToPop();
            }
            return stackPop.peek();
        }
        public static void main(String[] args){
            TwoStackQueue queue = new TwoStackQueue();
            queue.add(1);
            queue.add(2);
            queue.add(3);
            queue.add(4);
            System.out.println("入队列1,2,3,4。poll->"+queue.poll());
            System.out.println("poll->"+queue.poll());
            queue.add(5);
            queue.add(6);
            queue.add(7);
            System.out.println("入队列5,6,7。poll->"+queue.poll());

            queue.add(8);
            System.out.println("入队列8.poll->"+queue.poll());
            System.out.println("poll->"+queue.poll());
            System.out.println("poll->"+queue.poll());
            System.out.println("poll->"+queue.poll());

        }

    }
}
