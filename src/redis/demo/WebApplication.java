package redis.demo;

import redis.clients.jedis.Jedis;

public class WebApplication {
    public static void main(String args[]){
        new WebApplication().run();
    }

    public void run() {
        Jedis conn = new Jedis("localhost");
         conn.select(15);
    }

    public String checkToken(Jedis conn,String token){
        return conn.hget("login:",token);
    }

    public void updateToken(Jedis conn,String token,String user,String item){
        long timestamp = System.currentTimeMillis()/1000;
        conn.hset("login:",token,user);
        conn.zadd("recent:",timestamp,token);
        if(item!=null){
            conn.zadd("viewed:"+token,timestamp,item);
            conn.zremrangeByRank("viewed:"+token,0,-26);
        }
    }
}
