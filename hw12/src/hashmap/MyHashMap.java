package hashmap;

import org.checkerframework.checker.units.qual.C;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int elements;
    private int capacity;
    private double loadFactor;
    private int threshold;
    // You should probably define some more!

    @SuppressWarnings("unchecked")
    /** Constructors */
    public MyHashMap() {
        buckets = (Collection<Node>[]) new Collection[16];
        elements = 0;
        capacity = 16;
        loadFactor = 0.75f;
        threshold = (int) (capacity * loadFactor);

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity) {
        buckets = (Collection<Node>[]) new Collection[initialCapacity];
        elements = 0;
        capacity = initialCapacity;
        loadFactor = 0.75f;
        threshold = (int) (capacity * loadFactor);

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity, double loadFactor) {
        buckets = (Collection<Node>[]) new Collection[initialCapacity];
        elements = 0;
        capacity = initialCapacity;
        this.loadFactor = loadFactor;
        threshold = (int) (capacity * loadFactor);

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
        return new ArrayList<>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

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
        // key will never be null
        int index = Math.floorMod(key.hashCode(), capacity);
        if (buckets[index] == null) {
            buckets[index] = createBucket();
        }

        for (Node element : buckets[index]) {
            if (element.key.equals(key)) {
                element.value = value;
                return;
            }
        }

        buckets[index].add(new Node(key, value));
        elements++;

        if (elements >= threshold) {
            resize();
        }
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Collection<Node>[] newBuckets = (Collection<Node>[]) new Collection[capacity * 2];

        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] == null) {
                continue;
            }

            for (Node element : buckets[i]) {
                int index = Math.floorMod(element.key.hashCode(), capacity * 2);
                if (newBuckets[index] == null) {
                    newBuckets[index] = createBucket();
                }
                newBuckets[index].add(element);
            }
        }

        capacity = capacity * 2;
        threshold = (int) (capacity * loadFactor);
        buckets = newBuckets;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(), capacity);
        if (buckets[index] == null) {
            return null;
        }

        for (Node element : buckets[index]) {
            if (element.key.equals(key)) {
                return element.value;
            }
        }
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        int index = Math.floorMod(key.hashCode(), capacity);
        if (buckets[index] == null) {
            return false;
        }

        for (Node element : buckets[index]) {
            if (element.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return elements;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        buckets = (Collection<Node>[]) new Collection[capacity];
        elements = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] == null) {
                continue;
            }
            for (Node element : buckets[i]) {
                set.add(element.key);
            }
        }
        return set;
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        int index = Math.floorMod(key.hashCode(), capacity);
        // use iterator!
        if (buckets[index] == null) {
            return null;
        }

        Iterator<Node> iterator = buckets[index].iterator();
        while (iterator.hasNext()) {
            Node curNode = iterator.next();
            if (curNode.key.equals(key)) {
                V value = curNode.value;
                iterator.remove();
                elements--;
                return value;
            }
        }
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            private int bucketIndex = 0;
            private int count = 0;
            private Iterator<Node> iterator = null;

            /**
             * Returns {@code true} if the iteration has more elements.
             * (In other words, returns {@code true} if {@link #next} would
             * return an element rather than throwing an exception.)
             *
             * @return {@code true} if the iteration has more elements
             */
            @Override
            public boolean hasNext() {
                return count < elements;
            }

            /**
             * Returns the next element in the iteration.
             *
             * @return the next element in the iteration
             * @throws NoSuchElementException if the iteration has no more elements
             */
            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                while (iterator == null || !iterator.hasNext()) {
                    iterator = buckets[bucketIndex++].iterator();
                }

                Node element = iterator.next();
                count++;
                return element.key;
            }
        };
    }
}
