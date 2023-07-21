package class04;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 雪雪子
 * @Date: 2023/07/11/13:15
 * @Description: 调整数组为堆，重点方法：heapInsert新加的在尾部向上看调整heapify根部为新数，下沉
 * 默认写大根堆,系统堆PriorityQueue,参数填比较器
 */
public class Code02_Heap {

    public static class MyMaxHeap{
        private int[] heap;
        private final int limit;
        private int heapSize;

        public MyMaxHeap(int limit){
            this.limit = limit;
            heap = new int[limit];
            heapSize = 0;
        }

        public boolean isEmpty(){
            return heapSize == 0;
        }
        public boolean isFull(){
            return heapSize == limit;
        }
        //尾部填入数，调用heapInsert,向上看交换调整
        public void push(int value){
            if(heapSize == limit){
                throw new RuntimeException("my heap is full");
            }
            heap[heapSize] = value;
            heapInsert(heap, heapSize++);
        }
        //返回根部，因为根最大。再交换尾部数到根位置，Size--，再调整为堆
        public int pop(){
            if(heapSize == 0){
                throw new RuntimeException("my heap is empty");
            }
            int ans = heap[0];//记录最大值
            swap(heap, 0, --heapSize);//交换根和数组尾，并删除尾一
            heapify(heap, 0);//调整堆的结构，根不符合
            return ans;
        }
        //不符合堆结构的数在尾部，向上看交换,调整为堆
        private void heapInsert(int[] arr, int index){
            while(arr[(index - 1) / 2] < arr[index]){//父小于该子,交换该子上移
                swap(heap, (index - 1) / 2, index);
                index = (index - 1) / 2;
            }
        }
        //不符合堆结构的数在根部，向下看交换，调整为堆
        //为啥要传入size不能直接用吗，
        private void heapify(int[] arr, int index){
            //检查是否存在左孩子
            int left = 2 * index + 1;
            while(left < heapSize){//左孩子存在
                //存左右孩子中更大的那个
                int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
                //存父子中更大的那个
                largest = arr[index] > arr[largest] ? index : largest;
                if(largest == index){
                    break;//说明此时的父节点已经满足大根堆，无需向下调整了
                }
                swap(arr, index, largest);
                index = largest;
                left = 2 * index + 1;
            }
        }
        private void swap(int[] arr, int i, int j){
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

    }
    //for test,普通类含普通数组，push加数，pop弹出最大值----------------------------------------------------------------
    public static class RightMaxHeap{//对比类，不是大根堆，只是每次pop返回最大值
        private int[] arr;
        private final int limit;
        private int size;
        public RightMaxHeap(int limit){
            this.limit = limit;
            arr = new int[limit];
            size = 0;
        }
        public boolean isEmpty(){
            return size == 0;
        }
        public boolean isFull(){
            return size == limit;
        }
        public void push(int value){
            if(size == limit){
                throw new RuntimeException(" heap is full");
            }
            arr[size++] = value;
        }
        public int pop(){
            if(size == 0){
                throw new RuntimeException("heap is empty");
            }
            //遍历一遍数组，返回最大的，把原最大的位置用尾数填，size--
            int max = 0;
            for(int i = 1; i < size; i++){
                if(arr[i] > arr[max]){
                    max = i;
                }
            }
            int ans = arr[max];
            arr[max] = arr[--size];
            return ans;
        }
        //Integer降序比较器
        public static class MyComparator implements Comparator<Integer> {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        }
        public static void main(String[] args){
            //测试思路：1.一个系统堆，输入后，依次弹出看顺序

            PriorityQueue<Integer> heap = new PriorityQueue<>(new MyComparator());//不填参数就是正序排
            heap.add(5);
            heap.add(4);
            heap.add(3);
            heap.add(2);
            heap.add(1);
            System.out.println(heap.peek());
            heap.add(8);
            heap.add(19);
            heap.add(4);
            heap.add(34);
            System.out.println(heap.peek());
            while(!heap.isEmpty()){
                System.out.print(heap.poll()+",");
            }


            //测试思路
            // 2.MyMaxHeap和RightMaxHeap比较，判断是否同时空或满，不同则输出错误
            // 若空加数，满减数。否则，其余时候0.5的概率加数，0.5的概率减数
            int testTime = 10;//测试循环次数
            int limit = 10;//堆的最大长度
            int value= 100;//堆中数的最大值
            for(int i = 0; i < testTime; i++){
                int curLimit = (int)(Math.random()*limit) + 1;//此次循环，堆的容量,外部加1防止空堆
                MyMaxHeap my = new MyMaxHeap(curLimit);
                RightMaxHeap test = new RightMaxHeap(curLimit);
                int curOptimes = (int)(Math.random() * limit);//对堆的操作次数
                for(int j = 0; j < curOptimes; j++){//对堆的操作次数
                    if(my.isEmpty() != test.isEmpty()){
                        System.out.println("Oops");
                    }
                    if(my.isFull() != test.isFull()){
                        System.out.println("Oops");
                    }
                    if(my.isEmpty()){//默认同时空或满
                        int curValue = (int)(Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    }else if(my.isFull()){
                        if(my.pop() != test.pop()){
                            System.out.println("Oops");
                        }
                    }else{//加数减数任选一个
                        if(Math.random() > 0.5){
                            int curValue = (int)(Math.random() * value);
                            my.push(curValue);
                            test.push(curValue);
                        }else{
                            if(my.pop() != test.pop()){
                                System.out.println("Oops");
                            }
                        }
                    }

                }
//                while(!my.isEmpty()){
//                    System.out.print(my.pop()+",");
//                }
//                System.out.println();
            }
            System.out.println("finish");
        }

    }

}
