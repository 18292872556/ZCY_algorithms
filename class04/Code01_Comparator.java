package class04;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 雪雪子
 * @Date: 2023/06/29/13:15
 * @Description: 继承比较器，改写排序规则
 */
public class Code01_Comparator {
    //学生类：三个属性
    public static class Student {
        String name;
        int id;
        int age;

        public Student(String name, int id, int age) {
            this.name = name;
            this.id = id;
            this.age = age;
        }
    }

    //排序Student类
    //根据id从小到大，若id同，按年龄从大到小
    public static class IdShengAgeJiangOrder implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.id != o2.id ? (o1.id - o2.id) : (o2.age - o1.age);
        }
    }
//    //排序Student类
//    //id升序，小在前。id同，age降序，大在前
//    public static class IdInAgeDe implements Comparator<Student> {
//        @Override
//        public int compare(Student o1, Student o2) {
//            return o1.id != o2.id ;
//        }
//    }
    //排序Student类
    //id升序
    public static class IdAscending implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.id - o2.id;
        }
    }

    //排序Student类
    //id降序
    public static class IdDescending implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o2.id - o1.id;
        }
    }

    //排序Integer类
    //降序
    public static class MyComp implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    //    //排序Integer类
//    //原作有，我觉得都是Integer降序没必要
//    public static class AComp implements Comparator<Integer>{
//        @Override
//        public int compare(Integer o1, Integer o2) {
//            return 0;
//        }
//    }
    //打印学生三个属性
    public static void printStudents(Student[] students){
        for(Student s : students){
            System.out.println("Name:"+s.name+" Id:"+s.id+" Age:"+s.age);
        }
    }
    //打印数组
    public static void printArray(Integer[] arr){
        for(Integer i : arr){
            System.out.print(i+",");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        //Integer数组用MyComp排序并打印
        Integer[] arr = { 1, 2, 3, 5, 2, 1 };
        Arrays.sort(arr, new MyComp());
        printArray(arr);

        System.out.println("====================================");

        Student stu1 = new Student("A",4,18);
        Student stu2 = new Student("B",4,20);
        Student stu3 = new Student("C",4,8);
        Student stu4 = new Student("D",3, 4);
        Student stu5 = new Student("E",3, 25);


        //---------------------------------------------------------------
        System.out.println("第一条打印");
        Student[] stus = {stu1, stu2, stu3, stu4, stu5};
        Arrays.sort(stus, new IdShengAgeJiangOrder());
        printStudents(stus);


        System.out.println("第二条打印");
        ArrayList<Student> stus2 = new ArrayList<>();
        stus2.add(stu1);
        stus2.add(stu2);
        stus2.add(stu3);
        stus2.add(stu4);
        stus2.add(stu5);

        //5个Student的ArrayList用.sort(比较器）排序
        /**ArrayList两种调用排序写法-------------------------------------------------*/
        //ArrayList，两种排序都可以，本身是按照添加顺序存储
        stus2.sort(new IdShengAgeJiangOrder());
        //♥Collections.sort(stus2, new IdShengAgeJiangOrder());
        for(int i = 0; i < stus2.size(); i++){
            Student s = stus2.get(i);
            System.out.println(s.name+","+s.id+","+s.age);
        }

        System.out.println("第三条打印");
        stu1 = new Student("A", 4, 40);
        stu2 = new Student("B", 4, 18);
        stu3 = new Student("C", 4, 34);
        stu4 = new Student("D", 4, 89);
        stu5 = new Student("E", 4, 5);


        /**TreeMap的3种排序写法---------------------------------------------------------*/
        //5个Student的TreeMap用new时，new时用lambda表达式作为比较器((a,b) -> (a.id - b.id)
        //缺点，若id相同则只能存储一个，不能再根据age排序
        //♥TreeMap<Student, String> stus3 = new TreeMap<>((a,b) -> (a.id - b.id));

        //5个Student的数组，用IdSheng……排序，打印
        //♥TreeMap<Student, String> stus3 = new TreeMap<>(new IdShengAgeJiangOrder());

        //直接在参数部分实现匿名类重写比较器
        /**♥*/
        TreeMap<Student, String> stus3 = new TreeMap<>(new Comparator<Student> (){
            @Override
            public int compare(Student o1, Student o2) {
                return o1.id != o1.id ? o1.id - o2.id : o1.age - o2.age;
            }
        });
        stus3.put(stu1, "我是学生1,我的名字叫A");
        stus3.put(stu2, "我是学生2,我的名字叫B");
        stus3.put(stu3, "我是学生3,我的名字叫C");
        stus3.put(stu4, "我是学生4,我的名字叫D");
        stus3.put(stu5, "我是学生5,我的名字叫E");
        for(Student s : stus3.keySet()){
            System.out.println(s.name+","+s.id+","+s.age);
        }

    }
}
