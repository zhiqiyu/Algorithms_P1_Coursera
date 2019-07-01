import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF wqu;
    private boolean[] grid;

    public Percolation(int num) {
        // create n-by-n grid, with all sites blocked
        if(num <= 0){
            throw new IllegalArgumentException("n should be greater than 0.");
        }
        
        n = num;
        wqu = new WeightedQuickUnionUF(n*n+2);
        grid = new boolean[n*n+2];
        for(int i=0; i<n*n+2; i++){
            grid[i] = false;
        }
        grid[0] = true;         // open virtual top site 
        grid[n*n+1] = true;     // open virtual bottom site
    }

    private int flattenIndex(int row, int col) {
        // transform row and column index to flattened index
        return (row-1)*n+col; 
    }

    private void connectNeighbors(int row, int col) {
        int target_idx = flattenIndex(row, col);
        if (row == 1) wqu.union(0, target_idx);             // connect virtual top if the opened site is in the top row
        if (row == n) wqu.union(n*n+1, target_idx);         // connect virtual bottom if the opened site is in the bottom row
        if (col-1 >= 1 && grid[flattenIndex(row, col-1)]){  // have opened left neighbor
            wqu.union(target_idx, flattenIndex(row, col-1));
        } 
        if (col+1 <= n && grid[flattenIndex(row, col+1)]){  // have opened right neighbor
            wqu.union(target_idx, flattenIndex(row, col+1));
        }
        if (row-1 >= 1 && grid[flattenIndex(row-1, col)]){  // have opened upper neighbor
            wqu.union(target_idx, flattenIndex(row-1, col));
        }
        if (row+1 <= n && grid[flattenIndex(row+1, col)]){  // have opened lower neighbor
            wqu.union(target_idx, flattenIndex(row+1, col));
        }
    }

    public void open(int row, int col) {
        // open site (row, col) if it is not open already
        if (row<1 || col<1 || row>n || col >n){
            throw new IllegalArgumentException("row and col should be in [1, n]");
        }

        if (!isOpen(row, col)){
            grid[flattenIndex(row, col)] = true;
            connectNeighbors(row, col);
        }

    }

    public boolean isOpen(int row, int col) {
        // is site (row, col) open?
        if (row<1 || col<1 || row>n || col >n){
            throw new IllegalArgumentException("row and col should be in [1, n]");
        }
        return grid[flattenIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        // is site (row, col) full (connectable to top row)?
        if (row<1 || col<1 || row>n || col >n){
            throw new IllegalArgumentException("row and col should be in [1, n]");
        }
        return wqu.connected(0, flattenIndex(row, col));
    }

    public int numberOfOpenSites() {
        // number of open sites
        int count = 0;
        for(boolean i: grid){
            if(i) count++;
        }
        return count-2;
    }

    public boolean percolates() {
        // does the system percolate?
        return wqu.connected(0, n*n+1);
    }

    public static void main(String[] args) {
        // test client (optional)
    }
}