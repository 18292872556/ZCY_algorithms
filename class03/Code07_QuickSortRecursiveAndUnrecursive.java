package class03;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static class03.Code01_MergrSort.*;
/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 雪雪子
 * @Date: 2023/06/29/12:07
 * @Description: 快排递归和非递归的
 */
public class Code07_QuickSortRecursiveAndUnrecursive {
    //荷兰国旗问题，返回=区的左右边界[左，右]
    //以arr[R]为划分，分为<区，=区，>区
    public static int[] netherlandsFlag(int[] arr, int L, int R){
        if(L > R){//不存在=区
            return new int[]{-1,-1};
        }
        if(L == R){//一个值返回自己为=区
            return new int[]{L,R};
        }//验证了因为process中递归出口写了L>=R的情况，其实这里不写这两个判断也不影响
        int less = L-1;
        int more = R;//R先作为划分标准，最后换
        int i = L;//遍历数组
        while(i < more){//i=more时，走到了>区第一个值,R无需判断直接跟=区最右侧+1即>区最左值交换即可，相当于=区右扩，>区右移
            if(arr[i] < arr[R]){//进<区
                swap(arr, i++, ++less);//<区后换过来的必然属于=区，直接++
            }else if(arr[i] > arr[R]){//进>区
                swap(arr, i, --more);//>区换过来的没有判断
            }else{
                i++;//=区处于less与i之间
            }
        }
        //遍历i与more边界撞上,交换more第一个值与R位的值
        swap(arr,more,R);
        return new int[]{less+1, more};
    }
    public static void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] =tmp;
    }
    //快排递归版本2.0,调用Netherlands递归排序
    public static void quickSort1(int[] arr){
        if(null == arr || arr.length < 2){
            return;
        }
        process(arr, 0, arr.length-1 );
    }
    public static void process(int[] arr, int L, int R){
        if(L >= R){//递归出口,一个值无需分区,不存在的区间也直接返回
            return;
        }
        int[] equalArea = netherlandsFlag(arr, L, R);
        //思考：怎么处理返回[-1,-1]的情况，能返回说明L>R，根本不会进到这一步，上一步已经被筛选掉了
        process(arr, L, equalArea[0]-1);
        process(arr, equalArea[1]+1, R);
    }
    //快排非递归版本需要的辅助类
    //Op指要处理的是什么范围上的排序，
    public static class Op{
        public int l;
        public int r;

        public Op(int left, int right){
            l = left;
            r = right;
        }
    }
    //快排3.0 非递归版本，用栈来执行
    //定义equalArea 存分区后返回的区间，Stack<Op>
    //psuh(new Op(0, el-1))(new Op(er+1,N-1)确定在=区左右再排序
    public static void quickSort2(int[] arr){
        if(null == arr || arr.length <2){
            return;
        }
        //交换arr[R]和任意位
        int N = arr.length;//数组长
        int L = 0;
        int R = N - 1;
        swap(arr, R, L + (int)(Math.random() * (R - L + 1)));//随机数[0,R-L]加后[L,R]
        int[] equal = netherlandsFlag(arr, L, R);
        Stack<Op> stack = new Stack<>();
        stack.push(new Op(L, equal[0] - 1));
        stack.push(new Op(equal[1] + 1, R));
        while(!stack.isEmpty()){//栈空停止
            //开始取栈顶，如果区间存在，排序他的=区,栈内存他的左右。
            // 区间不存在就弹出，下一趟。相等也算不存在，因为不用排说明
            Op p = stack.pop();
            if(p.l < p.r){
                swap(arr, p.r, p.l + (int)(Math.random() * (p.r - p.l + 1)));
                equal = netherlandsFlag(arr, p.l, p.r);
                stack.push(new Op(p.l, equal[0] - 1));
                stack.push(new Op(equal[1] + 1, p.r));
            }
        }
        //栈空了，说明所有区间上的都排序完了
    }
    //快排3.0 非递归版本 用队列来执行,其实都是用特殊数据结构来保存不同L,R区间，划分每一次拿到栈顶区间的左右
    //存入新划分好的左右区间值，依次类推。当取出的区间不满足L<R时，只弹出。当队列空则排序结束
    public static void quickSort3(int[] arr){
        if(null == arr || arr.length <2){
            return;
        }
        //交换arr[R]和任意位
        int N = arr.length;//数组长
        int L = 0;
        int R = N - 1;
        swap(arr, R, L + (int)(Math.random() * (R - L + 1)));//随机数[0,R-L]加后[L,R]
        int[] equal = netherlandsFlag(arr, L, R);
        Queue<Op> stack = new LinkedList<>();
        stack.offer(new Op(L, equal[0] - 1));
        stack.offer(new Op(equal[1] + 1, R));
        while(!stack.isEmpty()){//队列空停止
            //开始取栈队列头，如果区间存在，排序他的=区,队列内入他的左右。
            // 区间不存在就弹出，下一趟。相等也算不存在，因为不用排说明
            Op p = stack.poll();
            if(p.l < p.r){
                equal = netherlandsFlag(arr, p.l, p.r);
                stack.offer(new Op(p.l, equal[0] - 1));
                stack.offer(new Op(equal[1] + 1, p.r));
            }
        }
        //队列空了，说明所有区间上的都排序完了
    }

    //导入Code01的四个 for test 方法，生成随机数组，拷贝数组，对比两个数组，打印数组

    //跑大样本随机测试（对数器）
    public static void main(String[] args) {

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
            if(!isEqual(arr1, arr2) || !isEqual(arr2, arr3)){
                System.out.println("Oops");
            }

        }
        System.out.println("finish");

    }
}
