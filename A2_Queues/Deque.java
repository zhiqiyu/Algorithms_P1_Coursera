import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private DequeNode<Item> head;
    private DequeNode<Item> tail;
    private int count;

    private class DequeNode<T> {
        T item;
        DequeNode<T> next;
        DequeNode<T> before;
        public DequeNode(T t) {
            item = t;
            next = null;
            before = null;
        }
    }

    // construct an empty deque
    public Deque() {
        head = null;
        tail = null;
        count = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            head = new DequeNode<>(item);
            tail = head;
        }
        else {
            DequeNode<Item> new_node = new DequeNode<>(item);
            new_node.next = head;
            head.before = new_node;
            head = new_node;
        }
        count ++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            head = new DequeNode<>(item);
            tail = head;
        }
        else {
            DequeNode<Item> new_node = new DequeNode<>(item);
            tail.next = new_node;
            new_node.before = tail;
            tail = new_node;
        }
        count ++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        DequeNode<Item> old_head = head;
        if (count > 1) {        // more than one node 
            head = head.next;
            head.before = null;         // set the new head's 'before node' as null
        }
        else {
            head = null;
            tail = null;
        }
        count --;
        return old_head.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        DequeNode<Item> old_tail = tail;
        if (count > 1) {        // more than one node 
            tail = tail.before;
            tail.next = null;          // set the new tail's 'next node' as null
        }
        else {
            head = null;
            tail = null;
        }
        count --;
        return old_tail.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private DequeNode<Item> current = head;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item value = current.item;
            current = current.next;
            return value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> dq = new Deque<>();
        dq.addFirst("b");
        dq.removeFirst();
        dq.addFirst("a");
        dq.addLast("c");
        dq.addLast("d");
        dq.addFirst("Alphabetics: ");
        System.out.println(dq.size());
        for (String i : dq) {
            System.out.print(i + " ");
        }
        System.out.println();
        dq.removeFirst();
        dq.removeLast();
        System.out.println(dq.size());
        for (String i : dq) {
            System.out.print(i + " ");
        }
    }

}