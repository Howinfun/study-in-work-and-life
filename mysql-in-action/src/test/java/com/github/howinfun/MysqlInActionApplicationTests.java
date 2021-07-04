package com.github.howinfun;

import com.github.howinfun.entity.UniqueKeyTest;
import com.github.howinfun.service.UniqueKeyTestService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = MysqlInActionApplication.class)
@RunWith(SpringRunner.class)
public class MysqlInActionApplicationTests {

	@Autowired
	private UniqueKeyTestService uniqueKeyTestService;

	/**
	 * 线程数
	 */
	private static final int threadNum = 3;
	/**
	 * 控制同时请求
	 */
	private static final CountDownLatch countDownLatch = new CountDownLatch(threadNum);

	/**
	 * 用户请求
	 */
	class UserRequest implements Runnable{

		@Override
		public void run() {
			try {
				// 等待
				countDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 批量插入用户，插入数据不变
			saveUserList();
		}
	}

	@Test
	public void contextLoads() {
		for (int i = 0; i < threadNum; i++) {
			new Thread(new UserRequest()).start();
			// 计数减一
			countDownLatch.countDown();
		}
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量插入用户
	 */
	public void saveUserList(){
		List<UniqueKeyTest> UniqueKeyTestList = new ArrayList<>();
		UniqueKeyTestList.add(new UniqueKeyTest().setName("winfun").setAge(1));
		UniqueKeyTestList.add(new UniqueKeyTest().setName("fenghao").setAge(2));
		UniqueKeyTestList.add(new UniqueKeyTest().setName("luff").setAge(3));
		this.uniqueKeyTestService.saveBatch(UniqueKeyTestList);
	}
}
