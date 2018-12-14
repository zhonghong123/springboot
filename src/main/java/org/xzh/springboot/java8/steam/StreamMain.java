package org.xzh.springboot.java8.steam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamMain {
	
	private static final Logger logger = LoggerFactory.getLogger(StreamMain.class);

	public static void main(String[] args) {
		List<Dish> menu = Arrays.asList(
				new Dish("pork", false, 800, Dish.Type.MEAT),
				new Dish("beef", false, 700, Dish.Type.MEAT),
				new Dish("chicken", false, 400, Dish.Type.MEAT),
				new Dish("french fries", true, 530, Dish.Type.OTHER),
				new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("season fruit", true, 120, Dish.Type.OTHER),
				new Dish("pizza", true, 550, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH),
				new Dish("salmon", false, 450, Dish.Type.FISH) );
		
		//筛选:用谓词筛选,Streams接口支持filter方法（你现在应该很熟悉了）。该操作会接受一个谓词（一个返回
		//boolean的函数）作为参数，并返回一个包括所有符合谓词的元素的流。
		List<Dish> filterDish = menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
		logger.info("filter dish is {}", filterDish);
		
		//筛选各异的元素.
		//流还支持一个叫作distinct的方法，它会返回一个元素各异（根据流所生成元素的
		//hashCode和equals方法实现）的流。
		List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
		numbers.stream().filter(i->i%2==0).distinct().forEach(msg -> logger.info(msg+""));
		
		//截短流
		//流支持limit(n)方法，该方法会返回一个不超过给定长度的流。所需的长度作为参数传递
		//给limit。如果流是有序的，则最多会返回前n个元素。
		List<Dish> limitDish = menu.stream().filter(p -> p.getCalories()>300).limit(3).collect(Collectors.toList());
		logger.info("filter dish is {}", limitDish);
		
		//跳过元素
		//流还支持skip(n)方法，返回一个扔掉了前n个元素的流。如果流中元素不足n个，则返回一
		//个空流。请注意，limit(n)和skip(n)是互补的
		List<Dish> skipDish = menu.stream().filter(p -> p.getCalories()>300).skip(2).collect(Collectors.toList());
		logger.info("skip dish is {}", skipDish);
		
		
		//-----------------------------------------------------
		
		//映射
		List<String> names = menu.stream().map(p -> p.getName()).collect(Collectors.toList());
		logger.info("map dish is {}",names);
		
		
		//---------------------------------------------------------
		
		//查找与匹配
		//检查谓词是否至少匹配一个元素
		if(menu.stream().anyMatch(p->p.isVegetarian())){
			logger.info("anyMatch is true");
		}
		
		//检查谓词是否匹配所有元素
		//allMatch方法的工作原理和anyMatch类似，但它会看看流中的元素是否都能匹配给定的谓词
		//和allMatch相对的是noneMatch。它可以确保流中没有任何元素与给定的谓词匹配
		logger.info("allMatch is {}", menu.stream().allMatch(p->p.isVegetarian()));
		logger.info("allMatch is {}", menu.stream().noneMatch(p->p.isVegetarian()));
		
		//findAny方法将返回当前流中的任意元素。
		Optional<Dish> findAnyDish = menu.stream().filter(p->p.isVegetarian()).findAny();
		logger.info(findAnyDish.get().getName());
		
		//查找第一个元素
		Optional<Dish> findFirstDish = menu.stream().filter(p->p.isVegetarian()).findFirst();
		if(findFirstDish.isPresent()){
			logger.info(findFirstDish.get().getName());
		}
		
		
		//------------------------------------------------
		
		//归约
		//元素求和
		int sum = menu.stream().mapToInt(p->p.getCalories()).reduce(0, (a, b)->a+b);
		logger.info("sum {}", sum);
		Optional<Integer> sum2 = menu.stream().map(p -> p.getCalories()).reduce((a, b) -> a+b);
		if(sum2.isPresent()){
			logger.info("sum2 {}", sum2.get());
		}
		
		//最大值和最小值
		Optional<Integer> min = menu.stream().map(p->p.getCalories()).reduce((a, b)->Integer.min(a, b));
		if(min.isPresent()){
			logger.info("min {}", min.get());
		}
		Optional<Integer> max = menu.stream().map(p->p.getCalories()).reduce((a, b)-> a>b?a:b);
		if(max.isPresent()){
			logger.info("max {}", max.get());
		}
	}
}
