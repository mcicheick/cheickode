package models;

import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        /*
        11000
        11000
        00100
        00011
         */
        char[][] grid = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };

        System.out.println(solution.numIslands(grid));
    }

    public boolean isValidSudoku(char[][] board) {
        int n = board.length;
        for (int i = 0; i < n; i++) {
            boolean[] row = new boolean[n];
            boolean[] col = new boolean[n];
            boolean[] sub = new boolean[n];
            for (int j = 0; j < n; j++) {
                int ir = (3 * i) % 9 + (j / 3);
                int ic = 3 * (i / 3) + j % 3;
                char r = board[i][j];
                char c = board[j][i];
                char s = board[ir][ic];
                if (r != '.') {
                    int ri = r - '0';
                    if (row[ri - 1]) {
                        return false;
                    } else {
                        row[ri - 1] = !row[ri - 1];
                    }
                }
                if (c != '.') {
                    int ci = c - '0';
                    if (col[ci - 1]) {
                        return false;
                    } else {
                        col[ci - 1] = !col[ci - 1];
                    }
                }

                if (s != '.') {
                    int si = s - '0';
                    if (sub[si - 1]) {
                        return false;
                    } else {
                        sub[si - 1] = !sub[si - 1];
                    }
                }
            }
        }
        return true;
    }

    public void moveZeroes(int[] nums) {
        int copyNums[] = new int[nums.length];
        int zeroCount = 0;
        int numberCount = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                copyNums[nums.length - ++zeroCount] = 0;
            } else {
                copyNums[numberCount++] = nums[i];
            }
        }

        for (int i = 0; i < nums.length; i++) {
            nums[i] = copyNums[i];
        }
    }

    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }

    public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int profit = 0;
        int min = prices[0];
        for (int i = 0; i < prices.length; i++) {
            min = Math.min(min, prices[i]);
            profit = Math.max(profit, prices[i] - min);
        }

        return profit;
    }

    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (!set.add(nums[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] all = new int[255];
        for (int i = 0; i < s.length(); i++) {
            all[s.charAt(i)]++;
            all[t.charAt(i)]--;
        }
        for (int i = 0; i < t.length(); i++) {
            if (all[s.charAt(i)] != 0) {
                return false;
            }
            if (all[t.charAt(i)] != 0) {
                return false;
            }
        }
        return true;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> preorder = new ArrayList<Integer>();
        if (root == null) {
            return preorder;
        }
        LinkedList<TreeNode> tampon = new LinkedList<TreeNode>();
        tampon.add(root);
        while (!tampon.isEmpty()) {
            TreeNode node = tampon.pop();
            preorder.add(node.val);
            TreeNode left = node.left;
            TreeNode right = node.right;
            if (right != null) {
                tampon.push(right);
            }
            if (left != null) {
                tampon.push(left);
            }
        }
        return preorder;
    }

    public int titleToNumber(String s) {
        int number = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            int value = (s.charAt(i) - 'A' + 1);
            number += value * Math.pow(26, n - i - 1);
        }
        return number;
    }

    public int[] countBits(int num) {
        int[] bitsCount = new int[num + 1];
        bitsCount[0] = 0;
        int k;
        int j = 1;
        int i;
        while (j <= num) {
            k = (int) (Math.log(j) / Math.log(2));
            i = j % (int) (Math.pow(2, k));
            bitsCount[j] = bitsCount[i] + 1;
            j++;
        }
        return bitsCount;
    }

    public List<Integer> topKFrequent(int[] nums, int k) {
        List<Integer> frequents = new ArrayList<>();
        Map<Integer, Integer> countsMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer count = countsMap.get(nums[i]);
            if (count == null) {
                count = 0;
            }
            countsMap.put(nums[i], count + 1);
        }
        Set<Map.Entry<Integer, Integer>> entrySet = countsMap.entrySet();

        List<Map.Entry<Integer, Integer>> keys = new ArrayList<>();
        if (k < 1 || k > entrySet.size()) {
            return frequents;
        }
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            keys.add(entry);
        }
        Collections.sort(keys, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }
        });
        for (int i = 0; i < k; i++) {
            frequents.add(keys.get(i).getKey());
        }

        return frequents;
    }

    public int integerBreak(int n) {
        int[] memoize = new int[n + 1];
        memoize[0] = 0;
        memoize[1] = 1;
        memoize[2] = 1;
        for (int i = 3; i <= n; i++) {
            memoize[i] = Math.max(Math.max(2 * (i - 2), 2 * memoize[i - 2]), Math.max(3 * memoize[i - 3], 3 * (i - 3)));
        }
        return memoize[n];
    }

    public double myPow(double x, int n) {
        if (n == 0 && x == 0) {
            return 0;
        }
        if (n == 0) {
            return 1;
        }
        if (x == 0) {
            return 0;
        }
        if (n == 1) {
            return x;
        }
        int power2 = 1;
        double p;
        double multiplicative;
        if (n > 0) {
            multiplicative = x;
        } else {
            multiplicative = 1 / x;
        }
        p = multiplicative;
        n = Math.abs(n);
        int k = (int) (Math.log(n) / Math.log(2));
        for (int i = 1; i <= k; i++) {
            p *= p;
            power2 *= 2;
        }
        p *= myPow(multiplicative, n - power2);
        return p;
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null) {
            return q == null;
        }
        if (q == null) {
            return p == null;
        }
        if (p.val != q.val) {
            return false;
        }
        if (!isSameTree(p.left, q.left)) {
            return false;
        }
        if (!isSameTree(p.right, q.right)) {
            return false;
        }
        return true;
    }

    class Cell {
        int x;
        int y;
        char c;
        boolean viewed;

        public Cell(int x, int y, char c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }

        void markNeighbours(Cell[][] map) {
            viewed = true;
            if (y - 1 >= 0 && !map[x][y - 1].viewed && map[x][y - 1].c == '1') {
                map[x][y - 1].markNeighbours(map);
            }
            if (y + 1 < map[x].length && !map[x][y + 1].viewed && map[x][y + 1].c == '1') {
                map[x][y + 1].markNeighbours(map);
            }
            if (x - 1 >= 0 && !map[x - 1][y].viewed && map[x - 1][y].c == '1') {
                map[x - 1][y].markNeighbours(map);
            }
            if (x + 1 < map.length && !map[x + 1][y].viewed && map[x + 1][y].c == '1') {
                map[x + 1][y].markNeighbours(map);
            }
        }
    }

    public int numIslands(char[][] grid) {
        if (grid.length == 0) {
            return 0;
        }
        List<Cell> lands = new ArrayList<>();
        Cell[][] map = new Cell[grid.length][grid[0].length];
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                Cell cell = new Cell(x, y, grid[x][y]);
                map[x][y] = cell;
                if (cell.c == '1') {
                    lands.add(cell);
                }
            }
        }
        int count = 0;
        for (int i = 0; i < lands.size(); i++) {
            Cell cell = lands.get(i);
            if (!cell.viewed) {
                count++;
                cell.markNeighbours(map);
            }
        }
        return count;
    }
}