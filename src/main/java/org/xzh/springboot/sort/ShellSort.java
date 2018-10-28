package org.xzh.springboot.sort;

public class ShellSort {

	public static int[] sort(int[] a){
        Integer h = a.length;
        Integer temp = 0;
        while(h >= 1) {
            for(int i=h;i<a.length;i++) {
                for(int j=i;j>=h && a[j] < a[j-h];j -= h) {
                    temp = a[j];
                    a[j] = a[j-h];
                    a[j-h] = temp;
                    
                }
            }
            h /= 9;
        }
        return a;
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
