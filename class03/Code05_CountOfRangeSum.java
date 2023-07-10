package class03;
/**
 * Created with IntelliJ IDEA.
 * @Author: 雪雪子
 * @Date: 2023/6/26/2:40
 * @Description: ✘ 不理解左程云写的这个为啥过了leetcode??!疑惑的点在于如果sum[i]=num[0]+……+num[i]
 * 区间和应该是sum[i,j]=sum[j]-sum[i-1]
 * 但是这样的话如果求[0,4]上的区间和没法=sum[4]-sum[-1]，对应的区间和也不对，只有返回的区间和个数正确？到底为啥
 * 所以如果碰到s(0,j)符合的条件，到底是怎么数的个数正确?、
 * ！！！理解了：s(0,j)相当于L==R的情况统计，s(j,j)有sum[j]-sum[j-1]处理
 * ========================================================================
 * 给你一个整数数组 nums 以及两个整数 lower 和 upper 。
 * 求数组中，值位于范围 [lower, upper] （包含 lower 和 upper）之内的 区间和的个数 。
 * 区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。
 * 示例 1：
 * 输入：nums = [-2,5,-1], lower = -2, upper = 2
 * 输出：3
 * 解释：存在三个区间：[0,0]、[2,2] 和 [0,2] ，对应的区间和分别是：-2 、-1 、2 。
 *-------------------------------------------------------------------------
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/count-of-range-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * --------------------------------------------------------------------------
 * 思路：理解题意：区间的和要求在[lower,upper]范围上，返回满足条件的区间和的个数即可
 *      解法：通过nums数组构成sum数组，对应nums数组，即sum[i]为nums[0]~nums[i]的和
 *这样的方法做出来区间和s(i,j)=sum[j]-sum[i-1]=sum[i,j]，并且
 *      妙处：1）sum可以排序，排序不影响计算区间和。
 *           2）用归并排序,因为左右组内有序，所以windowL到windowsR连续区间内的k可以和右组的j组成很多符合条件的区间和
 *1）用归并方法分左右组，以右组为基准，右组j遍历,定义max=sum[j]-lower,min=sum[j]-upper
 * min和max的推导方法  lower <= sum[i,j] <= upper,
 *                   lower <= sum[j]-sum[i-1] <= upper,
 *            sum[j]-upper <= sum[i-1] <= sum[j]-lower,此处注意：左组用k代替遍历，符合的k实际上比i小1
 *2）windowL和windowR从左组头遍历，到左组尾结束。左点>=min即可停止，右点<=max停止，
 * 即L>=min停止，R>max停止，ans += windowR - windowL；则这一位i对应的区间排查完，再进行下一个j
 * 3)预处理结束后正常给sum数组归并排序
 */
public class Code05_CountOfRangeSum {
   /**
   * @Description:
   * @Param: nums 原数组
   * @Param lower 左区间
   * @Param upper 右区间
   * @return: int 返回满足条件的"区间和个数"
    *
   */
    public static int countRangeSum(long[] nums, int lower, int upper) {
        if(nums == null || nums.length < 1){
            return 0;//没有区间和

        }
        long[] sum = new long[nums.length];
        //此处
        sum[0] = nums[0];
        for(int i = 1; i < nums.length; i++){
            sum[i] = nums[i] + sum[i-1];
        }
        return process(sum, 0, sum.length-1, lower, upper);

    }
    //递归调用自己，新建sum数组，对应nums数组，即sum[i]为nums[0]~nums[i]的和
    public static int process(long[] sum, int L, int R, int lower, int upper){
        if(L == R){//判断一个值的区间和是否符合范围
//            if(sum[L] <= upper && sum[L] >= lower){
//                System.out.print("["+L+","+R+"] ");
//                return 1;
//            }
//            return 0;
            return sum[L] <= upper && sum[L] >= lower?1:0;
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
            long min = sum[j] - upper;//
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
//            for(int i = windowL-1; i < windowR-1; i++){//i = k-1，k=[windowL,windowR)
//                System.out.printf("["+i+","+j+"]");
//            }
//            System.out.println();
            //打印结束
            ans += windowR - windowL;
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
//        long[] nums = {-2, 5, -1};//
//        System.out.println(countRangeSum(nums, -2, 2));
//
//        long[] nums2 = {-2, 5, -1, 3,7,-4};//[-2,3,2,5,12,7]
//        System.out.println(countRangeSum(nums2, -2, 2));

//        long[] nums3 = {2147483647,-2147483648,-1,0};
//        System.out.println(countRangeSum(nums3, -1, 0));

        long[] nums4= {0,2147483647,-2147483648,-1,0};
        System.out.println(countRangeSum(nums4, -1, 0));
    }

}
