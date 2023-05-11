package class01;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/04/21/18:39
 * @Description:详解关于交换两数值的位运算
 * 数组交换时不可以是一个位置上的数
 */
public class Code00_Swap {


    public static void swap(int[] arr, int i, int j){
        if(i == j || i<0 || i>= arr.length || j<0 || j>=arr.length){
            System.out.println("交换坐标错误");
            return;
        }
        arr[i] = arr[i]^arr[j];//a = 90^86
        arr[j] = arr[i]^arr[j];//b = 90^86^86 = 90
        arr[i] = arr[i]^arr[j];//a = 90^86^90 = 86
    }
    public static void main(String[] args){
        //N^N = 0  N^0 = N。因为异或指的是位相同为0，位不同为1
        int a = 90;
        int b = 86;
        System.out.println(a);
        System.out.println(b);
        a = a^b;//a = 90^86
        b = a^b;//b = 90^86^86 = 90
        a = a^b;//a = 90^86^90 = 86
        System.out.println(a);
        System.out.println(b);

        int[] arr = {1,2,3,4,5,6};
        int i = 0;
        int j = 0;
        System.out.println(arr[i]);//1
        System.out.println(arr[j]);//1
        arr[i] = arr[i] ^ arr[j];//1^1 = 0
        arr[j] = arr[i] ^ arr[j];//0^0 = 0 （因为arr[i]和arr[j]是一个位置的一个数）应该是0^1 = 1
        arr[i] = arr[i] ^ arr[j];//0^0 = 0（应该是0^1 = 1
        System.out.println(arr[i]);//0
        System.out.println(arr[j]);//0

         System.out.println("arr[i]="+arr[2]+",arr[j]="+arr[4]);
        swap(arr,2,4);
        System.out.println("arr[i]="+arr[2]+",arr[j]="+arr[4]);


    }
}
