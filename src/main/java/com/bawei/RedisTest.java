package com.bawei;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bawei.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:redis.xml")
public class RedisTest {

	User u1 = new User("电器","插排");
	User u2 = new User("水","水壶");
	User u3 = new User("游乐园","小马");
	User u4 = new User("马云","阿里巴巴");
	@Resource
	RedisTemplate redisTemplate;
	
	/**
	 * 
	    * @Title: show
	    * @Description: 测试list(list插进去表中，从左到右一次插入表头)
	    * @param     参数
	    * @return void    返回类型
	    * @throws
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void show() {
		
		ArrayList<User> list = new ArrayList<User>();
		list.add(u1);
		list.add(u2);
		list.add(u3);
		list.add(u4);
		redisTemplate.opsForList().leftPushAll("mylist", list);
		  //存进去所有的数据 
		/* redisTemplate.opsForList().leftPushAll("mylist", u1,u2,u3,u4); */
		  //获取所有的数据
		  
		  @SuppressWarnings("rawtypes")
		  List range = redisTemplate.opsForList().range("mylist", 0, -1); 
		  for (Object object :range) { 
			  System.out.println(object); 
			  } 
		  //从下标获取key里面值 
		  Object index =redisTemplate.opsForList().index("mylist", 3); 
		  System.out.println(index);
		 
		//获取key里面的长度
		Long size = redisTemplate.opsForList().size("mylist");
		//删除数据 count=0表头向表尾删除所有元素  count>0表头向表尾删除count个元素 ,count<0表尾向表头删除count个元素
		int count=0;
		Long remove = redisTemplate.opsForList().remove("mylist", count, u1);
		System.out.println("remove:"+remove);
		System.out.println(size);
	}
	//String类型
	@SuppressWarnings("unchecked")
	@Test
	public void stringRedis() {
		
		//进行往list里面添加
		redisTemplate.opsForValue().set("names","薛永");
		//获取到key为list
		Object object = redisTemplate.opsForValue().get("names");
		System.out.println(object);
		//删除key
		redisTemplate.delete("names");
	}
	//hash类型
	@SuppressWarnings("unchecked")
	@Test
	public void hashRedis() {
		HashMap<String, User> map = new HashMap<String,User>();
		map.put("u1", u1);
		map.put("u2", u2);
		map.put("u3", u3);
		map.put("u4", u4);
		redisTemplate.opsForHash().putAll("myhash", map);
		//得到所有的key
		Set keys = redisTemplate.opsForHash().keys("myhash");
		for (Object object : keys) {
			System.out.println(object);
		}
		//得到所有的value
		List values = redisTemplate.opsForHash().values("myhash");
		for (Object object : values) {
			System.out.println(object);
		}
		//获取myhash的键值对
		Map entries = redisTemplate.opsForHash().entries("myhash");
		Set entrySet = entries.entrySet();
		System.out.println(entrySet);
		//判断给定的key是否存在
		Boolean hasKey2 = redisTemplate.opsForHash().hasKey("myhash","u2");
		System.out.println(hasKey2);
		//删除多个key
		redisTemplate.opsForHash().delete("myhash", "u1","u2");
	}
	/**
	 * 
	    * @Title: setTest
	    * @Description: set类型的增删改查
	    * @param     参数
	    * @return void    返回类型
	    * @throws
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void setTest() {
		ArrayList<User> list = new ArrayList<User>();
		list.add(u1);
		list.add(u2);
		list.add(u3);
		list.add(u4);
		redisTemplate.opsForSet().add("myset1",list.toArray());
		//添加值
		redisTemplate.opsForSet().add("myset1","a","b","c","d","e");
		redisTemplate.opsForSet().add("myset2","a","b","c","gg","cc");
		//获取值
		Set members = redisTemplate.opsForSet().members("myset1");
		for (Object object : members) {
			System.out.println(object);
		}
		//求这两个集合的交集
		Set inter = redisTemplate.opsForSet().intersect("myset1","myset2");
		System.out.println(inter);
		//求两个集合的并集
		Set union = redisTemplate.opsForSet().union("myset1","myset2");
		System.out.println(union);
		//求两个集合的差集
		Set difference = redisTemplate.opsForSet().difference("myset1","myset2");
		System.out.println(difference);
	}
	/**
	 * zset
	    * @Title: zsetRedis
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param     参数
	    * @return void    返回类型
	    * @throws
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void zsetRedis() {
		//添加值
		redisTemplate.opsForZSet().add("zset", "sa", 80);
		redisTemplate.opsForZSet().add("zset", "李四", 90);
		redisTemplate.opsForZSet().add("zset", "zhangsan", 100);
		@SuppressWarnings("unchecked")
		//获取值
		Set range = redisTemplate.opsForZSet().range("zset", 0, -1);
		System.out.println(range);
		//求最小值和最大值之间的个数
		Long count = redisTemplate.opsForZSet().count("zset", 70, 90);
		System.out.println(count);
		//删除值
		Long remove = redisTemplate.opsForZSet().remove("zset", "sa");
		System.out.println(remove);
	}

}
