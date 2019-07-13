package com.minliu.demo.java8demos;


import java.util.Arrays;

/**
 * ClassName: Demo <br>
 * date: 3:08 PM 02/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */

public class Demo {
    public static void main(String[] args) {
        int[] arr = {1, 5, 12, 3, 2, 5, 12, 98, 25, 13, 43, 1, 9};
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void quickSort(int[] arr) {
        if (arr != null && arr.length >= 2) {
            quickSort(arr, 0, arr.length - 1);
        }
    }

    private static void quickSort(int[] arr, int L, int R) {
        int[] ints = partition(arr, L, R);
        partition(arr, L, ints[0] - 1);
        partition(arr, ints[1] + 1, R);
    }

    private static int[] partition(int[] arr, int L, int R) {
        int basic = arr[R];
        int less = L - 1;
        int more = R + 1;
        while (L < more) {
            if (arr[L] < basic) {
                swap(arr, ++less, L++);
            } else if (arr[L] > basic) {
                swap(arr, --more, L);
            } else {
                L++;
            }
        }
        return new int[]{less + 1, more - 1};
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[j];
        arr[j] = arr[i];
        arr[i] = tmp;
    }
}
