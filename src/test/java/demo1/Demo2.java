package demo1;

public class Demo2 {
    public void dogAge() {
        int age = 0 ;
        age += 7;
        System.out.println("小狗的真实年龄是：" + age);
    }

    public static void main(String[] args) {
        Demo2 demo2 = new Demo2();
        demo2.dogAge();
    }
}
