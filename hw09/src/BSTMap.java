import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private int size;
    private Node root;

    private class Node {
        K key;
        V value;
        Node left;
        Node right;

        Node (K key, V value, Node left, Node right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }
        root = putHelper(key, value, root);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        return getHelper(key, root);
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        if  (key == null) {
            throw new NullPointerException();
        }
        return containsKeyHelper(key, root);
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        Set<K> set = new LinkedHashSet<>();
        inOrder(root, set);
        return set;
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new NullPointerException();
        }

        if (!containsKey(key)) {
            return null;
        }

        V value = get(key);
        root = removeHelper(key, root);
        size--;

        return value;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {
        private Deque<Node> stack = new ArrayDeque<>();

        BSTMapIterator() {
            pushLeft(root);
        }

        private void pushLeft(Node node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
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
            return !stack.isEmpty();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public K next() {
            Node node = stack.pop(); // Left Node
            pushLeft(node.right);
            return node.key;
        }
    }

    // Helper Methods

    private Node putHelper(K key, V value, Node current) {
        if (current == null) {
            size++;
            return new Node(key, value, null, null);
        }

        int cmp = key.compareTo(current.key);

        if (cmp < 0) {
            current.left = putHelper(key, value, current.left);
        } else if (cmp > 0) {
            current.right = putHelper(key, value, current.right);
        } else {
            current.value = value;
        }

        return current;
    }

    private V getHelper(K key, Node current) {
        if (current == null) {
            return null;
        }

        int cmp = key.compareTo(current.key);

        if (cmp < 0) {
            return getHelper(key, current.left);
        } else if (cmp > 0) {
            return getHelper(key, current.right);
        } else {
            return current.value;
        }
    }

    private boolean containsKeyHelper(K key, Node current) {
        if (current == null) {
            return false;
        }

        int cmp = key.compareTo(current.key);

        if (cmp < 0) {
            return containsKeyHelper(key, current.left);
        } else if (cmp > 0) {
            return containsKeyHelper(key, current.right);
        } else {
            return true;
        }
    }

    // PreOrder: Root -> Left -> Right
    private void preOrder(Node current, Set<K> set) {
        if (current == null) {
            return;
        }

        set.add(current.key);
        preOrder(current.left, set);
        preOrder(current.right, set);
    }

    // InOrder: Left -> Root -> Right
    private void inOrder(Node current, Set<K> set) {
        if (current == null) {
            return;
        }

        inOrder(current.left, set);
        set.add(current.key);
        inOrder(current.right, set);
    }

    // PostOrder: Left -> Right -> Root
    private void postOrder(Node current, Set<K> set) {
        if (current == null) {
            return;
        }

        postOrder(current.left, set);
        postOrder(current.right, set);
        set.add(current.key);
    }

    private Node removeHelper(K key, Node current) {
        if (current == null) {
            return null;
        }

        int cmp = key.compareTo(current.key);

        if (cmp < 0) {
            current.left = removeHelper(key, current.left);
        } else if (cmp > 0) {
            current.right = removeHelper(key, current.right);
        } else {
            // Only Left Or Right
            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }

            // Has Two SubNodes
            Node successor = findMax(current.left);
            current.key = successor.key;
            current.value = successor.value;
            current.left = removeHelper(successor.key, current.left);
        }
        return current;
    }

    private Node findMax(Node current) {
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }
}
