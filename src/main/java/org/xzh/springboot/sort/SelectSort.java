package org.xzh.springboot.sort;


/**
 * 选择排序
 * 选择排序可以说是最简单的一种排序方法：
 * 1.找到数组中最小的那个元素
 * 2.将最小的这个元素和数组中第一个元素交换位置
 * 3.在剩下的元素中找到最小的的元素，与数组第二个元素交换位置
 * 重复以上步骤，即可以得到有序数组。
 *
 */
public class SelectSort {

	public static int[] sort(int[] src){
		if(src == null || src.length == 0){
			return src;
		}
		
		int min = 0;
		for(int i=0; i<src.length; i++){
			min = i;
			for(int j = i+1; j<src.length; j++){
				//每次遍历比较，找出最小（或者最大）下标
				if(src[min] > src[j]){
					min = j;
				}
			}
			
			//如果遍历找到比第一位更小的数，调换位置
			if(i != min){
				int tem = src[i];
				src[i] = src[min];
				src[min] = tem;
			}
		}
		
		return src;
	}
	
	public static void main(String[] args) {
		int[] src = {12,34,11,6,4,8,444,23,8,2,6,434,4,66,0,-3,-997};
		//Arrays.asList(sort(src)).forEach(a -> {System.out.print(a+",");});
		int[] dest = sort(src);
		for(int s : dest){
			System.out.print(s+" ");
		}
	}
}
