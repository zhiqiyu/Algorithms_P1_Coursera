import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[2];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    private void resize(int capacity) {
        Item[] new_arr = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i ++) {
            new_arr[i] = arr[i];   // straighten warpped around array
        }
        arr = new_arr;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == arr.length) {
            resize(2*n);
        }
        arr[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Queue underflow");
        }
        int rand = StdRandom.uniform(n);
        Item out = arr[rand];
        if (rand == n-1) {
            arr[rand] = null;
        }
        else {
            Item tail = arr[n-1];
            arr[n-1] = null;
            arr[rand] = tail;
        }
        n--;
        if (n <= arr.length/4.0 && n > 0) {
            resize(n/2);
        }
        return out;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Queue underflow");
        }
        int rand = StdRandom.uniform(n);
        return arr[rand];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private final int[] perm;
        private int cur_ind = 0;

        public RandomIterator() {
            perm = StdRandom.permutation(n);
        }

        public boolean hasNext() {
            return cur_ind < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return arr[perm[cur_ind++]];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        rq.enqueue("a");
        rq.enqueue("b");
        rq.enqueue("c");
        rq.enqueue("d");
        rq.enqueue("e");
        System.out.println(rq.size());
        for (String i : rq) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (String i : rq) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("first sample: " + rq.sample());
        System.out.println("second sample: " + rq.sample());
        rq.dequeue();
        rq.dequeue();
        System.out.println(rq.size());
        for (String i : rq) {
            System.out.print(i + " ");
        }
    }

}