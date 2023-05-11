package class01;
import java.util.Arrays;
/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/01/03/21:30
 * @Description: 测试错误的对数器，灵异的未解决的bug
 */
public class Test {

        //插入排序
        public static void insertionSort(int[] arr){
            if(arr == null || arr.length < 2){
                return;
            }
            //把握新值的下标
            for(int i = 1; i < arr.length; i++){//有序区间后的新值下标
                //巧思：此处，当j--来到j==0的位置时，应该停下。所以将j>0放在前，且用逻辑运算符&&而不是位运算符&，因为逻辑运算符左边若false，右边不会计算
                //就不存在j==0，却要访问arr[j-1]的越界情况
                for(int j = i-1; j >= 0 && arr[j] > arr[j+1]; j--){//如果新值比前一位大且不越界就一直交换
                    swap(arr,j,j+1);//一直符合条件就一直换
                }
            }

            //把握有序区间的下标
//        for(int i = 0; i < arr.length; i++){
//            for(int j = i+1; (j < arr.length) && (arr[j-1] > arr[j]); j++){
//                swap(arr, j-1, j);
//            }
//        }
        }

        public static void swap(int[]arr, int i, int j){
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
            int[] arr = new int[length];//[0,1)->(int)[0,maxSize+1)->[0,maxSize]
            int i = 0;
            while(i < arr.length){
                arr[i++] = (int)(Math.random()*(maxValue+1));
            }
            return arr;
        }

        //复制数组并返回
        public static int[] copyArray(int[] arr){
            if(arr == null|| arr.length == 0){
                return null;
            }
            int arr2[] = new int[arr.length];

            for(int i = 0; i<arr.length; i++){
                arr2[i] = arr[i];
            }

            return arr2;
        }

        //比较两数组是否相同
        public static boolean isEquals(int[] arr1, int[] arr2){
            if(arr1 == null || arr2 == null){
                return false;//空数组
            }else if(arr1.length != arr2.length){
                return false;//两数组长度不同
            }

            int i = 0;
            while(i < arr1.length){
                if(arr1[i] != arr2[i++]){
                    return false;
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
            int testTime = 101;
            boolean succeed = true;
            int maxSize = 20;
            int maxValue = 199;

            System.out.println("开始测试");
            for(int i = 0; i < testTime; i++){
                //生成随机数组
                int[] arr = generateRandomArray(maxSize, maxValue);
                //拷贝数组
                int[] arr2 = copyArray(arr);
                int[] arr1 = copyArray(arr);

                //两个数组一个用我写的排序，一个用系统排序

                comparator(arr1);
                insertionSort(arr2);

                if(!isEquals(arr1, arr2)){
                    succeed = false;
                    System.out.println("是测试的第"+testTime+"次失败了");
                    printArray(arr1);
                    printArray(arr2);
                    break;
                }
            }
            System.out.println(succeed ? "Nice!!!":"failed");

            //生成随机数组，排序后打印前后。展示一次
            int arr[] = generateRandomArray(maxSize, maxValue);
            printArray(arr);
            insertionSort(arr);
            printArray(arr);

        }


}
