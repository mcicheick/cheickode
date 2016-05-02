package models;

import java.util.HashSet;

public class Solution {
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
                        col[ci - 1] =! col[ci - 1];
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
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == 0) {
                copyNums[nums.length - ++zeroCount] = 0;
            } else {
                copyNums[numberCount++] = nums[i];
            }
        }

        for(int i = 0; i < nums.length; i++) {
            nums[i] = copyNums[i];
        }
    }

    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }

    public int maxProfit(int[] prices) {
        if(prices.length == 0) {
            return 0;
        }
        int profit = 0;
        int min = prices[0];
        for(int i = 0; i < prices.length; i++) {
            min = Math.min(min, prices[i]);
            profit = Math.max(profit, prices[i] - min);
        }

        return profit;
    }

    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < nums.length; i++) {
            if(!set.add(nums[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length()) {
            return false;
        }
        int[] all = new int[255];
        for (int i = 0; i < s.length(); i++) {
            all[s.charAt(i)]++;
            all[t.charAt(i)]--;
        }
        for (int i = 0; i < t.length(); i++) {
            if(all[s.charAt(i)] != 0) {
                return false;
            }
            if(all[t.charAt(i)] != 0) {
                return false;
            }
        }
        return true;
    }
}