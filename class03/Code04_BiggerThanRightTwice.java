package class03;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/06/01/15:39
 * @Description:l; 重要翻转对：两倍降序对，在数组中，若 i < j，且arr[i] > 2*arr[j] ，
 * 则称（i,j)为一个重要翻转对
 * 重点考虑排序问题
 */
public class Code04_BiggerThanRightTwice {
    public static int reversePairs(int[] arr){
        System.out.print("递归：");
        if(arr == null || arr.length < 2){
            return 0;
        }

        return process(arr, 0, arr.length-1);
    }
    public static int process(int[] arr, int l, int r){
        if(l == r){
            return 0;
        }
        int mid = l + ((r - l) >> 1);
        return process(arr, l, mid) +
                process(arr, mid +1, r) +
                merge(arr, l, mid, r);

    }
    /*重点考虑一个问题：与降序对基本一样写法，只是要求左数要比右数*2大，但是继续这样写。若2*右数>左数>右数，
    此时help拷走右数，但是！！！左边或许还有比2*右数更大的，却会因此错过！！！
    疑惑？为什么一样的思路，求降序对这样写就可以呢？
    因为求逆序对两组比较时，若左大，可以直接确定左组后面也一定都比他大，左小拷走，左等也拷走，留了右数与左后更大的数形成降序对
    但2倍就更复杂，不能正常排序。因为通常这种题目排序只是为了方便排除已经统计过的左右组中小和或降序对个数，避免归并左右组时重复统计
    但很明显，此处不可以通俗排序，会导致错过一些重要翻转对，
    如果我们可以只判断左数>右数*2,根据这个排先后顺序，排序后不是正常递增，但不是正常递增的话又怎么判断左组后面有几个符合条件呢？*/
    public static int merge(int[] arr, int L, int M, int R){
        int p1 = L;
        int p2 = M+1;
        int ans = 0;//重要翻转对个数
        int[] help = new int[R - L +1];
        int i = 0;//用来遍历help数组

        while(p1 <= M && p2 <= R){
            if(arr[p1] > arr[p2]*2){//左数比右数的两倍大，左数后到左组结束一定都比右数两倍大
                for(int k = p1; k <= M; k++){//为了打印翻转对，不打印写ans += M-p1+1;
                    ans++;
                    System.out.print("("+arr[k]+","+arr[p2]+") ");//因为等于这个区间固定了右数，找左数比自己两倍还大的
                }
                help[i++] = arr[p2++];
            }else if(arr[p1] > arr[p2]){
                //特殊处理，这个区间后或许有比p2*2更大的，可以循环处理一下，但可能又会与第一个if的重复。但不清楚时间复杂度
                for(int k = p1+1; k <= M; k++){
                    if(arr[k] > arr[p2]*2){//判断后续可能与这一位arr[p2]产生重要翻转对
                        ans++;
                        System.out.print("("+arr[k]+","+arr[p2]+") ");
                    }
                }
                help[i++] = arr[p2++];
            }else{//arr[p1] <= arr[p2]都是先拷贝走左数，==时留右数跟左侧后续更大的数比较产生重要翻转对
                help[i++] = arr[p1++];
            }
        }
        while(p1 <= M){
            help[i++] = arr[p1++];
        }
        while(p2 <= R){
            help[i++] = arr[p2++];
        }
        //注意：之前这里犯错，明确两数组赋值的区间范围不同
        i = 0;
        for(int k = L; k <= R; k++){
            arr[k] = help[i++];
        }
        return ans;
    }
    //老师的写法，一个windowR变量检索右组。反正都是写双层循环
    //思路：其他跟求降序对一样，但开头用双层循环统计一下ans
    public static int merge2(int[] arr, int L, int M, int R){
        int windowR = M+1;//遍历右组
        int ans = 0;
        //思路：i遍历左组，window遍历右组。因为需要右边小，所以以左组为基准
        //每一个左数，都对应从右组头开始找是否大于右数*2，不满足说明后面一定也不满足，
        //因为越往后越大，但需要右数小。满足就继续向后找，满足的右数一定是从右组头开始连续的一串
        for(int i = L; i <= M; i++){//查左组比右组哪几个*2还大
            while(windowR <= R && arr[i] > arr[windowR]*2){
                windowR++;//不符合情况说明已经走到右组比较大的部分，后面也不会再有符合的
            }
            ans+= windowR - M - 1;//因为Windows越界一位
        }
        //ans已经双层循环统计完了
        int p1 = L;
        int p2 = M+1;
        int i= 0;
        int[] help = new int[R - L + 1];
        while(p1 <= M && p2 <= R){
            help[i++] = arr[p1] > arr[p2]?arr[p2++]:arr[p1++];
            //为什么三目运算符不能这样写
            //arr[p1] > arr[p2] ? help[i++] = arr[p2] : help[i++] = arr[p1];
        }
        while(p1 <= M){
            help[i++] = arr[p1++];
        }
        while(p2 <= R){
            help[i++] = arr[p2++];
        }
        for(int k = 0; k < help.length; k++){
            arr[L+k] = help[k];
        }
        return ans;
    }
    //--------------------------for test-----------------------
    public static int comparator(int[] arr){
        int ans = 0;
        System.out.println();
        System.out.printf("暴力：");
        for(int i = 0; i < arr.length; i++){
            for(int j = i-1; j >= 0; j--){//在这个数左侧找比它两倍大的
                if(arr[j] > 2 * arr[i]){
                    ans++;
                    System.out.print("("+arr[j]+","+arr[i]+") ");

                }
            }
        }
        System.out.println();
        return ans;
    }
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int size = (int) (Math.random() * (maxSize + 1));
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static int[] copyArray(int[] arr) {
        int[] arr2 = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arr2[i] = arr[i];
        }
        return arr2;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if (null == arr1 || null != arr2) {
            return false;
        } else if (null != arr1 || null == arr2) {
            return false;
        } else if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void printArray(int[] arr) {
        for (int i : arr) {
            System.out.print(i + ",");
        }
        System.out.println();
    }

    public static void main(String[] args){

        int testTime = 10;
        int maxSize = 20;
        int maxValue = 1000;
        for(int i = 0; i < testTime; i++){
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            //为了打印
            int[] arr3 = copyArray(arr1);
            int[] arr4 = copyArray(arr2);
            int ans1 = reversePairs(arr1);
            int ans2 = comparator(arr2);
            if(ans1 != ans2){
                System.out.println("第"+i+"次"+"Oops!");
                System.out.print("重要翻转对数："+ans1+".arr1:");
                printArray(arr3);
                System.out.printf("重要翻转对数："+ans2+".arr2:");
                printArray(arr4);
            }
        }
        System.out.println("finish!!!!");

    }
}
