package com.tensquare.gathering;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * SpringCache
 * 对某个方法进行缓存，其实质就是缓存该方法的返回结果，并把方
 * 法参数和结果用键值对的方式存放到缓存中，当再次调用该方法使
 * 用相应的参数时，就会直接从缓存里面取出指定的结果进行返回
 *
 * @Cacheable-------使用这个注解的方法在执行后会缓存其返回结果。
 * @CacheEvict--------使用这个注解的方法在其执行前或执行后移除SpringCache中的某些元素。
 *
 * 添加@EnableCaching 开启缓存支持
 */
@SpringBootApplication
@EnableCaching
public class GatheringApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatheringApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}
	
}
