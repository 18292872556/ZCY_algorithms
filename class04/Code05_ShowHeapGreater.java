package class04;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 雪雪子
 * @Date: 2023/07/14/14:44
 * @Description: 调用自己写的堆方法，（即入堆后改掉了作为排序规则的属性，依旧能自动重新排序符合堆）
// 测试自己写的堆，满足对象入堆排序完成后，再改对象中用来排序的属性，依然有序（堆）

// 一定注意看main函数及其注释，用代码展示加强堆的用法
// 展示了加强堆怎么支持非基础类型和基础类型
// 看这个测试！
// 每一句都看懂
 */
public class Code05_ShowHeapGreater {

    public static void main(String[] args) {
        //展示非基础类型的用法一===========================================================
        //创建6个Student类对象，属性（int,String）
        Student s1 = new Student(17, "学生A");
        Student s2 = new Student(10, "学生B");
        Student s3 = new Student(29, "学生C");
        Student s4 = new Student(33, "学生D");
        Student s5 = new Student(54, "学生E");
        Student s6 = new Student(93, "学生F");
        //生成一个加强堆，排序策略是年龄小的在堆顶，年龄小根堆。参数用lambda表达式
        Code06_HeapGreater<Student> heap1 = new Code06_HeapGreater<>((a, b) -> a.age - b.age);//升序
        //把所有学生入堆
        heap1.push(s1);
        heap1.push(s2);
        heap1.push(s3);
        heap1.push(s4);
        heap1.push(s5);
        heap1.push(s6);
        // 加入之后
        // 可以把某个同学的年龄改了
        // 比如把s5，也就是E同学
        // 年龄从54改成了4
        s5.age = 4;
        // 此时堆被破坏了，因为你擅自改了一个同学的年龄
        // 只需要调用resign方法，就能让堆恢复成年龄小根堆
        // 而且复杂度是O(log N)，很快的
        // 系统提供的堆做不到的，加强堆可以
        heap1.resign(s5);
        // 依次弹出所有学生，用pop
        // 会发现从年龄小到年龄大依次弹出
        // 说明堆是正确的
        while(heap1.size() != 0){
            Student s = heap1.pop();
            System.out.println("年龄：" + s.age + "姓名：" + s.name);
        }

        // 现在展示基础类型的加强堆用法==================================================
        int[] arr = {3, 3, 2, 5, 3};
        // arr[0] == 3
        // arr[1] == 3
        // arr[2] == 2
        // arr[3] == 5
        // arr[4] == 3
        // 每个位置的数字一定会自带一个下标，这是一定的!
        // 任何基础类型的元素，天生一定会自带一些类似下标的身份信息的！这是一定的！
        // 生成一个加强堆
        // 加强堆里只放下标即可，因为通过下标可以找到数字
        // 排序策略是 :
        // 数字小的下标，在堆顶
        //创建加强堆，并且参数用lambda表达式
        Code06_HeapGreater<Integer> heap2 = new Code06_HeapGreater<>((a, b) -> arr[a] - arr[b]);
        // 把数组所有的下标加入堆
        // 就等于加入了所有数字
        heap2.push(0);
        heap2.push(1);
        heap2.push(2);
        heap2.push(3);
        heap2.push(4);
        // 加入之后
        // 可以把某个下标上的数字改了
        // arr[1]原来是3，现在变成了-9
        arr[1] = -9;
        // 此时堆被破坏了，因为你擅自改了一个下标的数字
        // 只需要调用resign方法，就能让堆恢复
        // 而且复杂度是O(log N)，很快的
        // 系统提供的堆做不到的，加强堆可以
        // 调用resign方法
        heap2.resign(1);
        // 依次弹出所有下标
        // 会发现下标上的数字越小，下标越早弹出
        // 说明堆是正确的
        while(heap2.size() != 0){
            int i = heap2.pop();
            System.out.println("下标：" + i + "值：" + arr[i]);
        }

    }
    // 一个自己定义的非基础类型
    public static class Student {
        public int age;
        public String name;

        public Student(int a, String n) {
            age = a;
            name = n;
        }
    }

}
