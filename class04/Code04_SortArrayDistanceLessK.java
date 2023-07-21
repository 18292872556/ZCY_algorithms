package class04;

import class04.Code02_Heap.*;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 雪雪子
 * @Date: 2023/06/29/13:16
 * @Description: 已知一个几乎有序数组，几乎有序指，如果把数组排好序的话，
 * 每个元素移动的距离一定不超过k,并且k相对于数组长度来说是比较小，请选择一个合适的排序策略，
 * 对这个数组进行排序
 * 思路：小根堆顶一定是全队最小，我们选择建立一个长度k的堆，调整为小根堆，位置0的数一定在这k+1个数的范围内可找到
 ？理解左的代码问题在于，生成随机数组时，交换的两位是（i,i+[0,k])为K个距离，k+1个数。
                    但是！排序时生成的堆初始化时取得是[0,k-1]为k-1个距离，k个数，按理来说是有问题的
 因为有可能当前k个距离内最小数没有进堆，就会出错。但是左代码跑的正确，原因在于！后续进数在弹数前，保证了弹数前堆长度为K+1个数，k个距离
 但我的思路是弹数进入的循环先弹还是先压无所谓，就定义开始堆就K+1个数好了
 */
public class Code04_SortArrayDistanceLessK {
    public static void sortedArrDistanceLessK(int[] arr, int k) {
        //判断k是否合法
        if(k == 0){//说明需要排序的数之间距离为0
            return;
        }
        //使用系统默认小根堆PriorityQueue，遍历0……k-1或0……length-1（后续数不够K-1个的情况）次，
        // 把数组中这部分数字填入小堆，K个
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int i = 0;
        for(; i < Math.min(k, arr.length); i++){
            heap.add(arr[i]);
        }
        //再循环分别两个指针遍历数组一个用来接收排好序的新数，一个用来往堆里加数，一步步往后挪，把后续数组数填入堆，
        //再把堆中最小数弹出填入数组。这个弹出填入顺序无所谓，因为新加的数是给后一位做准备，在k范围外
        //指针a在数组前从头接收堆弹出的最小值，指针b在堆后往堆中添加新数
        int a = 0;
        int b = i;
//        for(; b < arr.length; a++,b++){
//            heap.add(arr[b]);
//            arr[a] = heap.poll();
//        }
        while(b < arr.length){
            heap.add(arr[b++]);//填数必须在前，因为初始化堆个数为k，但k个距离数应该为k+1
            arr[a++] = heap.poll();
        }
        //如果数组填数完毕，堆还没有弹空，继续循环接收堆弹出的数即可，每次都是堆内最小
        while(!heap.isEmpty()){
            arr[a++] = heap.poll();
        }
    }

    // for test----------------------------------------------------------------
    public static void comparator(int[] arr, int k) {
        //比较器，调用系统排序,根本用不到K
        Arrays.sort(arr);
    }

    // for test
    //生成一个随机数组，且数组排序时每个数移位不超过K个距离
    public static int[] randomArrayNoMoveMoreK(int maxSize, int maxValue, int K) {
        //先生成一个随机数组，然后排序，然后随机交换但每个数距离不超过k，可以生成负数正数一半一半的数组
        int[] arr = new int[(int)(Math.random() * (maxSize + 1))];
        for(int i = 0; i < arr.length; i++){
            arr[i] = (int)(Math.random() * (maxValue + 1)) - (int)(Math.random() * maxValue);//[-maxValue + 1,maxValue]
        }
        // 先排个序
        Arrays.sort(arr);
        //定义swap数组，跟生成数组长度一样，swap[i] = true表示i位置已经交换过
        boolean[] swap = new boolean[arr.length];//boolean类型默认false
        //从i开始遍历，随机一个k以内的坐标，如果两个数都没有交换过，则开始交换，否则处理下一位
        // 然后开始随意交换，但是保证每个数距离不超过K
        // swap[i] == true, 表示i位置已经参与过交换
        // swap[i] == false, 表示i位置没有参与过交换
        for(int i = 0; i < arr.length; i++){
            int j = Math.min(i + (int)(Math.random() * (K + 1)), arr.length - 1);//i + [0,k]若后续k超出数组尾，则取尾
            if(swap[i] == false && swap[j] == false){//找到的随机交换位j若交换过则i不换了,也有可能i == j
                swap[i] = true;
                swap[j] = true;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if(arr == null){
            return null;
        }
        int[] copy = new int[arr.length];
        for(int i = 0; i < arr.length; i++){
            copy[i] = arr[i];
        }
        return copy;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
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

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ，");
        }
        System.out.println();
    }

    // for test

    public static void main(String[] args) {
//       int[] arr = new int[]{-4, -4, -4, -5, -4, -5, -6, -8, -4, -1, 3, -1, -1, -3, 6, 0, 5, 9, 5, -1, 1, 0, 0, 8, 1};
//       int[] arrs = copyArray(arr);
//       int[] arrss = copyArray(arr);
//       sortedArrDistanceLessK(arrs, 9);
//       comparator(arrss,9);
//       if(!isEqual(arrs, arrss)){
//           System.out.println("oops");
//           printArray(arr);
//           printArray(arrs);
//           printArray(arrss);
//
//       }else{
//           System.out.println("finish");
//       }


        //多次测试，随机生成size以内的K,三个相同数组,后两个一个用来走堆排序，一个走普通排序，
        //如果两个排序后不同，输出原数组，和这两个数组和k并break
        System.out.println("test begin");
        int testTime = 500000;
        int maxSize = 30;
        int maxValue = 10;
        boolean flag = true;
        for(int i = 0; i < testTime; i++){
            int k = (int)(Math.random() * maxSize) + 1;
            int[] arr1 = randomArrayNoMoveMoreK(maxSize, maxValue, k);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            sortedArrDistanceLessK(arr2, k);
            comparator(arr3, k);
            if(!isEqual(arr2, arr3)){
                System.out.println("第"+i+"次 Oops"+", k = "+k);
                printArray(arr1);
                printArray(arr2);
                printArray(arr3);
                break;
            }

        }
        System.out.println("finish");

    }

}
