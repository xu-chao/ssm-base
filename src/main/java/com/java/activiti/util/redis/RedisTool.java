package com.java.activiti.util.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.UUID;

public class RedisTool {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
    private static  int ticketCount = 450;
    public static JedisPool pool;
    static String lockKey = getRequestId();
//    static {
//        if(pool == null) {
//            JedisPoolConfig config = new JedisPoolConfig();
//            //����һ��pool�ɷ�����ٸ�jedisʵ����ͨ��pool.getResource()����ȡ��
//            //�����ֵΪ-1�����ʾ�����ƣ����pool�Ѿ�������maxActive��jedisʵ�������ʱpool��״̬Ϊexhausted(�ľ�)��
//            config.setMaxTotal(1000);
//            config.setMaxWaitMillis(1000);
//            //����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ����
//            config.setMaxIdle(50);
//            //��ʾ��borrow(����)һ��jedisʵ��ʱ�����ĵȴ�ʱ�䣬��������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException����λ����
//            //С����:������ȷ����ʱ��,  Ĭ��-1
//            config.setMaxWaitMillis(1000 * 100);
//            //��borrow(����)һ��jedisʵ��ʱ���Ƿ���ǰ����validate���������Ϊtrue����õ���jedisʵ�����ǿ��õģ�
//            config.setTestOnBorrow(true);
//            //return һ��jedisʵ����poolʱ���Ƿ������ӿ����ԣ�ping()��
//            config.setTestOnReturn(true);
//            //connectionTimeout ���ӳ�ʱ��Ĭ��2000ms��
//            //soTimeout ��Ӧ��ʱ��Ĭ��2000ms��
//            pool = new JedisPool(config, "127.0.0.1", 6379,  2000);
//        }
//    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static String getRequestId(){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

//    @Test
//    public void test() throws InterruptedException {
//        long start = System.currentTimeMillis();
//        final CountDownLatch countDownLatch = new CountDownLatch(500);
//        Executor executor = Executors.newFixedThreadPool(50);
//        for(int i=0 ; i<500 ; i++){
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        distributeLock();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    countDownLatch.countDown();
//                }
//            });
//        }
//        countDownLatch.await();
//        long costTime = System.currentTimeMillis() - start;
//        pool.close();
//        System.out.println("it totally took��" + costTime + " ms");
//    }

    public void distributeLock() throws InterruptedException {
        Jedis jedis = null;
        String requestId = null;
        int timeoutCount = 0;
        requestId = getRequestId();
        while (true){
            try{
                jedis = getJedis();
                break;
            } catch (Exception e){
                if (e instanceof JedisConnectionException || e instanceof SocketTimeoutException) {
                    //��¼�»�ȡ���ٴβŻ��jedis���ӣ������ܶ��ʱ����ܳ�������Ӳ��������»�ȡ����ʧ�ܣ���������ѭ����ȡ
                    timeoutCount++;
                    //System.out.println("user:"+requestId+" getJedis timeoutCount={"+timeoutCount+"}");
                }
            }
        }

        if(tryGetDistributedLock(jedis, lockKey, requestId, 3000)) {
            if (ticketCount > 0) {
                System.out.println(requestId + " have got a ticket��" + ticketCount);
                ticketCount--;
            } else {
                System.out.println(requestId + "the ticket have been sold out.");
            }
            releaseDistributedLock(jedis, lockKey, requestId);
            jedis.close();
        } else {
            System.out.println("user"+ requestId +" have been sold out!Give up getting lock");
        }

    }

    /**
     * ���Ի�ȡ�ֲ�ʽ��
     * @param jedis Redis�ͻ���
     * @param lockKey ��
     * @param requestId �����ʶ
     * @param expireTime ����ʱ��
     * @return �Ƿ��ȡ�ɹ�
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) throws InterruptedException {

        int timeoutCount = 0;
        long currentTime = System.currentTimeMillis();
        while(true){

            try{
                String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
                if (LOCK_SUCCESS.equals(result)) {
                    return true;
                }
                if(ticketCount <=0){
                    System.out.println("user"+ requestId +" have been sold out!Give up getting lock");
                    return false;
                }
                /*//�ȴ���60S���ϣ�ֱ�Ӳ��ٻ�ȡ��
                if(System.currentTimeMillis() > (currentTime+ 60*1000)){
                    System.out.println("user"+ requestId +"�ȴ���60S�����ˣ�����������");
                    return false;
                }*/
            } catch (Exception e){
                /*if (e instanceof JedisConnectionException || e instanceof SocketTimeoutException) {
                    timeoutCount++;
                    //System.out.println("user:"+requestId+" jedis.set() timeoutCount={"+timeoutCount+"}");
                    if (timeoutCount > 3)
                    {
                        System.out.println("connect error��");
                        break;
                    }
                }*/
            }
        }
    }

    /**
     * �ͷŷֲ�ʽ��
     * @param jedis Redis�ͻ���
     * @param lockKey ��
     * @param requestId �����ʶ
     * @return �Ƿ��ͷųɹ�
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

        //�ж�requestId���ʱΪ��ȷ�������ͻ�ȡ�����û���ͬһ���û�
        //lockKey����Դ�ҵ�����ID ִ�гɹ�����1.
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }
}
