package class03;
import static class03.Code01_MergrSort.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 雪雪子
 * @Date: 2023/06/29/12:07
 * @Description: 分区及快排
 */

public class Code06_PartitionAndQuickSort {
    public static void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    /**
     * 在arr[L……R]上，以arr[R]位置的数做划分，即X
     * 分区 <=X ,>X
     * 最后交换<=X,X,>X
     * 返回=X的位置
    */
    public static int partition(int[] arr, int L, int R){
        if(L > R){
            return -1;//返回一个不存在的坐标
        }
        if(L == R){
            return L;//返回自身，因为无需排序
        }
        int lessEqual = L-1;
        int index = L;
        while(index < R){//因为==R作为X
            if(arr[index] <= arr[R]){//纳入<=区
                swap(arr, index++, ++lessEqual);//<=区右扩一位，交换该index,index++

            }else{
                index++;//说明大于则++下一位

            }
        }
        //遍历结束后处理arr[R]，放到<=区的最右，即交换一个>区的
        //考虑特殊情况，如<=区没有或者，<=区全是走到R,即和自己交换
        swap(arr,R,++lessEqual);//<=区的右边界[左，lessEqual]
        return lessEqual;
    }

    //快排1.0  分区<=区，>区
    public static void quickSort1(int[] arr){
        if(null == arr || arr.length < 2){
            return;
        }
        process1(arr, 0, arr.length-1);
    }
    //递归分区
    public static void process1(int[] arr, int L, int R){
        if(L >= R){//这个分区无需排序
            return;
        }
        int M = partition(arr, L, R);//用分区分到一个中间数
        process1(arr, L, M-1);//排序左半边
        process1(arr, M+1, R);//再排序右半边
    }

    /**arr[L……R]玩荷兰国旗问题，以arr[R]做划分
     < arr[R] ==arr[R] > arr[R]
     返回=区的头尾*/
    public static int[] netherlandsFlag(int[] arr, int L, int R){
        if(L > R){
            return new int[]{-1,-1};//返回一个不存在的坐标
        }
        if(L == R){
            return new int[]{L,L};//返回两个值相同且无需排序
        }
        int less = L - 1;
        int more = R;
        int index = L;
        while(index < more){//遍历检查，不能跟>区撞上
            if(arr[index] < arr[R]){//<区
                swap(arr,index++,++less);//交换的一定是==index,所以直接交换后++
                //放入<区，因为此时，<区与index之间只有=区。相当于<区右扩，=区右移
            }else if(arr[index] > arr[R]){//>区
                swap(arr,index,--more);//放入>区，因为交换过来的没有检查index先不++
            }else{//=区
                index++;
            }
        }
        swap(arr, R, more);//more换前是>区最左侧，换后为=区的最右侧
        return new int[]{less + 1,more};//more不用-1因为X放在more区首位
    }
    //快排2.0  分区<区，=区，>区
    public static void quickSort2(int[] arr){
        if(null == arr || arr.length < 2){
            return;
        }
       process2(arr, 0, arr.length-1);
    }
    //递归分区
    public static void process2(int[] arr, int L, int R){
        if(L >= R){//递归出口//防止分区返回-1出错
            return;
        }
        int[] M = netherlandsFlag(arr, 0, R);//长度为2的数组，=区的左右坐标
        process2(arr, L, M[0]-1);
        process2(arr, M[1]+1, R);
    }
    //快排3.0与2.0的区别就是以一个arr[L……R]上的随机数作为划分
    public static void quickSort3(int[] arr){
        if(null == arr || arr.length < 2){
            return;
        }
        process3(arr, 0, arr.length-1);
    }
    //递归分区
    public static void process3(int[] arr, int L, int R){
        if(L >= R){
            return;
        }
        //arr[L……R]中选一个随机数作为划分，与arr[R]交换
        //复习math.random生成的随机数范围[0,1)小数
        int X = L + (int)(Math.random()*(R - L + 1));//L+[0,R-L]->[L,R]
        swap(arr,R,X);
        int[] M = netherlandsFlag(arr,L,R);
        process3(arr, L, M[0]-1);
        process3(arr, M[1]+1, R);
    }

    //for test============================================================================
//    public static int[] generateRandomArray(int maxSize, int maxValue){
//
//    }
    //Code01里写过了调用
    public static void main(String[] args){
        //初步测试，15行数组指正越界
//        int[] arr1 = generateRandomArray(10, 100);
//        int[] arr2 = copyArray(arr1);
//        int[] arr3 = copyArray(arr2);
////        quickSort1(arr1);
// //       quickSort2(arr2);
//        quickSort3(arr3);

        int testTime = 10;
        int maxSize = 10;
        int maxValue = 1000;
        for(int i = 0; i < testTime; i++){
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr2);

            quickSort1(arr1);
            quickSort2(arr2);
            quickSort3(arr3);
            if(!isEqual(arr1, arr2) && !isEqual(arr2, arr3)){
                System.out.println("Oops");
                printArray(arr1);
                printArray(arr2);
                printArray(arr3);
            }
        }
        System.out.println("finish!");
    }


}
