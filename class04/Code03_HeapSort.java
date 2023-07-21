package class04;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 雪雪子
 * @Date: 2023/06/29/13:16
 * @Description:
 */
public class Code03_HeapSort {
    public static void heapSort(int[] arr){
        //调heapInsert上升，O(N*logN)
//        for(int i = 0; i < arr.length; i++){
//            heapInsert(arr, i);//保证每个数都是她子树的大根堆
//        }

        //调heapify下沉，O(N)，下沉的条件是子树都为大根堆，只有根需要调整
        for(int i = arr.length - 1; i >= 0; i--){
            heapify(arr, i, arr.length);
        }
        //已经调整为大根堆，说明根位置一定是当前最大，根据这个特性，
        //交换头尾，且调整区间去除最后一位。此时，最大值在尾部，下次调整排序不管这一位，
        //再次调整为大根堆后，再交换新根头尾（每个新根都比旧根少最后一位），此时的尾是整个数组中倒数第二大且位置也在倒数第二
        int heapSize = arr.length;//定义每次新堆长度
        swap(arr, 0, --heapSize);//交换头尾，且区间去尾数
        while(heapSize > 1){
            heapify(arr, 0, heapSize);//调整根符合大根堆
            swap(arr, 0, --heapSize);//把当前最大值放到末尾
        }
    }
    //一般index是尾部数下标,上升
    public static void heapInsert(int[] arr, int index){
        while(arr[(index - 1) / 2 ] < arr[index]){//为什么这里不怕数组下标小于0？因为index最多从1变到0，然后两侧都为0跳出循环
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }
    //这个需要size因为是下沉防止越界，一般index是0
    public static void heapify(int[] arr, int index, int heapSize){
        int left = 2 * index + 1 ;

        while(left < heapSize){
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            largest = arr[index] > arr[largest] ? index : largest;
            if(largest == index){
                break;
            }
            swap(arr, index, largest);
            index = largest;
            left = 2 * index +1;
        }
    }
    public static void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    //for test----------------------------------------------------------
    public static void comparator(int[] arr){
        Arrays.sort(arr);
    }
    public static int[] copyArray(int[] arr){
        int[] copy = new int[arr.length];
        for(int i = 0; i < arr.length; i++){
            copy[i] = arr[i];
        }
        return copy;
    }
    public static boolean isEqual(int[] arr1, int [] arr2){
        if(arr1 == null && arr2 == null){
            return true;
        }
        if(arr1 != null && arr2 == null || arr1 == null && arr2 != null){
            return false;
        }
        if(arr1.length != arr2.length){
            return false;
        }
        for(int i = 0; i < arr1.length; i++){
            if(arr1[i] != arr2[i]){
                return false;
            }
        }
        return true;
    }
    public static int[] randomArray(int maxSize, int maxValue){
        int size = (int)(Math.random() * maxSize) + 1;//免得为0[1,maxSize]
        int[] arr = new int[size];

        for(int i = 0; i < arr.length; i++){
            arr[i] = (int)(Math.random() * (maxValue + 1));//[0,maxValue]
        }
        return arr;
    }
    public static void printArray(int[] arr){
        for(int i : arr){
            System.out.printf(arr + ",");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        //测试思路，1.使用系统的堆，PriorityQueue默认为小根堆，添加数，然后全部弹出观察次序
        //2.两相同数组，一个用comparator排序，一个用heapSort排序，不同则false
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.add(6);
        heap.add(8);
        heap.add(0);
        heap.add(2);
        heap.add(9);
        heap.add(1);
        System.out.println(heap.peek());
        while(!heap.isEmpty()){
            System.out.print(heap.poll()+",");
        }
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 10000;
        for(int i = 0; i < testTime; i++){
            int[] arr1 = randomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            heapSort(arr1);
            comparator(arr2);
            if(!isEqual(arr1, arr2)){
                System.out.println("Oops");
            }
        }
        System.out.println("finish");
    }
}
