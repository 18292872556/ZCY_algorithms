package class01;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/01/03/21:26
 * @Description:选择排序。遍历数组，每趟记录该趟最小值，然后填到该趟区间的最前，然后数组头后缩一位作为下一次遍历的区间 
 * My思路：
 * 从头开始遍历到尾，每趟记录该趟读到的最小值位置，遍历完后与头位置交换，保证头位置值始终为本趟最小。
 * 头位置取值在[0,length-2]，尾部始终[length-1]即，
 * 第1趟排序位置0，
 * 第2趟排序位置1，
 * ……
 * 第length-1趟排序位置length-2，结束。即[length-2,length-1]中倒数第二个位置更小，剩余的一个倒数第一个位置就不用排
 *
 */
public class Code01_SelectionSort {
    //选择排序
    public static void selectionSort(int[] arr){
        for(int i=0; i<arr.length; i++){
            int minIndex = i;
            for(int j=i+1; j<arr.length; j++){
                minIndex = arr[j] <arr[minIndex]? j: minIndex;
            }
            swap(arr, i, minIndex);
        }
    }

    //交换数组中两个元素
    public static void swap(int[] arr, int i, int j){
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
        int times = 100;//检测次数
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
            //printArray(arr1);
            selectionSort(arr1);
            comparator(arr2);
//            System.out.println("第"+count+"次，排序后arr1==arr2："+isEquals(arr1, arr2));
//            printArray(arr1);
//            printArray(arr2);

            if(!isEquals(arr1, arr2)){//循环检测时，只要有一次交换后不等
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        //生成随机数组，排序后打印前后。展示一次
        int[] arr1 = generateRandomArray(100,100);
        printArray(arr1);
        selectionSort(arr1);
        printArray(arr1);

    }
}
