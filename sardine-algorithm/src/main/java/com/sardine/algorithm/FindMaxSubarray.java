package com.sardine.algorithm;

import java.util.Arrays;

/**
 * 动态规划
 * 给定一个数组，求最大子串和以及对应的子串
 */
public class FindMaxSubarray {

    public static int[] findMaxSubarray(int[] arr) {
        if (arr == null || arr.length == 0){
            return new int[]{0};
        }

        int maxSum = arr[0], currentSum = arr[0];
        int start = 0, end, maxStart = 0, maxEnd = 0;

        // 需要求得: 最大子串开始下标和结束下标 即: maxStart, maxEnd.
        for (int i = 1; i < arr.length; i++) {
            if (currentSum + arr[i] < arr[i]) {
                currentSum = arr[i];
                start = i;
            } else {
                currentSum += arr[i];
            }
            end = i;

            if (currentSum > maxSum) {
                maxSum = currentSum;
                maxStart = start;
                maxEnd = end;
            }
        }

        int[] maxSubarray = new int[maxEnd - maxStart + 1];
        System.arraycopy(arr, maxStart, maxSubarray, 0, maxSubarray.length);

        return maxSubarray;
    }

    public static void main(String[] args) {
        int[] arr = {2, -5, 3, 6, -2, 4, -3, 2, -1, 5, -4};
        int[] maxSubarray = findMaxSubarray(arr);
        int maxSum = 0;
        for (int num : maxSubarray){
            maxSum += num;
        }

        System.out.println("Maximum Subarray: " + Arrays.toString(maxSubarray));
        System.out.println("Maximum Subarray Sum: " + maxSum);
    }
}
