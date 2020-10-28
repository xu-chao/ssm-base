package IdGenerator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * �÷�˵��
 * @author Ivan.Ma
 */
public class TestStandaloneIdGenerator {

    @Test
    public void test1(){
        IdGenerator idGenerator = new DefaultIdGenerator();

        System.out.println("--------�򵥲���------------------");
        for (int i=0; i<100; i++){
            System.out.println(idGenerator.next());
        }
    }

    @Test
    public void test2(){
        IdGenerator idGenerator = new DefaultIdGenerator();

        //���̲߳���
        System.out.println("--------���̲߳��Բ��ظ�------------------");
        Set<String> idSet = Collections.synchronizedSet(new HashSet<>());
        ExecutorService es = Executors.newFixedThreadPool(100);
        for (int i=0; i<2000000; i++){
            es.submit(() -> {
                String val = idGenerator.next();
                if (idSet.contains(val)){
                    System.out.println("�ظ���: " + val);
                }else{
                    idSet.add(val);
                }
            });
        }
        es.shutdown();
        System.out.println("����˳��ر�");
        while(true){
            if(es.isTerminated()){
                System.out.println("���е����̶߳������ˣ�");
                break;
            }
            try {
                System.out.println("���̵߳�����û������");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("������: " + idSet.size() + "��");
    }

    @Test
    public void  test3(){
        //���Ե�������
        System.out.println("--------���Ե��߳�����------------------");
        IdGenerator idGenerator2 = new DefaultIdGenerator();
        long t1 = System.currentTimeMillis();
        int total = 10000000;
        for (int i=0; i<total; i++){
            idGenerator2.next();
        }
        System.out.println("���߳�����" + total + "��ID����ʱ: " + (System.currentTimeMillis() - t1) + "ms");
    }

    //500���̲߳���, ÿ���̻߳�ȡ10000��ID
    @Test
    public void test4(){
        //���Զ��߳�����
        System.out.println("--------���Զ��߳�����------------------");
        ExecutorService es1 = Executors.newFixedThreadPool(500);
        IdGenerator idGenerator3 = new DefaultIdGenerator();
        long t1 = System.currentTimeMillis();
        for (int i=0; i<500; i++){
            es1.submit(() -> {
                int count = 0;
                while (count < 10000){
                    idGenerator3.next();

                    count++;
                }
            });
        }
        es1.shutdown();
        System.out.println("����˳��ر�");
        while(true){
            if(es1.isTerminated()){
                System.out.println("���е����̶߳������ˣ�");
                break;
            }
            try {
                System.out.println("���̵߳�����û������");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("500�߳�,ÿ���߳�����10000�����к�.����ʱ: " + (System.currentTimeMillis() - t1) + " ms");
    }

    @Test
    public void test5(){
        System.out.println("--------�������ɵ�ID�Ƿ���ʱ�����----------");
        IdGenerator idGenerator = new DefaultIdGenerator();
        for (int i=0; i<20; i++){
            String id = idGenerator.next();
            System.out.println(id);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test6(){
        System.out.println("--------ID�������������������----------");
        IdGeneratorConfig config = new DefaultIdGeneratorConfig() {
            @Override
            public String getSGWX() {
                return "SG";
            }

            @Override
            public String getABK() {
                return "A";
            }

            @Override
            public String getSplitString() {
                return "-";
            }

            @Override
            public String get08467() {
                return "8";
            }

            @Override
            public int getInitial() {
                return 1;
            }

            @Override
            public String getH() {
                return "H";
            }

            @Override
            public String getPrefix() {
                return "";
            }
        };
        IdGenerator idGenerator = new DefaultIdGenerator(config);
        for (int i=0; i<20; i++){
            String id = idGenerator.next();
            System.out.println(id);
            try {
                Thread.sleep(1000 * 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}