package class01;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/01/03/21:29
 * @Description:用二分找到有序数组中所有<=value中最大值，即所有<=value的数中找到最右侧的那个
 * 核心思路：每次二分mid的值满足<=value则记录，然后去右侧找，若满足再记录，则最后一次一定找到的是满足<=value中最大的值。
 * 若>value则在左边找满足条件的。
 * 思路：二分查找，如果mid>value，在左侧区间找。如果mid<=value，在右侧区间找,并记录index=mid。最后一次记录的index则为最大的满足条件的
 */
public class Code05_BSNearRight {
    public static int nearestIndex(int[] arr, int value){
        int index = -1;
        int L = 0;
        int R = arr.length-1;
        while(L <= R){
            int mid = L + ((R - L) >> 1);
            if(arr[mid] <= value){
                index = mid;
                L = mid + 1;
            }else{
                R = mid - 1;
            }
        }
        return index;//返回-1下标说明没找到，没有
    }

    //for test
    //从后往前遍历，第一个<=value的就是
    public static int isRight(int[] arr, int value){
        for(int i = arr.length-1; i >= 0; i--){
            if(arr[i] <= value){
                return i;
            }
        }
        return -1;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue){
        int[] arr = new int[(int)(Math.random()*(maxSize+1))];
        for(int i = 0; i < arr.length; i++){
            arr[i] = (int)(Math.random()*(maxValue+1));
        }
        return arr;
    }

    public static void printArray(int[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i]+",");
        }
        System.out.println();
    }

    public static void main(String[] args){
        int testTime = 120;
        int maxSize = 20;
        int maxValue = 100;
        boolean succeed = true;

        for(int i = 0; i < testTime; i++){
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            if(isRight(arr,50) != nearestIndex(arr,50)){
                System.out.println("出错数组为:");
                printArray(arr);
                System.out.println("isRight返回值："+isRight(arr,50)+"。nearesrIndex返回值："+nearestIndex(arr,50));
                succeed = false;
                break;
            }
        }
        System.out.println(succeed?"nice!!!!!":"failed");
    }


}
