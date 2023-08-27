package class05;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 雪雪子
 * @Date: 2023/08/19/16:41
 * @Description: 基数排序
 * 原本的思路：给数组中数字排序的原理是，从个位数字开始比较，建立10个桶，下标为0~9,
 * 按原本的顺序，入队列的桶，个位为0的进0桶，个位为9的进9桶。
 * 入桶后出通，先进的先出（保留个位相同，原本的相对顺序）此时完成个位的排序
 * 依次类推，排序十位，百位，直到最高位。
 * ✔ 但是注意，实现时并不走这样逻辑，而是优化，简化逻辑
 * 我们用前缀和数组来简化入桶出桶的过程
 * 例如一个无序数组[14,27,8,31,51，0]按个位排序的话，需要建立10个队列分别入队列出队列
 * 建他的桶数组长度为10[1,2,0,0,1, 0,0,1,1,0]即i位含义是个位为i的数有几个
 * 建立他的前缀和数组  [1,2,2,2,3， 3,3,4,5,5]即i位指的是前0~i位数的和，即<=i的有几个
 * 1）我们从右侧开始入桶，个位为1，入1桶，
 * 2）并且已经得知，<= 1的数有2个，1桶中数据下标范围为0~1,又因为排在最后，
 * 是从右开始遍历的，所以是1桶中最后出桶的。即应该在1位
 * 循环1）2）依次类推不同的数和循环外不同的位数
 *
 *
 * Math.pow(base,exponent) 函数返回基数（base）的指数（exponent）次幂，即 base^exponent。
 */
public class Code04_RadixSort {
    // only for no-negative value
    public static void radixSort(int[] arr) {
        //判非法数组，调用自己的重载方法，4参数那个
        if(arr == null || arr.length < 2){
            return;
        }
        radixSort(arr, 0, arr.length - 1, maxbits(arr));
    }

    //找数组中最高位是几位
    public static int maxbits(int[] arr) {
        //先找出最大值max，然后判断位数res
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < arr.length; i++){
            max = Math.max(max, arr[i]);
        }
        int res = 1;//代表位数
        max /= 10;
        while(max != 0){
            max /= 10;
            res++;
        }
        return res;
    }

    // arr[L..R]排序  ,  最大值的十进制位数digit
    public static void radixSort(int[] arr, int L, int R, int digit) {
        //定义final变量radix为10，代表10进制
        // 有多少个数准备多少个辅助空间help数组
        //以位数digit循环，每次排序一个位数，由低到高
        //count数组10个空间，因为是十进制，存个数前缀和

       // 有多少位就进出几次
            // 10个空间
            // count[0] 当前位(d位)是0的数字有多少个
            // count[1] 当前位(d位)是(0和1)的数字有多少个
            // count[2] 当前位(d位)是(0、1和2)的数字有多少个
            // count[i] 当前位(d位)是(0~i)的数字有多少个
             // count[0..9]
            //4个内循环，
        final int radix = 10;
        int[] help = new int[R - L + 1];
        for(int d = 1; d <= digit; d++){//取不同的位数
            //分别是填充count数组，j取当前需要的位，count统计i桶几个数
            //前缀和
            //覆盖原help数组，从右往左确定位置，count--
            //help刷写回arr数组
            int[] count = new int[radix];
            for(int i = L; i <= R; i++){
                int j = getDigit(arr[i], d);
                count[j]++;//取数组个位（d位）统计个数
            }
            for(int i = 1; i < radix; i++){
                count[i] += count[i - 1];
            }
            //核心,以当前位作为比较标准进行排序
            //例如：arr从右往左第一位的d位为2，要进2桶，count[2]代表<=2的有几个，假设有3个
            //那当前的arr[i]一定在help的2位上
            for(int i = R; i >= L; i--){//从右往左遍历arr数组
                int j = getDigit(arr[i], d);
                help[count[j] - 1] = arr[i];
                count[j]--;
            }
            for(int i = 0, j = L; i < help.length; i++,j++){
                arr[j] = help[i];
            }
        }

    }

    //返回一个数的d位数字，Math.pow(x,y)==x^y
    //比如取十位，d=2，(x/10)%10取到10位数
    public static int getDigit(int x, int d) {
        return (int)((x/(Math.pow(10, d - 1))) % 10);//为啥要取整呢本来就是余数诶
    }

    // ------------------------------for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
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
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
//        int[] arr = new int[]{27,81,68,22,91,3,84,70,39,25};
//        radixSort(arr);
//        printArray(arr);

        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        radixSort(arr);
        printArray(arr);

    }


}
