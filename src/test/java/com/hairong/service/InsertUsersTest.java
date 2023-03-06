package com.hairong.service;

import com.hairong.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest
public class InsertUsersTest {
    @Resource
    private UserService userService;

    // 扩大线程池
    // CPU 密集型：分配的核心线程数 = CPU -1
    // IO 密集型：分配的核心线程数可以大于 CPU 核数
    private ExecutorService executorService = new ThreadPoolExecutor(40, 1000, 10000, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10000));

    /**
     * 批量插入用户数据（顺序插入）
     */
    @Test
    public void doInsertUsers() {
        // 计算插入数据的时间
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 100000;
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            user.setUsername("假海荣");
            user.setUserAccount("fakehairong");
            user.setAvatarUrl("https://img1.baidu.com/it/u=1659441821,1293635445&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
            user.setGender(0);
            user.setUserPassword("b0dd3697a192885d7c055db46155b26a");
            user.setPhone("123");
            user.setEmail("123@qq.com");
            user.setTags("[]");
            user.setUserStatus(0);
            user.setUserRole(0);
            user.setPlanetCode("1111111");
            userList.add(user);

        }
        // 批量插入一百万条数据花费27秒(27154) （本质上还是顺序插入）
        userService.saveBatch(userList, 10000);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

    /**
     * 并发批量插入用户
     */
    @Test
    public void doConcurrencyInsertUsers() {
        // 计算插入数据的时间
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 100000;
        int batchSize = 5000;
        // 分十组
        int j = 0;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            List<User> userList = new ArrayList<User>();
            while(true) {
                j ++;
                User user = new User();
                user.setUsername("假海荣");
                user.setUserAccount("fakehairong");
                user.setAvatarUrl("https://img1.baidu.com/it/u=1659441821,1293635445&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
                user.setGender(0);
                user.setUserPassword("b0dd3697a192885d7c055db46155b26a");
                user.setPhone("123");
                user.setEmail("123@qq.com");
                user.setTags("[]");
                user.setUserStatus(0);
                user.setUserRole(0);
                user.setPlanetCode("1111111");
                userList.add(user);
                if (j % batchSize == 0) {
                    break;
                }
            }
            // 异步执行
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                System.out.println("threadName" + Thread.currentThread().getName());
                userService.saveBatch(userList, batchSize);
            }, executorService);
            futureList.add(future);
        }
        // 阻塞一下任务，方便下一步的计时操作
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
        // 批量插入一百万条数据花费7秒 （9788） (7067) (7507) (6333)
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
