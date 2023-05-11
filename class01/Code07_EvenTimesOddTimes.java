package class01;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/01/03/21:30
 * @Description:
 */
public class Code07_EvenTimesOddTimes {
    //在一个数组中，只有一种数出现了奇数次，其他数都出现了偶数次。求出这个数？
    public static void printOddTimesNum1(int[] arr){
        if(arr == null || arr.length < 1){
            return;
        }
        //利用异或 ，n^n = 0, n^0 = n
        int sum = 0;//用来存储所有数的异或
        for(int i = 0; i < arr.length; i++){
            sum ^= arr[i];
        }
        System.out.println("只出现了奇数次的数为："+sum);

    }

    //数组中有俩种数，出现奇数次
    public static void printOddTimesNum2(int[] arr){
        if(arr == null || arr.length < 2 ){
            return;
        }
        int a = 0;
        int b = 0;
        for(int i = 0; i < arr.length; i++){
            a ^= arr[i];
        }
        //此时，sum = a^b,(假设a,b是出现了奇数次的两个数)
        //在sum里挑出二进制位为1的一个数，说明这一位a和b是不同的。以这一位为标准将数组的数分为两部分
        //怎么把一个int类型数提取出最右侧的1    N&(~N+1)
        int flag = a&(~a+1);
        //根据这一位把数组区分成两组数
        //怎么判断flag是哪一位，又怎么区分这一位是1还是0？用&,因为flag只有一位是1，&的话，如果是1说明这一位是1，如果是0说明这一位是0
        for(int i = 0; i < arr.length; i++){
            if((flag & arr[i]) == 1){
                //flag的这一位为1
                b ^= arr[i];
            }
        }
        a = a^b;//(a^b^b)
        System.out.print("数组arr:");
        printArray(arr);
        System.out.println("a:"+a+",b:"+b);

    }

    //数二进制数中1的位数
    public static int bit1count(int N){
        int num = N;//逐步取掉最右侧的1
        int count = 0;//计数
        //每次取最右侧的1
        while(num != 0){
            int flag = num&(~num+1);
            num -= flag;//去掉最右侧的1
            //num ^= flag;why？不同位全保留，只flag的那一位使num异或为0
            count++;
        }
        return count;
    }

    public static void printArray(int[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i]+",");
        }
        System.out.println();
    }

    public static void main(String[] args){

        int[] arr1 = {1,2,2,3,3,4,4,5,5,6,6,7,7};//1
        int[] arr2 = {2,45,2,23,23,45,100,34,34};//100
        int[] arr3 = {49,56,49,49,56,23,23,34,12,12};//49,34
        int[] arr4 = {345,123,123,345,345,23,23,56,56,56,56,334};//334,345

        printOddTimesNum1(arr1);
        printOddTimesNum1(arr2);
        printOddTimesNum2(arr3);
        printOddTimesNum2(arr4);

        System.out.println(bit1count(7));
        System.out.println(bit1count(6));

    }
}
