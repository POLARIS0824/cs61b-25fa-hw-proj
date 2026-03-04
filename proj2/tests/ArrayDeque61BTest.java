import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class ArrayDeque61BTest {

    @Test
    public void addFirstTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addFirst(1);
        assertThat(deque.toList()).containsExactly(1).inOrder();
        deque.addFirst(2);
        assertThat(deque.toList()).containsExactly(2, 1).inOrder();
        deque.addFirst(3);
        assertThat(deque.toList()).containsExactly(3, 2, 1).inOrder();
    }

    @Test
    public void addLastTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addLast(1);
        assertThat(deque.toList()).containsExactly(1).inOrder();
        deque.addLast(2);
        assertThat(deque.toList()).containsExactly(1, 2).inOrder();
        deque.addLast(3);
        assertThat(deque.toList()).containsExactly(1, 2, 3).inOrder();
    }

    @Test
    public void addFirstAndGetTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addFirst(1); // [1, ...]
        deque.addFirst(2); // [2, 1, ...]
        deque.addFirst(3); // [3, 2, 1, ...]
        assertThat(deque.get(0)).isEqualTo(3);
        assertThat(deque.get(1)).isEqualTo(2);
        assertThat(deque.get(2)).isEqualTo(1);
        assertThat(deque.get(5)).isNull();
        assertThat(deque.get(-1)).isNull();
    }

    @Test
    public void addLastAndGetTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addLast(1); // [1, ...]
        deque.addLast(2); // [1, 2, ...]
        deque.addLast(3); // [1, 2, 3, ...]
        assertThat(deque.get(0)).isEqualTo(1);
        assertThat(deque.get(1)).isEqualTo(2);
        assertThat(deque.get(2)).isEqualTo(3);
        assertThat(deque.get(5)).isNull();
        assertThat(deque.get(-1)).isNull();
    }

    @Test
    public void randomAddAndGetTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addFirst(1);  // [1, ...]
        deque.addLast(2);   // [1, 2, ...]
        deque.addFirst(0);  // [0, 1, 2, ...]
        deque.addFirst(-1); // [-1, 0, 1, 2, ...]
        deque.addLast(3);   // [-1, 0, 1, 2, 3, ...]
        assertThat(deque.get(0)).isEqualTo(-1);
        assertThat(deque.get(1)).isEqualTo(0);
        assertThat(deque.get(2)).isEqualTo(1);
        assertThat(deque.get(3)).isEqualTo(2);
        assertThat(deque.get(4)).isEqualTo(3);
        assertThat(deque.get(5)).isNull();
        assertThat(deque.get(-1)).isNull();
    }

    @Test
    public void isEmptyTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        assertThat(deque.isEmpty()).isTrue();
        deque.addFirst(1);
        assertThat(deque.isEmpty()).isFalse();
    }

    @Test
    public void sizeTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addFirst(1);  // [1, ...]
        deque.addLast(2);   // [1, 2, ...]
        deque.addFirst(0);  // [0, 1, 2, ...]
        assertThat(deque.size()).isEqualTo(3);
        deque.addFirst(-1); // [-1, 0, 1, 2, ...]
        deque.addLast(3);   // [-1, 0, 1, 2, 3, ...]
        assertThat(deque.size()).isEqualTo(5);
    }

    @Test
    public void toListTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addFirst(1);  // [1, ...]
        deque.addLast(2);   // [1, 2, ...]
        deque.addFirst(0);  // [0, 1, 2, ...]
        deque.addFirst(-1); // [-1, 0, 1, 2, ...]
        deque.addLast(3);   // [-1, 0, 1, 2, 3, ...]
        assertThat(deque.toList()).containsExactly(-1, 0, 1, 2, 3).inOrder();
    }

    @Test
    public void removeFirstTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addFirst(1);  // [1, ...]
        deque.addLast(2);   // [1, 2, ...]
        deque.addFirst(0);  // [0, 1, 2, ...]
        deque.addFirst(-1); // [-1, 0, 1, 2, ...]
        deque.addLast(3);   // [-1, 0, 1, 2, 3, ...]
        assertThat(deque.removeFirst()).isEqualTo(-1);
        assertThat(deque.removeFirst()).isEqualTo(0);
        assertThat(deque.toList()).containsExactly(1, 2, 3).inOrder();
    }

    @Test
    public void removeLastTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addFirst(1);  // [1, ...]
        deque.addLast(2);   // [1, 2, ...]
        deque.addFirst(0);  // [0, 1, 2, ...]
        deque.addFirst(-1); // [-1, 0, 1, 2, ...]
        deque.addLast(3);   // [-1, 0, 1, 2, 3, ...]
        assertThat(deque.removeLast()).isEqualTo(3);
        assertThat(deque.removeLast()).isEqualTo(2);
        assertThat(deque.toList()).containsExactly(-1, 0, 1).inOrder();
    }

    @Test
    public void resizeUpTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        int testSize = 100;
        for (int i = 0; i < testSize; i++) {
            deque.addLast(i);
        }

        assertThat(deque.size()).isEqualTo(testSize);
        for (int i = 0; i < testSize; i++) {
            assertThat(deque.get(i)).isEqualTo(i);
        }
        for (int i = 0; i < testSize; i++) {
            assertThat(deque.removeFirst()).isEqualTo(i);
        }
    }

    @Test
    public void resizeDownTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        int testSize = 100;
        for (int i = 0; i < testSize; i++) {
            deque.addLast(i);
        }

        assertThat(deque.size()).isEqualTo(testSize);
        for (int i = 0; i < testSize; i++) {
            deque.removeFirst();
        }
    }

    @Test
    @DisplayName("Verify physical capacity reduction using reflection")
    public void verifyCapacityReduction() throws Exception {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        // 1. 填充数据触发扩容
        for (int i = 0; i < 64; i++) deque.addLast(i);

        // 2. 通过反射获取私有数组的长度
        java.lang.reflect.Field field = deque.getClass().getDeclaredField("items");
        field.setAccessible(true);
        int capacityAfterAdd = ((Object[]) field.get(deque)).length;

        // 3. 大量删除触发缩容
        for (int i = 0; i < 60; i++) deque.removeFirst();

        int capacityAfterRemove = ((Object[]) field.get(deque)).length;

        // 4. 断言：物理容量确实缩小了
        assertThat(capacityAfterRemove).isLessThan(capacityAfterAdd);
    }

    @Test
    public void iterateTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        // 1. 测试空容器：迭代器不应有内容
        assertThat(deque).isEmpty();
        for (Integer x : deque) {
            fail("Empty deque should not have any elements to iterate");
        }

        // 2. 测试循环折返场景：先添加一些再删除，使 nextFirst 处于数组中间
        // 假设初始容量为 8
        for (int i = 0; i < 6; i++) deque.addLast(i);    // [0, 1, 2, 3, 4, 5]
        for (int i = 0; i < 4; i++) deque.removeFirst(); // 剩余 [4, 5]
        for (int i = 6; i < 12; i++) deque.addLast(i);   // 触发扩容或折返 [4, 5, 6, 7, 8, 9, 10, 11]

        // 预期结果序列
        List<Integer> expected = List.of(4, 5, 6, 7, 8, 9, 10, 11);
        List<Integer> actual = new ArrayList<>();

        // 3. 验证增强型 for 循环（即验证 Iterator 接口实现）
        for (Integer item : deque) {
            actual.add(item);
        }
        assertThat(actual).containsExactlyElementsIn(expected).inOrder();

        // 4. 验证多个迭代器并行（互不干扰）
        // 工业级容器必须支持同时开启两个循环（例如寻找两数之和）
        int pairCount = 0;
        for (Integer x : deque) {
            for (Integer y : deque) {
                pairCount++;
            }
        }
        assertThat(pairCount).isEqualTo(expected.size() * expected.size());
    }

    @Test
    public void equalsTest() {
        ArrayDeque61B<Integer> deque1 = new ArrayDeque61B<>();
        ArrayDeque61B<Integer> deque2 = new ArrayDeque61B<>();

        for (int i = 0; i < 8; i++) {
            deque1.addLast(i);
            deque2.addLast(i);
        }

        assertThat(deque1.equals(deque2)).isTrue();
    }

    @Test
    public void equalsNullTest() {
        ArrayDeque61B<Integer> deque1 = new ArrayDeque61B<>();
        ArrayDeque61B<Integer> deque2 = new ArrayDeque61B<>();

        for (int i = 0; i < 8; i++) {
            deque1.addLast(i);
        }
        deque2.addLast(null);
        assertThat(deque1.equals(deque2)).isFalse();
    }

    @Test
    @DisplayName("toString should return a formatted string in logical order")
    public void toStringTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        // 1. 测试空队列
        // 预期：[]
        assertThat(deque.toString()).isEqualTo("[]");

        // 2. 测试单个元素
        // 预期：[99]
        deque.addLast(99);
        assertThat(deque.toString()).isEqualTo("[99]");

        // 3. 测试多个元素及其顺序
        // 预期：[99, 1, 2, 3]
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertThat(deque.toString()).isEqualTo("[99, 1, 2, 3]");

        // 4. 关键测试：循环数组折返后的顺序
        // 我们先删除前面的元素，再从后面添加，使逻辑头部在物理数组的末尾
        ArrayDeque61B<String> sDeque = new ArrayDeque61B<>();
        // 假设初始容量 8，填满它
        for (int i = 0; i < 8; i++) sDeque.addLast("S" + i);
        // 删除前 7 个
        for (int i = 0; i < 7; i++) sDeque.removeFirst();
        // 此时队列只剩 ["S7"]，且它可能在物理数组的索引 7
        // 再添加几个，它们会折返绕回到物理数组的索引 0, 1...
        sDeque.addLast("A");
        sDeque.addLast("B");

        // 逻辑顺序应该是 [S7, A, B]
        assertThat(sDeque.toString()).isEqualTo("[S7, A, B]");
    }
}
