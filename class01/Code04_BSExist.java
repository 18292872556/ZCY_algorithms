package class01;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/01/03/21:28
 * @Description:用二分判断一个数num是否存在有序数组sortedArr中
 * My思路：
 *  规定一个区间[left,rigth]，每次找到中间位置的数mid与num比较，
 *  若mid>num,则num在mid的左半边，缩小寻找区间为[left,mid)
 *  若mid<num,则num在mid的右半边，缩小寻找区间为（mid,rigth]
 *  直到找到并返回所在坐标，或一直到寻找区间没有数，left>right的情况停止，返回找不到
 */

public class Code04_BSExist {
    //用二分判断一个数num是否存在有序数组sortedArr中
    public static boolean exist(int sortedArr[], int num){
        //记得加一个数组判空指针或空数组
        if(sortedArr == null || sortedArr.length == 0) {
            //这里只能用||：如果前者为真则不判断后者，因为结果必真。
            //如果用 |：则前后都会照常判断，若为空指针则后者不能用长度则报错
            return false;
        }

        int left = 0;
        int right = sortedArr.length-1;
        int mid = 0;
        while(left <= right){//==的时候可能是刚好最后一次找到了
            mid = left+((right-left) >> 1);
            if(sortedArr[mid] == num){
                return true;//找到了
            }else if(sortedArr[mid] < num){
                left = mid+1;
            }else{
                right = mid-1;
            }
        }
        return false;//越过了依然找不到
    }

    //比较器，遍历检查num是否存在
    public static boolean test(int[] sortedArr, int num){
        for(int i = 0; i < sortedArr.length; i++){
            if( num == sortedArr[i]){
                return true;
            }
        }

        return false;
    }

    //生成随机数组
    public static int[] generateRandomArray(int maxSize, int maxValue){
        int[] arr = new int[(int)(Math.random()*(maxSize+1))];
        for(int i = 0; i < arr.length; i++){
            arr[i] = (int)(Math.random()*(maxValue+1));
        }
        return arr;
    }

    //打印数组
    public static void printArray(int[] arr){
        for(int i=0; i<arr.length; i++){
            System.out.print(arr[i]+",");
        }
        System.out.println();
    }

    public static void main(String[] args){
        int testTime = 100;
        int maxSize = 13;
        int maxValue = 700;
        boolean succeed = true;

        for(int i = 0; i < testTime; i++){
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int num = (int)(Math.random()*(maxValue+1));
             if(exist(arr, num) != test(arr, num)){
                 succeed = false;
                 System.out.println("exist第"+(i+1)+"次判断错误");
                 System.out.println("esist:"+exist(arr, num)+",test:"+test(arr, num));
                 System.out.println("num为"+num+",arr数组为：");
                 printArray(arr);
             }
        }
        System.out.println(succeed?"Nice!!！棒棒":"failed");

    }

}
