package redis.demo.messageQueue;

import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.UUID;

public class TaskProducer implements Runnable{
    Jedis conn = new Jedis("localhost");

    public void run(){
        Random random = new Random();
        while(true){
            try {
                Thread.sleep(random.nextInt(600)+600);
                //mock task
                UUID taskId = UUID.randomUUID();
                //put task into queue
                conn.lpush("task-queue",taskId.toString());
                System.out.println("input a new task-queue:"+taskId);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }
}
