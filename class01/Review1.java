package class01;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xuexuezi
 * @Date: 2023/03/17/22:37
 * @Description: 我的复习
 */
public class Review1<T extends Comparable> {
    //泛型类，只允许泛型为实现Camparable接口的实现类的引用调用

    //如果使用泛型，只能使用静态方法自己定义的泛型
    //所以改成非静态的普通方法，虽然不能直接用类名调用，还要创造对象，但为了实验泛型类使用在方法中用
    public void swap(T[] arr, int a, int b){
        T tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
    public void bubbleSort(T[] arr){
        //
        for(int i=arr.length-1; i>0; i++){
            for(int j=0; j<i; j++) {
                if(arr[j].compareTo(arr[j+1]) > 0){//用接口Comparable的方法compareTo（E o)
                    swap(arr, j, j+1);
                }
            }
        }
    }
    public void printarray(T[] arr){
        for(int i=0; i<arr.length; i++){
            System.out.print(arr[i]);
        }
        System.out.println();
    }

    //test
    public void main(String[] args){
        int arr[] = new int[]{8,7,6,10,1,3,2};
        String arr2[] = new String[]{"89rt","89","8"};

        Review1 r1 = new Review1();
        //r1.bubbleSort(arr);//在这一步夭折，因为要求的泛型是继承接口的包装类，不是基本数据类型。


    }
}
