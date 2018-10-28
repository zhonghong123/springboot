package org.xzh.springboot.sort;

/**
 * 插入排序
 * 插入排序类似整理扑克牌，将每一张牌插到其他已经有序的牌中适当的位置。
 * 
 * 插入排序由N-1趟排序组成，对于P=1到N-1趟，插入排序保证从位置0到位置P上的元素为已排序状态。
 * 
 * 简单的说，就是插入排序总共需要排序N-1趟，从index为1开始，讲该位置上的元素与之前的元素比较，放入合适的位置，这样循环下来之后，即为有序数组。
 *
 */
public class InsertionSort {

	public static int[] sort(int[] src){
		if(src == null || src.length == 0){
			return src;
		}
		
		for(int i=1; i<src.length; i++){
			for(int j=0; j<i; j++){
				if(src[i] < src[j]){
					int temp = src[i];
					for(int k=i; k>j; ){
						src[k] = src[--k];
					}
					src[j] = temp;
				}
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
