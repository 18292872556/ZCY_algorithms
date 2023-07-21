package class04;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 雪雪子
 * @Date: 2023/07/20/21:30
 * @Description: // 即堆里实现一个resign方法查找改过的数在反向表中的位置，然后调整堆。做小根堆
 * <p>
 * list3.remove(0);//移除索引为0的元素
 * list1.add(“b”);//添加元素，第一个添加的元素索引下标0
 * list3.set(3,333);//改索引3位置元素为333
 * list1.add(1,“iu”);//在指定索引位置1，插入元素"iu"
 * System.out.println(list1.get(2));//通过索引来访问指定位置的集合元素
 * System.out.println(list3.size());// 返回集合长度
 * System.out.println(list1);//打印集合
 *
 * map1.put(“b”,1);//添加数据
 * System.out.println(map1.get(“b”));//根据key取值
 * map1.remove(“c”);//根据key移除键值对
 * System.out.println(map1.containsKey(“h”));//判断当前的map集合是否包含指定的key
 * System.out.println(map1.size());//map集合的长度
 */
public class Code06_HeapGreater<T> {
//四个私有属性，一个数组作为堆heap，因为长度会动态改变，会一直加数，所以用ArrayList创建数组
//一个哈希表indexMap，作为映射关系记录。记录每一个入堆的对象保存在哪个坐标上
//一个整型heapSize，确定哈希表长度
//一个比较器类型的对象comp，可以比较的类型[T，无穷大)只允许泛型T及T父类的引用调用
    private ArrayList<T> heap;
    private HashMap<T, Integer> indexMap;
    private int heapSize;
    private Comparator<? super T> comp;

    public Code06_HeapGreater(Comparator<? super T> c) {
        //初始化四个属性
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        comp = c;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int size() {
        return heapSize;
    }

    //根据key查找是否存在该对象
    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }
    //返回堆顶
    public T peek() {
        return heap.get(0);
    }

    //入堆，同时入反向表，调整堆
    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);

    }
    //弹数，
    // 保存头，交换堆数组的头尾，从反向表里删除该数的映射对，堆size--，下沉调整堆，返回保存的原数组头数
    public T pop() {
       T ans = heap.get(0);//是即将弹出的数
       swap(0, heapSize - 1);//交换头尾，则尾数来到头，头数来到尾只需长度-1即可
       indexMap.remove(ans);
       heap.remove(--heapSize);//从堆中删除尾数
       heapify(0);//调整堆头
       return ans;
    }
 //删除堆中某个对象
//保存堆数组尾数，保存反向表该参数的坐标值，remove反向表的该对象
//堆remove尾数size--，如果参数不是堆尾对象（如果是，说明已经在堆里和反向表中删除了），
//在该原参数坐标填入尾数，并入表这对映射关系，调整堆resign
    public void remove(T obj) {
        T replace = heap.get(heapSize - 1);//保存尾部对象，用来取代要删除的对象位置
        int index = indexMap.get(obj);//找到要删除的对象坐标
        indexMap.remove(obj);//先从表中删除
        heap.remove(--heapSize);//删除尾部数且堆长度减1，随后用提前保存下来的尾部对象在堆中替代要删除的对象即可
        if(replace != obj){//如果不等的话，刚才已经删掉了，因为是尾数也不用调整堆
            heap.set(index, replace);//在原数坐标位置填入尾对象
            indexMap.put(replace, index);//刷新尾数在表中的新坐标
            resign(replace);//调整该对象在堆中的位置
        }
    }
    //调用两个调整堆方法，上升或下下沉但只会执行一个
    public void resign(T obj) {//这两个方法必然只走一个，因为这个数要么比原来的大，要么小。只能要么向上，要么向下
        heapInsert(indexMap.get(obj));//找到该对象的坐标传入调整
        heapify(indexMap.get(obj));
    }

    // 请返回堆上的所有元素，创建一个新list，然后添加堆中元素返回
    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for(T c : heap){
            ans.add(c);
        }
        return ans;
    }
    //上升调整堆，用comp.compare方法比较父子大小。
    //比较自己与母节点谁大(若存在)，自己大就上移，一直到不满足
    // 暂时不用改表，因为这里只用swap交换堆中元素，而swap里交换堆时，也会改表
    private void heapInsert(int index) {//为啥不用考虑(index - 1) / 2不合法呢？最小的index，-0,5会自动整型0，（0,0）跳出循环
        while(comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0){//这里考虑怎么看是大根堆还是小根堆，因为不知道compare方法是升序还是降序
            // 如果compare是升序，说明堆应该是小根堆，只有母父节点较大时进入交换。返回负数，恰好参数1子在前较小，进入
            //降序，大根堆，只有母父较小时交换。参数1子在前较大，进入
            swap((index - 1) / 2, index);
            index = (index - 1) / 2;
        }

    }
    //下沉调整堆，左孩子是否存在，若存在，找到两个孩子里最大的跟母结点比较，若更大上移交换（大根堆）循环结束到没有左孩子或者自己更大break
    private void heapify(int index) {
        int left = index * 2 + 1;
        while(left < heapSize){
            //考虑比较器降序大根堆，比较器升序小根堆的情况
            //用best不用largest就是因为两种情况，不能确定是哪种堆
            //若comp升序，则为小根堆，较小为best。若comp降序，则为大根堆，较大为best。
            //先定义best为俩孩子里较大的，注意判断右孩子是否存在
            int best = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? left + 1: left;
            //再让best等于与母结点里更best的
            best = comp.compare(heap.get(index), heap.get(best)) < 0 ? index : best;
            if(best == index){//母跟best孩子比较，若母更best则停止下沉
                //如果是升序，best应该更小，返回负数进入，说明index排在前更小，成立
                break;
            }
            swap(index, best);//说明大孩子更大，下沉交换。或者小孩子更小，下沉
            index = best;//下一轮循环
            left = index * 2 + 1;
        }

    }

    //取数ij位置的值，交换堆的同时，更改反向表用put
    private void swap(int i, int j) {
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);

        indexMap.put(o1, j);
        indexMap.put(o2, i);
    }

}


