package class02;

import static sun.swing.MenuItemLayoutHelper.max;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/05/12/15:46
 * @Description:递归求数组最大值
 */
public class Code08_GetMax {
    public static int getMax(int[] arr){
        return process(arr,0,arr.length-1);//返回最大值坐标
    }

    public static int process(int[] arr, int L, int R){
        if(null == arr || 0 == arr.length){
            return -1;//没有最大值
        }
        if(L == R){//区间只有一个数即为最大值，其余大小在return处重复比较
            return L;
        }
        int mid = L+((R-L)>>2);

        int left = process(arr,L,mid);
        int right = process(arr,mid+1,R);
        return arr[left] > arr[right]?left:right;
    }

    //生成长度随机值随机的数组
    public static int[] generateRandomArray(int length, int value){//
        int[] arr = new int[(int)(Math.random()*(length+1))];
        for(int i = 0; i < arr.length; i++){
            arr[i] = (int)(Math.random()*(value+1));
        }
        return arr;
    }
    public static void printArray(int[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.printf(arr[i]+",");
        }
        System.out.println();
    }
    public static void main(String[] args){
        int testTime = 20;//测试次数
        int length = 10;//数组长度最大值
        int value = 1000;//数组值最大值
        for(int i = 0; i < testTime; i++){
            int arr[] = generateRandomArray(length, value);
            System.out.print("第"+(i+1)+"次：");
            printArray(arr);
            System.out.println("maxIndex:"+getMax(arr));
        }
        System.out.println("test finish");
    }
}
