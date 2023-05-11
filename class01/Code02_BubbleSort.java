package class01;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/01/03/21:27
 * @Description:冒泡排序。头取值[0]，尾取值[length-1,1]
 * My思路：
 * 从头位置开始遍历，相邻两个值两两比较并将大的交换到后（升序），一个数（除数组头尾）要与前后相邻两位比较，共两次。
 * 每趟遍历到尾，则成功将该范围最大的交换到了尾。
 * 每趟尾部数有序后缩短尾部，尾取值[length-1,1]，最后一次尾部在1位置则结束
 *
 */
public class Code02_BubbleSort {
    //冒泡排序
    public static void bubbleSort(int[] arr){
        for(int i=arr.length-1; i> 0; i--){//该趟的尾部
            for(int j=0; j<i; j++){//该次遍历中相邻两两交换到尾部
                if(arr[j] > arr[j+1]){
                    swap(arr, j, j+1);
                }
            }
        }

    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    //---------------------------------------------------对数器----6个方法----------------------------------------------------
    //比较器
    //跟系统提供的排序比较，检测自己写的是否正确
    public static void comparator(int[] arr){
        Arrays.sort(arr);
    }


    //生成随机数组，长度随机，值随机
    //Math.random()返回的是[0,1)中等概率小数
    public static int[] generateRandomArray(int maxSize, int maxValue){
        int length = (int)(Math.random()*(maxSize+1));
        int[] arr = new int[length];
        for(int i=0; i<length; i++){
            arr[i] = (int)(Math.random()*(maxValue+1));
        }

        return arr;
    }

    //复制数组并返回
    public static int[] copyArray(int[] arr){
        int[] arr2 = new int[arr.length];
        for(int i=0; i<arr.length; i++){
            arr2[i] = arr[i];
        }
        return arr2;
    }

    //比较两数组是否相同
    public static boolean isEquals(int[] arr1, int[] arr2){
        if(arr1 == null && arr2 != null){
            return false;
        }else if(arr1 != null && arr2 == null){
            return false;
        }else if(arr1.length != arr2.length){
            return false;
        }else{
            for(int i=0; i<arr1.length;i++){
                if(arr1[i] != arr2[i]){
                    return false;
                }
            }
        }
        return true;
    }

    //打印数组
    public static void printArray(int[] arr){
        for(int i=0; i<arr.length; i++){
            System.out.print(arr[i]+",");
        }
        System.out.println();
    }

    //大样本检测
    //初始化变量-测试次数，数组最大长度，数字最大值，是否成功标志
    //展示一次排序前后
    public static void main(String[] args){

        int testTime = 100;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for(int i = 0; i < testTime; i++){
            int[] arr1 = generateRandomArray(maxSize,maxValue);
            int[] arr2 = copyArray(arr1);
//            System.out.println("第"+count+"次，排序前arr1==arr2："+isEquals(arr1, arr2));
           // printArray(arr1);
            bubbleSort(arr1);
            comparator(arr2);
//            System.out.println("第"+count+"次，排序后arr1==arr2："+isEquals(arr1, arr2));
            //printArray(arr1);
//            printArray(arr2);

            if(!isEquals(arr1, arr2)){//循环检测时，只要有一次交换后不等
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!!" : "Fucking fucked!");

        //生成随机数组，排序后打印前后。展示一次
        int[] arr1 = generateRandomArray(100,100);
        printArray(arr1);
        bubbleSort(arr1);
        printArray(arr1);

    }
}
