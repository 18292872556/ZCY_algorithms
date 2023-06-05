package class02;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/05/12/15:44
 * @Description:用数组实现队列
 */
public class Code04_RingArray {
    public static class MyQueue{
        public int[] arr;
        int pushi;//队尾进数
        int polli;//队头出数
        int size;//队列长度
        final int limit;//数组长度即队列最大值

        public MyQueue(int limit) {
            arr = new int[limit];
            int pushi = 0;
            int polli = 0;
            int size = 0;
            this.limit = limit;
        }

        public void push(int value){
            if(size == limit){
                throw new RuntimeException("队列满了，不可入");
            }
            arr[pushi] = value;
            size++;
            pushi = nextIndex(pushi+1);
        }

        public int pop(){
            if(size == 0){
                throw new RuntimeException("队列空了，不可出");
            }
            int poll = arr[polli];
            size--;
            polli = nextIndex(polli+1);
            return poll;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int nextIndex(int i) {//判断坐标是否合法
            return i > limit - 1 ? 0 : i + 1;
        }
        public void printRing(){
            //从队头打印到队尾
            int count = size;
            int ii = polli;
            while(count > 0){
                System.out.print(arr[ii]+",");
                ii = nextIndex(ii+1);
                count--;
            }
            System.out.println( );
        }
    }
    public static void main(String[] args){
        //测试能否正常出入栈

        MyQueue queue = new MyQueue(20);
        queue.push(1);
        queue.push(2);
        queue.push(3);

        System.out.print("入队列1,2,3.my queue:");
        queue.printRing();

        System.out.println("pop->"+queue.pop());

        queue.push(4);
        queue.push(5);
        System.out.print("入队列4,5.my queue:");
        queue.printRing();

        System.out.println("pop->"+queue.pop());
        System.out.println("pop->"+queue.pop());
        System.out.println("pop->"+queue.pop());

        queue.push(3456);
        queue.push(87);
        System.out.print("入队列3456,87.my queue:");
        queue.printRing();

        System.out.println("pop->"+queue.pop());

        queue.push(88);
        System.out.print("入队列88.my queue:");
        queue.printRing();

    }

}
