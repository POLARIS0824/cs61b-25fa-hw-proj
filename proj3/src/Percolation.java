import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.security.InvalidParameterException;

public class Percolation {
    // TODO: Add any necessary instance variables.
    private boolean[][] isOpen;  // use for isOpen
    private int n;
    private int virtualTop;
    private int virtualBottom;
    private int openCount;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufIsFull;

    private int[] dx = {-1, 1, 0, 0};
    private int[] dy = {0, 0, -1, 1};

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        if (N <= 0) throw new IllegalArgumentException();

        isOpen = new boolean[N][N];
        n = N;
        virtualTop = n * n;
        virtualBottom = n * n + 1;
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufIsFull = new WeightedQuickUnionUF(n * n + 1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                isOpen[i][j] = false;  // not open
            }
        }
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        if (!isValid(row, col)) {
            throw new InvalidParameterException();
        }
        if (isOpen[row][col]) {
            return;
        }
        isOpen[row][col] = true;
        openCount++;

        int currentIndex = xyTo1D(row, col);
        for (int i = 0; i < 4; i++) {
            int nx = row + dx[i];
            int ny = col + dy[i];
            if (!isValid(nx, ny)) {
                continue;
            }
            if (isOpen[nx][ny]) {
                int neighborIndex = xyTo1D(nx, ny);
                uf.union(neighborIndex, currentIndex);
                ufIsFull.union(neighborIndex, currentIndex);
            }
        }

        if (row == 0) {
            uf.union(virtualTop, currentIndex);
            ufIsFull.union(virtualTop, currentIndex);
        }
        if (row == n - 1) {
            uf.union(virtualBottom, currentIndex);
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        if (!isValid(row, col)) {
            throw new IllegalArgumentException();
        }
        return isOpen[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        if (!isValid(row, col)) {
            throw new IllegalArgumentException();
        }
        return ufIsFull.find(row * n + col) == virtualTop;
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return openCount;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        return uf.find(virtualTop) == uf.find(virtualBottom);
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.
    private int xyTo1D(int row, int col) {
        return row * n + col;
    }

    private boolean isValid(int row, int col) {
        return (row >= 0 && row < n && col >= 0 && col < n);
    }
}
