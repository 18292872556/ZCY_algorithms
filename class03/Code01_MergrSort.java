package class03;


/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/06/01/15:37
 * @Description:归并排序
 */
public class Code01_MergrSort {
    //递归归并排序1：判断组合法，需调用process
    public static void mergeSort1(int[] arr){
        if (null == arr || arr.length < 2) {
            System.out.println("数组空或length<2，mergeSort1排序失败");
            return;
        }
        process(arr,0,arr.length-1);
    }
    //递归，先左有序，再右有序，再merga
    public static void process(int[] arr, int L, int R){
        if(L == R){//递归出口
            return;
        }
        int mid = L + ((R - L) >> 1);
        //只分解问题，并没有解决最小规模的问题
        process(arr,L,mid);//分解为子问题1
        process(arr,mid+1,R);//分解为子问题2
        merge(arr,L,mid,R);//解决当前问题
    }
    //一个隐藏前提，需要左侧右侧都是有序的。才能保证两侧比较填数不需要回退
    //准备一个help等长数组，p1=L,p2=M+1;比较大小后填数，任一指针越界另一个指针区域copy剩余所有数
    public static void merge(int[] arr, int L, int M, int R) {
        int p1 = L;
        int p2 = M + 1;
        int[] help = new int[R - L + 1];//只操作L到M之间的
        int i = 0;
//写法一
        while (p1 <= M || p2 <= R) {//p1:L~<M  p2: M+1~R
            //左右都不越界需要两指针比较选小的填
            //特殊情况，只有两位，L==M==0,R==1
            //if (p2 > R || arr[p1] <= arr[p2])
            //当p1越界后来到此处，p1与p2相等，错误的给p1++,实际上应该是右组，导致指针越界
            //修改为if (p2 > R || arr[p1] < arr[p2])
            /*!!!!不可以这样写的原因是虽然每次进入次循环一定是至少有一个指针没有越界!!!!
            //但是前者的判断条件 if ((p2 > R && p1 <= M) || arr[p1] < arr[p2])中显然p1<=M是多余的
            因为能进入循环就说明如果一个越界，那另一个一定没越界的
            但前者false来判断后者时，两指针的情况将复杂得多。比如先判断p2没有越界，再判断p1所指是否<p2,但p1是否越界不得知，就会异常
            */
            if (p2 > R || (p1 <= M && arr[p1] < arr[p2])) {//右指针越界或者右指针比左指针大
                help[i++] = arr[p1++];

            } else if (p1 > M || (p2 <= R && arr[p1] >= arr[p2])) {//右指针更小并且右指针没越界（此处隐藏条件是，p2<=R且arr[p1] > arr[p2]。
                help[i++] = arr[p2++];

            }

        }
 //写法二
//        while(p1 <= M && p2 <= R){
//            //arr[p1] < arr[p2]? help[i++] = arr[p1++]:help[i++] = arr[p2];
//            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
//        }
//        while(p1 <= M){//p2越界
//            help[i++] = arr[p1++];
//        }
//        while(p2 <= R){
//            help[i++] = arr[p2++];
//        }
        //复制help到arr上
        for(i = 0; i < help.length; i++){
            arr[L+i] = help[i];
        }
    }

    //非递归方法实现
    public static void mergeSort2(int[] arr) {
        if(arr == null || arr.length < 2){
            System.out.println("数组空或length<2，mergeSort2排序失败");
            return;
        }
        int N = arr.length;
        int mergeSize = 1;//最开始1，即2个为一左组一右组。相当于k=2，指当前有序的左组长

        while (mergeSize < N) {//每次变换左组长度，开始进行归并左组和右组。前提是左右组都分别有序
            //外循环确定组长
            //确定L和M、R的位置
            int L = 0;
            while (L < N) {
                if (mergeSize >= N - L) {//N-l 用在最后一个左组长 < 原定组长的情况，直接跳出说明这一趟已经全部归并完成
                    //因为归并排序前提就是左右组都分别有序才归并，走到最后一个左组长度不足，没有右组就无需归并一定是有序的
                    break;
                }
                //左组L,M;M+1,R。每次确定L位置
                //内循环确定每次指针位置
                int M = L - 1 + mergeSize;//右组长不足的情况
                int R = M + Math.min(mergeSize, N - M -1);//剩下的右组长或原定组长
                merge(arr, L, M, R);
                L = R + 1;
            }
            if(mergeSize > N/2){//因为此处若mergeSize > N/2说明这次排序已经让mergeSize*2的长度有序了
                break;//防止溢出
            }
            mergeSize <<= 1;//*=2
        }
    }
    public static int[] generateRandomArray(int maxSize, int maxValue){
        int size = (int)(Math.random()*(maxSize+1));
        int[] arr = new int[size];
        for(int i = 0; i < size; i++){
            arr[i] = (int)(Math.random()*(maxValue+1));
        }
        return arr;
    }
    public static int[] copyArray(int[] arr){
        int size = arr.length;
        int[] arr2 = new int[size];
        for(int i = 0; i < size; i++){
            arr2[i] = arr[i];
        }
        return arr2;
    }
    public static boolean isEqual(int[] arr1, int[] arr2){
        if(arr1 == null && arr2 == null){
            return true;
        }
        if(arr1 == null && arr2 != null || arr1 != null && arr2 == null){
            return false;
        }
        if(arr1.length != arr2.length){
            return false;
        }
        for(int i = 0; i < arr1.length; i++){
            if(arr1[i] != arr2[i]){
                return false;
            }
        }
        return true;
    }
    public static void printArray(int[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.printf(arr[i]+",");
        }
        System.out.println();
    }
    public static void main(String[] args){
        int maxSize = 100;
        int maxValue = 100;
        int testTime = 1000;
        for(int i = 0; i < testTime; i++){
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            mergeSort1(arr1);
            mergeSort2(arr2);
            //System.out.println("第"+i+"次");
            if(!isEqual(arr1, arr2)){
                System.out.println("错误！");
                printArray(arr1);
                printArray(arr2);
            }

        }
        System.out.println("finish!");

    }
}
