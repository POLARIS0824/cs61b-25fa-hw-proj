import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

public class Stack {
    private static class IntNode {
        private int item;
        private IntNode next;
        private IntNode(int item, IntNode next) {
            this.item = item;
            this.next = next;
        }
    }

    private IntNode sentinel;
    private int size;
    private int sum;

    public Stack() {
        sentinel = new IntNode(-1, null);
        size = 0;
        sum = 0;
    }

    public Stack(int x) {
        sentinel = new IntNode(-1, null);
        sentinel.next = new IntNode(x, null);
        size = 1;
        sum = x;
    }

    public void push(int x) {
        size++;
        sum += x;
        sentinel.next = new IntNode(x, sentinel.next);
    }

    public int pop() {
        if (size == 0) {
            throw new EmptyStackException();
        } else {
            size--;
            int temp = sentinel.next.item; // top of the stack
            sum -= temp;
            sentinel.next = sentinel.next.next;
            return temp;
        }
    }

    public int size() {
        return size;
    }

    public int sum() {
        return sum;
    }
}
