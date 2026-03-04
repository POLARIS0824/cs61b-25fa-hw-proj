import java.util.*;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int capacity;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        capacity = 8;
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        if (size == capacity) {
            resizeUp();
        }
        items[nextFirst] = x;
        nextFirst = (nextFirst - 1 + capacity) % capacity;
        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        if (size == capacity) {
            resizeUp();
        }
        items[nextLast] = x;
        nextLast = (nextLast + 1) % capacity;
        size++;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(get(i));
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        if (capacity >= 16 && size * 4 <= capacity) {
            resizeDown();
        }

        int actualIndex = (nextFirst + 1) % capacity;
        T returnValue = items[actualIndex];
        items[actualIndex] = null;
        nextFirst = actualIndex;
        size--;
        return returnValue;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        if (capacity >= 16 && size * 4 <= capacity) {
            resizeDown();
        }

        int actualIndex = (nextLast - 1 + capacity) % capacity;
        T returnValue = items[actualIndex];
        items[actualIndex] = null;
        nextLast = actualIndex;
        size--;
        return returnValue;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(nextFirst + index + 1) % capacity];
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

    /**
     * Resizing Up method
     */
    private void resizeUp() {
        T[] newItems = (T[]) new Object[capacity * 2];
        copyHelper(newItems);
        capacity *= 2;
        nextFirst = capacity - 1;
        nextLast = size;
        items = newItems;
    }

    /**
     * Resizing Down method
     */
    private void resizeDown() {
        T[]  newItems = (T[]) new Object[capacity / 2];
        copyHelper(newItems);
        capacity /= 2;
        nextFirst = capacity - 1;
        nextLast = size;
        items = newItems;
    }

    private void copyHelper(T[] newItems) {
        for (int i = 0; i < size; i++) {
            newItems[i] = get(i);
        }
    }

    // 最好和 get 解耦
    private class DequeIterator implements Iterator<T> {
        private int wizPos; // 当前迭代到的位置
        private int count;  // 已经迭代的个数

        public DequeIterator() {
            wizPos = (nextFirst + 1) % capacity;
            count = 0;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return count < size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T returnItem = items[wizPos];
            wizPos = (wizPos + 1) % capacity;
            count++;
            return returnItem;
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Deque61B<?> otherObject) {
            if (this.size() != otherObject.size()) {
                return false;
            }
            Iterator<?> otherIterator = otherObject.iterator();
            for (T x : this) {
                if (!Objects.equals(x, otherIterator.next())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(get(i));
            if (i != size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
