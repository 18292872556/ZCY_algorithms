package class05;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 雪雪子
 * @Date: 2023/08/19/16:41
 * @Description:计数排序，属于通排的思想，对数据有要求
 *  * 员工的年龄在0~200之间存在数组里，准备一个长度201的help数组，i位存放年龄为i的人的个数
 *  * 怎么再排序原数组，0岁有几个就覆盖原数组几位为0，以此覆盖排序
 */
public class Code03_CountSort {
    //only for 0~200
    public static void countSort(int[] arr){
        //先找到最大的数，确定bucket数组长度；写入桶；从桶出来覆盖排序
        if(arr == null || arr.length < 2){
            return;
        }
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < arr.length; i++){
            max = Math.max(max, arr[i]);
        }
        int[] bucket = new int[max + 1];
        for(int i = 0; i < arr.length; i++){
            bucket[arr[i]]++;
        }
        //覆盖原数组
        //本来想的是，arr.length因为只有这么多数需要操作
        //但是进去赋值以后，显然两个数组的遍历坐标不一样，同一个bucket坐标可能对应好几个不同的arr数组坐标赋值。
        int j = 0;
        for(int i = 0; i < bucket.length; i++){
            while(bucket[i]-- > 0){
                arr[j++] = i;
            }
        }
    }
    //for test------------------------
    //系统排序
    public static void comparator(int[] arr){
        Arrays.sort(arr);
    }
    //生成一个长度值都随机的数组，参数为最大值
    public static int[] generateRandomArray(int maxSize, int maxValue){
        int[] arr = new int[(int)(Math.random() * (maxSize + 1))];//0-maxSize
        for(int i = 0; i < arr.length; i++){
            arr[i] = (int)(Math.random() * (maxValue + 1));
        }
        return arr;
    }
    //拷贝数组
    public static int[] copyArray(int[] arr){
        int[] copy = new int[arr.length];
        for(int i = 0; i < arr.length; i++){
            copy[i] = arr[i];
        }
        return copy;
    }
    public static boolean isEqual(int[] arr1, int[] arr2){
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

    public static void printArray(int[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.printf(arr[i] + ",");
        }
        System.out.println();
    }
    public static void main(String[] args){
        //测试两个排序，比较排序后是否相同
        int testTime = 10000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succed = true;
        for(int i = 0; i < testTime; i++){
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            countSort(arr1);
            comparator(arr2);
            if(!isEqual(arr1, arr2)){
                succed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succed == true ? "Nice!" : "fuck");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        countSort(arr);
        printArray(arr);
    }
}
