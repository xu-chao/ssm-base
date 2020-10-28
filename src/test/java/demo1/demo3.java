package demo1;

public class demo3 {
    public static void main(String[] args) {
        String str[][] = new String[2][2];
        str[0][0] = "0-0";
        str[0][1] = "0-1";
        str[1][0] = "1-0";
        str[1][1] = "1-1";
for (int i =0;i<str.length;i++){
    System.out.println(str[i][i]);

}
    }
}
