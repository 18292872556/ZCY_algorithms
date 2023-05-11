package class01;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/01/03/21:29
 * @Description:用二分法找到数组（无序数组，相邻不等）内一个局部最小的，即只比左右两个数小即可。对于数组两端的数来说比相邻的一个小即可
 * 思路： 先看两端是不是，不是的话说明中间必然有一个局部最小，因为两段都局部最大，且向中间趋近于更小。
 */
public class Code06_BSAweaome {
    public static int getLessIndex(int[] arr){//返回找到的局部最小的索引
        if(arr == null || arr.length == 0){
            return -1;
        }
        if(arr.length == 1 || arr[0] < arr[1]){
            return 0;
        }
        if(arr[arr.length-1] < arr[arr.length-2]){
            return arr.length-1;
        }
        //二分法找
        int left = 1;
        int right = arr.length - 2;
        int mid = 0;
        while(left <= right){
            mid = left + ((right - left) >> 1);
            if(arr[mid] < arr[mid-1] && arr[mid] < arr[mid+1]){//此处应该不会越界，因为长度必然是3及以上（长度小于3的情况前面都处理了）。并且取了中间mid
                return mid;//找到了
            }else if(arr[mid] < arr[mid - 1]){//只比左一位小，则去右边找。就是假设是一个向局部最小递减的趋势，如果不是递减说明找到了局部最小
                left = mid + 1;
            }else{
                right = mid - 1;
            }
// else if(arr[mid] < arr[mid + 1]){//只比右一位小，去左边找
//                right = mid-1;
//            }else{//局部最大，左右都可以找因为，数组两侧是局大，两局大中间必然有一局小
//                left =mid + 1;
//            }
        }

        return -1;//没有就返回-1，除了空数组情况不可能没有局部最小。{无序数组，相邻不等）
    }

    //检查getLessIndex的返回结果是否正确
    public static boolean isRight(int[] arr, int index){
        if(arr.length <= 1){
            return true;//why?长度不足2，说明一定是局小？
        }
        if(index == 0){
            return arr[0] < arr[1];
        }
        if(index == arr.length-1){
            return arr[arr.length-1] < arr[arr.length-2];
        }
        return arr[index] < arr[index-1] && arr[index] < arr[index+1];

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
        int testTime = 100;
        int maxSize = 10;
        int maxValue = 200;
        boolean succeed = true;

        for(int i = 0; i < testTime; i++){
            int[] arr = generateRandomArray(maxSize, maxValue);
            if(!isRight(arr, getLessIndex(arr))){
                System.out.println("第"+i+"次出错");
                succeed = false;
                printArray(arr);
                System.out.println(" index:"+getLessIndex(arr));
            }
        }
        System.out.println(succeed?"nice!!!!!!":"falied");
    }
}

