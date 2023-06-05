package class02;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/05/12/15:44
 * @Description:
 */
public class Code05_GetMinStack {


    public static class MyStack1 {//最小栈与数据栈同高，最小栈每次只填当前高度最小的值
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        public MyStack1(){
            stackData = new Stack<Integer>();
            stackMin = new Stack<Integer>();
        }


        public void push(int newNum) {
            //!!注意：此处要考虑是否存在<=情况入最小栈。
            // 存在==情况，因为共同出栈的条件是两栈顶==时最小栈才出栈
            //如果最小栈只填小于栈顶的数的话，
            if (stackData.empty() || newNum < stackMin.peek()) {
                stackMin.push(newNum);
            } else {
                stackMin.push(stackMin.peek());
            }
            stackData.push(newNum);
        }

        public int pop() {
            if (stackData.empty()) {
                throw new RuntimeException("stack is empty");
            }
            stackMin.pop();
            return stackData.pop();
        }

        public int getMin() {
            return stackMin.peek();
        }

    }

    public static class MyStack2 {//新数比当前栈中最小值小时，才进入最小栈。只有最小栈顶与数据栈顶要出栈的数相同时，最小栈才一起出栈
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;
        public MyStack2(){
            stackData = new Stack<Integer>();
            stackMin = new Stack<Integer>();
        }

        public void push(int newNum) {
            //!!注意：此处要考虑是否存在<=情况入最小栈。[4,3,8,9,1,1]
            // 存在==情况，因为共同出栈的条件是两栈顶==时最小栈才出栈
            //<如果最小栈只填小于栈顶的数的话，[4,3,1]出栈时4位的1没有对应一起出的。
            // 不算错，但不太严谨，因为最好是代表了最小值的那个数和最小栈顶一起出
            //<=小于等于入栈[4,3,1,1]出栈时4位的1和2位的1一起出栈
            if (stackData.empty() || newNum < stackMin.peek()) {// < 和 <= 都可以
                stackMin.push(newNum);
            }
            stackData.push(newNum);
        }

        public int pop() {
            if (stackData.empty()) {
                throw new RuntimeException("stack is empty");
            }
            if (stackData.peek() == stackMin.peek()) {
                stackMin.pop();
            }
            return stackData.pop();
        }

        public int getMin() {
            return stackMin.peek();
        }

    }

    public static void main(String[] args) {
        MyStack1 stack1 = new MyStack1();
        stack1.push(34);
        stack1.push(23);
        stack1.push(4);
        stack1.push(3);
        stack1.push(10);
        System.out.println("入栈，34,23,4,3,10.最小值为"+stack1.getMin());
        System.out.println("pop->"+stack1.pop());
        System.out.println("pop->"+stack1.pop());
        System.out.println("最小值为"+stack1.getMin());

        stack1.push(1);
        stack1.push(0);
        System.out.println("入栈1,0.最小值为"+stack1.getMin());

        System.out.println("pop->"+stack1.pop());
        System.out.println("最小值为"+stack1.getMin());

        System.out.println("pop->"+stack1.pop());
        System.out.println("最小值为"+stack1.getMin());

        System.out.println("pop->"+stack1.pop());
        System.out.println("最小值为"+stack1.getMin());
        System.out.println("================");



        MyStack2 stack2 = new MyStack2();
        stack1.push(34);
        stack1.push(23);
        stack1.push(4);
        stack1.push(3);
        stack1.push(10);
        System.out.println("入栈34,23,4,3,10.最小值为"+stack1.getMin());
        System.out.println("pop->"+stack1.pop());
        System.out.println("pop->"+stack1.pop());
        System.out.println("最小值为"+stack1.getMin());
        stack1.push(1);
        stack1.push(0);
        System.out.println("入栈1,0.最小值为"+stack1.getMin());

        System.out.println("pop->"+stack1.pop());
        System.out.println("最小值为"+stack1.getMin());

        System.out.println("pop->"+stack1.pop());
        System.out.println("最小值为"+stack1.getMin());

        System.out.println("pop->"+stack1.pop());
        System.out.println("最小值为"+stack1.getMin());





    }
}
