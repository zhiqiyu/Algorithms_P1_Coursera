import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
    
        int count = 0;
        for (String i: rq) {
            if (count == k) {
                break;
            }
            System.out.println(i);
            count ++;
        }
    }
}