package class03;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 雪雪子
 * @Date: 2023/06/26/19:38
 * @Description: 题解解法，sum[0] = 0,(i>0)sum[i] = nums[0]+……+nums[i-1]；
 * s(i,j)=sum(j+1)-sum(i)
 * L==R只作为递归出口，不处理此时返回的区间和个数，值得注意的是，方法一处理的情况就是（0，j）上的情况
 */
public class Code05_02_CountOfRangeSum {
    public static int countRangeSum(int[] nums, int lower, int upper){
        if(nums == null || nums.length < 1){
            return 0;//没有区间和

        }
        long[] sum = new long[nums.length+1];
        sum[0] = 0;//用作s(1,2)=sum(3)-sum(1)，若s(0,j)符合则sum(j+1)-sum(0)
        for(int i = 1; i < nums.length+1; i++){
            sum[i] = nums[i-1] + sum[i-1];
        }
        return process(sum, 0, sum.length-1, lower, upper);
    }
    public static int process(long[] sum, int L, int R, int lower, int upper){
//        if(L == R){//判断一个值的区间和是否符合范围
////            if(L != 0 && sum[L] <= upper && sum[L] >= lower){//一个数的区间，sum数组向后推一位
////                System.out.print("["+(L-1)+","+(R-1)+"] ");
////                return 1;
////            }
////            return 0;
//            return sum[L] <= upper && sum[L] >= lower && L != 0?1:0;//0位是用来给【0，j】区间减的
//        }
        if(L == R){
            return 0;
        }
        int M = L + ((R - L) >> 1);
        return process(sum, L, M, lower, upper) +
                process(sum, M+1, R, lower, upper) +
                merge(sum, L, M, R, lower, upper);
    }

    public static int merge(long[] sum, int L, int M, int R, int lower, int upper){
        int ans = 0;
        int windowL = L;
        int windowR = L;
        for(int j = M+1; j <= R; j++){
            long min = sum[j] - upper;
            long max = sum[j] - lower;
            while(windowL <= M && sum[windowL] < min){//等一向右包含到min就停止
                windowL++;
            }
            while(windowR <= M && sum[windowR] <= max){//这里多一个=是防止有多个=max的值，向后继续包含
                windowR++;
            }//此时[windowL,windowR)都是满足范围的区间左
            //打印一下，因为在外部处理了一个值length==1，本身ans的个数
            //所以此处一定只有L与R不同的区间
            //打印时候因为sum[j]-sum[i-1] = sum[i,j]
            // sum[j]-sum[k]符合范围，按理说k = i-1的
            //因为例如[-2,5，-1]对应sum[-2,3,2]
//            for(int i = windowL; i < windowR; i++){//i = k，k=[windowL,windowR)
//                System.out.printf("["+i+","+(j-1)+"]");//s(i,j)=sum(j+1)-sum(i)
//            }//无法打印，因为j-1才是对应的区间，但是如果j-1不是右组则是已经排序过的位置，打印错误
//            System.out.println();
            //打印结束
            ans += windowR - windowL;//[windowL,windowR)
        }
        //预处理结束，统计完毕正常左右组排序
        int p1 = L;
        int p2 = M + 1;
        int i = 0;//遍历help数组
        long[] help = new long[R - L + 1];
        while(p1 <= M && p2 <= R){//相等时，先排哪个都可以，因为已经预处理过了
            help[i++] = sum[p1] <= sum[p2] ? sum[p1++] : sum[p2++];
        }
        while(p1 <= M){
            help[i++] = sum[p1++];
        }
        while(p2 <= R){
            help[i++] = sum[p2++];
        }
        //刷写回sum
        i = 0;
        for(int a = L; a <= R; a++){
            sum[a] = help[i++];
        }
        return ans;
    }
    //for test
    public static void main(String[] args) {
//        int[] nums = {-2, 5, -1};//[-2,3,2,5,12,7]
//        System.out.println(countRangeSum(nums, -2, 2));
//
//        int[] nums2 = {-2, 5, -1, 3,7,-4};//[-2,3,2,5,12,7]
//
//        System.out.println(countRangeSum(nums2, -2, 2));

        int[] nums3 = {2147483647,-2147483648,-1,0};
        System.out.println(countRangeSum(nums3, -1, 0));

    }
}
