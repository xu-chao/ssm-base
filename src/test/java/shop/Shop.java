package shop;

import java.util.ArrayList;
import java.util.Scanner;

public class Shop {

        public static void main(String[] args) {

/**
 ��Ϊ���鳤�Ȳ��ɱ� ���Բ��ü��Ϸ�ʽ? ArrayList�зŵ����������͵�����

 * ����arraylist���� �洢FruitItem���͵�����

 */
            ArrayList<FruitItem> arry = new ArrayList<FruitItem>();

            init(arry);
            //��ѭ������
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
                        System.err.println("�������Ų�����");
                        break;
                }
            }
        }

        /**
         * ������Ʒ��ʼ������? ����������Ʒ����ӵ�����array��
         */
        public static void init(ArrayList<FruitItem> arry) {

            //������1����Ʒ

            FruitItem f1 = new FruitItem();

            f1.ID = 1000;

            f1.name = "�ʼǱ�";

            f1.price = 10.0;

            //������2����Ʒ

            FruitItem f2 = new FruitItem();

            f2.ID = 1001;

            f2.name = "������";

            f2.price = 2.0;

            //������3����Ʒ

            FruitItem f3 = new FruitItem();

            f3.ID = 1002;

            f3.name = "����";
            f3.price = 5.0;

            arry.add(f1);

            arry.add(f2);

            arry.add(f3);

        }

        /**
         * ������
         */

        public static void mainMenu() {

            System.out.println();

            System.out.println("==========���й���ϵͳ===========");

            System.out.println("1: �����嵥 2: ���ӻ��� 3: ɾ������ 4: �޸Ļ���? 5 �˳�");
            System.out.println("�����Ҫ�����ı��");
        }

        /**
         * �鿴����
         */

        public static void show(ArrayList<FruitItem> arry) {

            System.out.println();

            System.out.println("==========��Ʒ�嵥===============");

            System.out.println("��Ʒ���  ��Ʒ���� ��Ʒ����");

//��������

            for (int i = 0; i < arry.size(); i++) {

                FruitItem f = arry.get(i);

                System.out.println(f.ID + "  " + f.price + " " + f.name);

            }


        }

        /**
         * ��ӹ���
         */

        public static void add(ArrayList<FruitItem> arry) {

            System.out.println("ѡ����������Ʒ����");

            Scanner in = new Scanner(System.in);

            System.out.println("�����Ʒ���ID");

            int ID = in.nextInt();

            System.out.println("������Ʒ����");

            double price = in.nextDouble();

            System.out.println("������Ʒ����");

            String name = in.next();

            //������Ʒ����

            FruitItem f = new FruitItem();

            f.ID = ID;

            f.price = price;

            f.name = name;

            //��ӵ�����

            arry.add(f);

            System.out.println("��ӳɹ�");

        }

        /**
         *
         */

        public static void del(ArrayList<FruitItem> arry) {

            System.out.println();

            System.err.println("ѡ�����ɾ������");

            System.out.println("���Ҫɾ������Ʒ���ID");

            Scanner in = new Scanner(System.in);

            int id = in.nextInt();

            //��������

            for (int i = 0; i < arry.size(); i++) {

                FruitItem f = arry.get(i);

                //�ȶ���������Ѿ����ڵ�

                if (f.ID == id) {

                    arry.remove(f);

                    System.out.println("ɾ���ɹ�");

                    //��������ȵ�ʱ��ֱ�ӽ�������

                    return;

                    //break;

                }

                //���if��ִ����? forѭ��������ӡ

                System.out.println("�����ڴ���Ʒ");

            }

        }

        /**
         * �޸Ĺ���
         */

        public static void update(ArrayList<FruitItem> arry) {

            System.out.println();

            System.out.println("ѡ�����޸Ĺ���");

            System.out.println("������Ҫ�޸ĵ���Ʒ���ID");

            Scanner in = new Scanner(System.in);

            int id = in.nextInt();

            //��������

            for (int i = 0; i < arry.size(); i++) {

                FruitItem f = arry.get(i);

                if (f.ID == id) {

                    System.out.println("�����µ���Ʒ���");

                    f.ID = in.nextInt();

                    System.out.println("������Ʒ����");

                    f.price = in.nextDouble();

                    System.out.println("������Ʒ����");

                    f.name = in.next();

                    System.out.println("�޸ĳɹ�");

                    return;

                }
            }
        }
    }

