package com.sardine.algorithm;

public class QuickSort {

    /**
     * 我们选择第一个元素作为枢轴，然后用两个指针i和j分别从左右两边开始扫描数组，交换左右两边的元素，直到i和j相遇为止。
     * 最后把枢轴放到正确的位置上，再分别对左右两边的元素进行快速排序，递归执行即可。
     */
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // 选择第一个元素作为枢轴
            int pivot = arr[low];
            int i = low;
            int j = high;
            while (i < j) {
                // 从右边开始找比枢轴小的元素
                while (i < j && arr[j] >= pivot) {
                    j--;
                }
                // 把找到的元素放到左边
                arr[i] = arr[j];
                // 从左边开始找比枢轴大的元素
                while (i < j && arr[i] <= pivot) {
                    i++;
                }
                // 把找到的元素放到右边
                arr[j] = arr[i];
            }
            // 把枢轴放到正确的位置
            arr[i] = pivot;
            // 对左边的元素进行快速排序
            quickSort(arr, low, i - 1);
            // 对右边的元素进行快速排序
            quickSort(arr, i + 1, high);
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 6, 4};
        quickSort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}