package class03;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/06/01/15:37
 * @Description: 小和，一个数组中，一个数左边比他小的数的和为这个数的小和，数组的小和为所有数的小和相加
 * 核心思路：1.不求单个数的小和，直接求数组的小和，比如数组[4,5,1,2,3]中比较1比右侧的，2,3,小则产生两个为1的小和
 * 2.用归并排序的方法，在每一次排序比较数字时顺便产生小和，每次两指针都在组头向后走进行比较，
 * 3.若左右组的数字比较相等的情况下例如左组3记为a,右组3记为b，先拷贝b到help数组
 * （即以左组为基准，1）即相同情况保留左组数先拷贝右数组那个数出去2）并且右边有多少个数比她大，就产生几个这个较小数作为小和）
 * ，保留a继续与右组后续数产生比较，
 * **因为右组往后走可能出现比a更大的从而产生小和，但是b比他小的已经在左组的a之前全部与b比较过产生过小和了**
 *
 * 大和，指针从组尾开始比较，以右组为基准
 * （即 1）相同情况保留右组数先拷贝左数组那个数出去2）左边有多少个数比她小，就产生几个这个较大数作为大和**
 */
public class   Code02_SmallSum {
    public static int smallSum(int[] arr){
        if(null == arr || arr.length < 2){
            return 0;
        }
        return process(arr, 0, arr.length-1);
    }
    //递归排序并加返回值的小和
    public static int process(int[] arr, int l, int r){
        if(null == arr){
            return 0;//一个数没有小和
        }
        if(l == r){
            return 0;//小和
        }
        int m = l + ((r - l) >> 1);
        return process(arr, l, m) +
                process(arr, m+1, r) +
                merge(arr, l, m, r);//左侧内部排序和小和+右侧内部排序和小和+归并当前长度和算当前长度小和
    }
    //归并排序统计小和，[l,M]有序[M+1,R]上有序
    public static int merge(int[] arr, int L, int M, int R){
        if(L > M || L > R || M > R){
            throw new RuntimeException ("下标错误");
        }
        //int mergeSize = 1;//单组长,这里写错了，因为归并变换区间在递归部分完成，只有非递归才需要变化确定组长
        int p1 = L;
        int p2 = M+1;
        int i = 0;
        int[] help = new int[arr.length];
        int ans = 0;
        while(p1 <= M && p2 <= R){
            if(arr[p1] < arr[p2]){//左组数小于右组，产生小和。右组有几个数比左数大，就产生几个左数作为小和
                //产生R-p2+1个小和arr[p1]
                ans += (R-p2+1)*arr[p1];
                help[i++] = arr[p1++];
            }else{//相等的情况先拷贝右组，因为左组这个数还要与右组后序其他数产生小和
                help[i++] = arr[p2++];
            }
        }
        while(p1 <= M){//说明直接拷剩下的左组
            //考虑其中一指针越界，还会不会产生小和：不会
            help[i++] = arr[p1++];
        }
        while(p2 <= R){
            help[i++] = arr[p2++];
        }
        //得把排序完的刷写回去，因为这个小区间已经产生完小和。
        // 后续在大范围产生小和时，左右组需分别有序才可以根据比较左组比右组小时，
        // 观察右组较大数后还有几个数（都是比较大数大的，即有序的）再判定要产生几个这个较小数来作为小和的和
        int j = 0;
        for(i = L; i <= R; i++){//重新排序这个区间
            arr[i] = help[j++];//这里犯了弱智错误，断断续续两天花了3个小时调bug，

        }

        //错误写法
        // 错在刷写是两数组都用i定位，导致arr一递归就大量为0，
//            // 更弱智的是每次debug都是在递归的时候数组突然大量为0，
//            //在这个刷写位置根本不变数组变量
//        for(i = L; i <= R; i++){
//            arr[i] = help[i];
//        }
        return ans;
    }
    //--------------fot test写对数器----------------------
    //最小和的暴力写法，作为比较器,双层循环
    public static int comparator(int[] arr){
        int ans = 0;
        for(int i = 0; i < arr.length; i++){
            //每个数都算一下他的最小和,即所有左侧比他小的数相加
            int sum = 0;
            for(int j = i-1; j >= 0; j--){
                if(arr[j] < arr[i]){
                    sum += arr[j];
                }
            }
            ans += sum;
        }
        return ans;
    }
    public static int[] generateRandomArray(int maxSize, int maxValue){
        int size = (int)(Math.random()*(maxSize + 1));
        int[] arr = new int[size];
        for(int i = 0; i < size; i++){
            arr[i] = (int)(Math.random()*(maxValue));
        }
        return arr;
    }
    public static int[] copyArray(int[] arr){
        int[] arr2 = new int[arr.length];
        for(int i = 0; i < arr.length; i++){
            arr2[i] = arr[i];
        }
        return arr2;
    }
    public static boolean isEqual(int[] arr1, int[] arr2){
        if(null == arr1 && null != arr2){
            return false;
        }else if(null != arr1 && null == arr2){
            return false;
        }else if(arr1.length != arr2.length){
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
            System.out.print(arr[i] + ",");
        }
        System.out.println();
    }
    public static void main(String[] args){
        //思路：两个数组，一个用递归小和，一个用暴力求小和，比较返回值
        int testTime = 10;
        int maxSize = 10;
        int maxValue = 100;
        for(int i = 0; i < testTime; i++){
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if(smallSum(arr1) != comparator(arr2)){
                System.out.println("第"+(i+1)+"次"+"Oops!!：");
                System.out.printf("arr1及小和:");
                printArray(arr1);
                System.out.println("smallSum(arr1)小和"+smallSum(arr1));
                System.out.print("arr2及小和:");
                printArray(arr2);
                System.out.println("comparator(arr2)小和"+comparator(arr2));
            }
        }
        System.out.println("finish");

        //对错误写法为了检查调试的单次调试写法，
        //merge中help用刷写错误写法，help.length==arr.length
        // 在递归return处打断点，看是不是还没进入方法就一瞬间arr1赋值0
//        int[] arr1 = {71, 51, 79, 73, 19, 16, 14, 6};
//        int[] arr2 = arr1;
//        if (smallSum(arr1) != comparator(arr2)) {
//            System.out.println("arr1降序对数：" + smallSum(arr1));
//            printArray(arr1);
//            System.out.println("arr2降序对数：" + comparator(arr2));
//            printArray(arr2);
//        }
    }

}