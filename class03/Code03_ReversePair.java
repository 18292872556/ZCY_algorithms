package class03;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/06/09/15:38
 * @Description: 找出数组中所有的逆序对，原理：找一个数左侧比他大的有几个
 * 左右组比较相等时，先拷贝走左侧的数，因为右侧的需要与左组后侧的更大数统计逆序对
 */
public class Code03_ReversePair {
    public static int reverPairNumber(int[] arr) {
        if (null == arr || arr.length < 2) {
            return 0;
        }
        System.out.printf("递归：");
        return process(arr, 0, arr.length - 1);

    }

    public static int process(int[] arr, int l, int r) {
        if (null == arr || arr.length < 2) {
            return 0;
        }
        if (l == r) {
            return 0;
        }
        int mid = l + ((r - l) >> 1);
        return process(arr, l, mid) +
                process(arr, mid + 1, r) +
                merge(arr, l, mid, r);//左右组内部的降序对+归并两组时的降序对
    }

    //既要归并排序升序（所以指针从前往后走，右组比较时，知道左组数越往后越大），中途要统计逆序对（即降序对），
    // 以右组为基准，观察左组有几个比他大。
    public static int merge(int[] arr, int L, int M, int R) {
        if (L > M || L > R || M > R) {
            throw new RuntimeException("排序坐标错误");
        }
        int p1 = L;
        int p2 = M + 1;
        //int mergeSize = 1;
        int ans = 0;//降序对个数
        int[] help = new int[arr.length];
        int i = 0;
        while (p1 <= M && p2 <= R) {
            if (arr[p1] > arr[p2]) {
                for(int k = p1; k <= M; k++){//这里可以不用谢循环，直接ans += M-p1+1即可，我是为了打印
                    System.out.print("(" + arr[k] + "," + arr[p2] + ") ");
                    ans++;//不可以直接一次++，应该是j后到M有几个就有几个降序对
                }
                help[i++] = arr[p2++];
            } else {//==时也是先拷贝走左组，留右组这个数与左组后更大的数组成降序对
                help[i++] = arr[p1++];
            }
        }
        //当其中一组越界时，组内都是有序的，组内已经统计过降序对了，不会再产生
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        //又忘记刷写回arr
        i = 0;
        for (int j = L; j <= R; j++) {
            arr[j] = help[i++];
        }
        return ans;
    }

    //-------------------fot test 对数器---------------------------
    //暴力法：找降序对，从前往后遍历数组，每个数挨个向后找比自己小的。or每从后往前，每个数挨个向前找比自己大的
    public static int comparator(int[] arr) {
        int ans = 0;
        System.out.println();
        System.out.printf("暴力：");

        for (int i = 0; i < arr.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] > arr[i]) {
                    ans++;
                    System.out.print("(" + arr[j] + "," + arr[i] + ") ");
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

    public static void main(String[] args) {
        //两个相同数组一个用递归算降序对，一个用暴力法算。若不等则错误
        int testTime = 10;
        int maxSize = 10;
        int maxValue = 1000;
        for(int i = 0; i < testTime; i++){
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            //int[] arr2 = arr1;//错误，相当于给数组按别名，并不是复制数组
            int[] arr2 = copyArray(arr1);
            //保存两数组原值，和这一次测试求出的降序对数
            int[] arr3 = copyArray(arr1);
            int[] arr4 = copyArray(arr2);
            int ans1 = reverPairNumber(arr1);
            int ans2 = comparator(arr2);
            if(ans1 != ans2){
                System.out.println("第"+(i+1)+"次"+"Oops!");
                //System.out.println("arr1降序对数："+ reverPairNumber(arr1));//这里再走也没意义，一定是0，因为判断部分已经排好序走过
                System.out.println("arr1降序对数："+ ans1);//改进写法用ans存一下，一次循环测试只调用一次归并排序
                System.out.print("arr1:");
                printArray(arr3);
                System.out.println("arr2降序对数："+ ans2);
                System.out.print("arr2:");
                printArray(arr4);
                //打印没意义，因为过程已经排好序
               // printArray(arr2);

            }
        }
        System.out.println("finish");


    }
}
