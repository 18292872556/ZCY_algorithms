package class01;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/01/03/21:28
 * @Description:用二分找到有序数组中所有>=value中最小的值，即所有>=value的数中找到最左侧的那个。
 * 核心思路：满足>=value时，记录mid。最后一次记录的mid就是所有>=value的数中最小的
 * ？A：因为当满足>=value时，记录当前 坐标。然后会去左边寻找更小的，所以最后一次找到的一定是最小的。
 * 思路：[left,right]每次取中间值mid判断是否 >= value，是则在左边范围找，不是则在右边范围找
 * 当left>right遍历结束
 * ■什么时候找到？
 * 如果不存在，例如[1,1,2,2]中找>=4min，遍历结束也没有满足>=4，不记录索引Index
 * 如果存在，例如[1,1,2,3,4,4]中找>=4min，遍历过程会最后一个满足>=4的则为最小的，因为大了往左找小了往右找有一个诬陷接近的趋势
 */
public class Code05_BSNearLeft {
    public static int nearestIndex(int[] arr, int value){

        int L = 0;
        int R = arr.length-1;
        int index = -1;//标记找到的位置
        while(L <= R){
            int mid = L+((R-L)>>1);
            if(arr[mid] >= value){
                index = mid;
                R = mid-1;
            }else{
                L = mid+1;
            }
        }

        return index;//返回-1下标说明没找到，没有
    }

    //for test
    //比较器，从头遍历第一个满足>=value的
    public static int isRight(int[] arr, int value){
        for(int i= 0; i<arr.length; i++){
            if(arr[i] >= value){
                return i;
            }
        }
        return -1;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue){
        int[] arr = new int[(int)(Math.random()*(maxSize+1))];
        for(int i= 0; i < arr.length; i++){
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
        int testTime = 100;
        int maxSize = 20;
        int maxValue = 100;
        boolean succeed = true;

        for(int i = 0; i < testTime; i++){
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            if(isRight(arr,50) != nearestIndex(arr,50)){
                System.out.println("出错的数组为：");
                printArray(arr);
                System.out.println("isRight返回值："+isRight(arr,50)+"。nearesrIndex返回值："+nearestIndex(arr,50));
                succeed = false;
                break;
            }
        }
        System.out.println(succeed?"nice!":"failed");
    }
}
