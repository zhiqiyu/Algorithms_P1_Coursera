import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int n;
    private double[] thresholds;
    private double mean;
    private double stddev;
    private final double CONFIDENCE_95 = 1.96;

    public PercolationStats(int num, int trials) {
        if (num <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials should be greater than 0.");
        }

        n = num;
        thresholds = new double[trials];

        for(int i=0; i<trials; i++){
            thresholds[i] = oneTrial();
        }

        mean = mean();
        stddev = stddev();

    }   

    private double oneTrial(){
        Percolation p = new Percolation(n);
        double[] distribution = new double[n];
        for(int i=0; i<n; i++) distribution[i] = 1.0/n;
        while(!p.percolates()){
            int row = StdRandom.discrete(distribution) + 1;
            int col = StdRandom.discrete(distribution) + 1;
            p.open(row, col);
        }
        return p.numberOfOpenSites()/(double)(n*n);
    }

    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(thresholds);
    }                         
    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(thresholds);
    }                       
    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return mean - CONFIDENCE_95*stddev/Math.sqrt(thresholds.length);
    }                 
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return mean + CONFIDENCE_95*stddev/Math.sqrt(thresholds.length);
    }                 
 
    public static void main(String[] args) {
        // test client (described below)
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats tests = new PercolationStats(n, t);
        String output = "mean\t="+tests.mean()+"\n"+
                        "stddev\t="+tests.stddev()+"\n"+
                        "95% confidence interval\t=["+tests.confidenceLo()+", "+tests.confidenceHi()+"]\n";
        System.out.print(output);
    }       
 }