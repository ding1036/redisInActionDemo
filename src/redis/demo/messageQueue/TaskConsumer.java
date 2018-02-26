package redis.demo.messageQueue;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class TaskConsumer implements Runnable{
    Jedis conn = new Jedis("localhost");

    public void run(){
        Random random = new Random();
        while(true){
            String taskId = conn.rpoplpush("task-queue","tmp-queue");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // simulated failure situation
            if(random.nextInt(13)%7==0){
                conn.rpoplpush("tmp-queue","task-queue");//out of order
                //conn.rpush("task-queue",conn.rpop("tmp-queue")); //in order
                System.out.println(taskId+" handle failure, ture back to task-queue");
            }else{
                conn.rpop("tmp-queue");
                System.out.println(taskId+" handle successful");
            }
        }
    }
}
