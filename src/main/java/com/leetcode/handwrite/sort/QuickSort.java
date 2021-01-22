package com.leetcode.handwrite.sort;

import java.lang.reflect.Array;

public class QuickSort {

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static void quickSort1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process1(arr, 0, arr.length - 1);
	}

	public static void process1(int[] arr, int L, int R) {
		if (L >= R) {
			return;
		}
		int M = partition(arr, L, R);
		process1(arr, L, M - 1);
		process1(arr, M + 1, R);
	}

	public static int partition(int[] arr, int L, int R) {
		if (L > R) {
			return -1;
		}
		if (L == R) {
			return L;
		}
		int lessEqual = L - 1;
		int index = L;
		while (index < R) {
			if (arr[index] <= arr[R]) {
				swap(arr, index, ++lessEqual);
			}
			index++;
		}
		swap(arr, ++lessEqual, R);
		return lessEqual;
	}

	public static void quickSort2(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process2(arr, 0, arr.length - 1);
	}

	public static void process2(int[] arr, int L, int R) {
		if (L >= R) {
			return;
		}
		int[] equalArea = netherlandsFlag(arr, L, R);
		process1(arr, L, equalArea[0] - 1);
		process1(arr, equalArea[1] + 1, R);
	}

	public static int[] netherlandsFlag(int[] arr, int L, int R) {
		if (L > R) {
			return new int[] { -1, -1 };
		}
		if (L == R) {
			return new int[] { L, R };
		}
		int less = L - 1;
		int more = R;
		int index = L;
		while (index < more) {
			if (arr[index] == arr[R]) {
				index++;
			} else if (arr[index] < arr[R]) {
				swap(arr, index++, ++less);
			} else {
				swap(arr, index, --more);
			}
		}
		swap(arr, more, R);
		return new int[] { less + 1, more };
	}

	// for test
	public static void main(String[] args) {
		int testTime = 1;
		int maxSize = 10000000;
		int maxValue = 100000;
		boolean succeed = true;
		long T1=0,T2=0;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			int[] arr3 = copyArray(arr1);
//			int[] arr1 = {9,8,7,6,5,4,3,2,1};
			long t1 = System.currentTimeMillis();
			quickSort1(arr1);
			long t2 = System.currentTimeMillis();
			quickSort2(arr2);
			long t3 = System.currentTimeMillis();
			T1 += (t2-t1);
			T2 += (t3-t2);
			if (!isEqual(arr1, arr2) || !isEqual(arr2, arr3)) {
				succeed = false;
				break;
			}
		}
		System.out.println(T1+" "+T2);
//		System.out.println(succeed ? "Nice!" : "Oops!");

	}


	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

}
