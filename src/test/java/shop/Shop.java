package shop;

import java.util.ArrayList;
import java.util.Scanner;

public class Shop {

        public static void main(String[] args) {

/**
 因为数组长度不可变 所以采用集合方式? ArrayList中放的是引用类型的数据

 * 创建arraylist集合 存储FruitItem类型的数据

 */
            ArrayList<FruitItem> arry = new ArrayList<FruitItem>();

            init(arry);
            //死循环操作
            while (true) {
                mainMenu();
                Scanner s = new Scanner(System.in);
                int in = s.nextInt();
                switch (in) {
                    case 1:
                        show(arry);
                        break;
                    case 2:
                        add(arry);
                        break;
                    case 3:
                        del(arry);
                        break;
                    case 4:
                        update(arry);
                        break;
                    case 5:
                        return;
                    default:
                        System.err.println("输入的序号不存在");
                        break;
                }
            }
        }

        /**
         * 定义商品初始化方法? 创建几个商品并添加到集合array中
         */
        public static void init(ArrayList<FruitItem> arry) {

            //创建第1个商品

            FruitItem f1 = new FruitItem();

            f1.ID = 1000;

            f1.name = "笔记本";

            f1.price = 10.0;

            //创建第2个商品

            FruitItem f2 = new FruitItem();

            f2.ID = 1001;

            f2.name = "西红柿";

            f2.price = 2.0;

            //创建第3个商品

            FruitItem f3 = new FruitItem();

            f3.ID = 1002;

            f3.name = "辣条";
            f3.price = 5.0;

            arry.add(f1);

            arry.add(f2);

            arry.add(f3);

        }

        /**
         * 主方法
         */

        public static void mainMenu() {

            System.out.println();

            System.out.println("==========超市管理系统===========");

            System.out.println("1: 货物清单 2: 增加货物 3: 删除货物 4: 修改货物? 5 退出");
            System.out.println("输出你要操作的编号");
        }

        /**
         * 查看方法
         */

        public static void show(ArrayList<FruitItem> arry) {

            System.out.println();

            System.out.println("==========商品清单===============");

            System.out.println("商品编号  商品单价 商品名称");

//遍历集合

            for (int i = 0; i < arry.size(); i++) {

                FruitItem f = arry.get(i);

                System.out.println(f.ID + "  " + f.price + " " + f.name);

            }


        }

        /**
         * 添加功能
         */

        public static void add(ArrayList<FruitItem> arry) {

            System.out.println("选择的是添加商品功能");

            Scanner in = new Scanner(System.in);

            System.out.println("输出商品编号ID");

            int ID = in.nextInt();

            System.out.println("输入商品单价");

            double price = in.nextDouble();

            System.out.println("输入商品名称");

            String name = in.next();

            //创建商品对象

            FruitItem f = new FruitItem();

            f.ID = ID;

            f.price = price;

            f.name = name;

            //添加到集合

            arry.add(f);

            System.out.println("添加成功");

        }

        /**
         *
         */

        public static void del(ArrayList<FruitItem> arry) {

            System.out.println();

            System.err.println("选择的是删除功能");

            System.out.println("输出要删除的商品编号ID");

            Scanner in = new Scanner(System.in);

            int id = in.nextInt();

            //遍历集合

            for (int i = 0; i < arry.size(); i++) {

                FruitItem f = arry.get(i);

                //比对输入的与已经存在的

                if (f.ID == id) {

                    arry.remove(f);

                    System.out.println("删除成功");

                    //当遍历相等的时候直接结束方法

                    return;

                    //break;

                }

                //如果if不执行则? for循环结束打印

                System.out.println("不存在此商品");

            }

        }

        /**
         * 修改功能
         */

        public static void update(ArrayList<FruitItem> arry) {

            System.out.println();

            System.out.println("选的是修改功能");

            System.out.println("输入你要修改的商品编号ID");

            Scanner in = new Scanner(System.in);

            int id = in.nextInt();

            //遍历集合

            for (int i = 0; i < arry.size(); i++) {

                FruitItem f = arry.get(i);

                if (f.ID == id) {

                    System.out.println("输入新的商品编号");

                    f.ID = in.nextInt();

                    System.out.println("输入商品单价");

                    f.price = in.nextDouble();

                    System.out.println("输入商品名称");

                    f.name = in.next();

                    System.out.println("修改成功");

                    return;

                }
            }
        }
    }

