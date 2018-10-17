package org.xzh.springboot.jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 强引用、软引用、弱引用、虚引用
 *
 */
public class Reference {
	
	private int index = 0;

	public static void main(String[] args) {
		//强引用：通过new的方式创建对象，主要该引用（strong）存在，垃圾回收期用户不会回收。
		//只有当该引用（strong）释放之后，才会被回收。否则就算内存溢出，也不会回收强引用
		Reference strong = new Reference();
		//可以通过strong获取对象
		System.out.println(strong.index);
		
		//软引用:非必须引用，内存溢出之前进行回收
		//这时候sf是对obj的一个软引用，通过sf.get()方法可以取到这个对象，当然，当这个对象被标记为需要回收的对象时，则返回null；
		//软引用主要用户实现类似缓存的功能，在内存足够的情况下直接通过软引用取值，无需从繁忙的真实来源查询数据，提升速度；当内存不足时，自动删除这部分缓存数据，从真正的来源查询这些数据。
		Reference soft = new Reference();
		SoftReference<Reference> softReference = new SoftReference<Reference>(soft);
		if(softReference.get()!=null){
			System.out.println(softReference.get().index);
		}
		
		//弱引用:第二次垃圾回收时回收
		//弱引用是在第二次垃圾回收时回收，短时间内通过弱引用取对应的数据，可以取到，当执行过第二次垃圾回收时，将返回null。
		//弱引用主要用于监控对象是否已经被垃圾回收器标记为即将回收的垃圾，可以通过弱引用的isEnQueued方法返回对象是否被垃圾回收器标记。
		Reference weak = new Reference();
		WeakReference<Reference> weakReference = new WeakReference<Reference>(weak);
		System.out.println(weakReference.get());
		
		//虚引用:垃圾回收时回收，无法通过引用取到对象值
		//虚引用是每次垃圾回收的时候都会被回收，通过虚引用的get方法永远获取到的数据为null，因此也被成为幽灵引用。
		//虚引用主要用于检测对象是否已经从内存中删除。
		Reference phantom = new Reference();
		PhantomReference<Reference> phantomReference = new PhantomReference<Reference>(phantom, null);
		System.out.println(phantomReference.get());
	}
}
