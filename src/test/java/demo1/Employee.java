package demo1;

public class Employee {
    public String empName;
    private double salary;
    static final int age1 = 17;
     public static  int age2 = 17;
    public  Employee(String name){
        empName = name;
    }
    public void setSalary(double salary){
        this.salary = salary;
    }

    public static void main(String[] args) {
        Employee employee = new Employee("Ð¡·å");
        employee.setSalary(8888.88);
        System.out.println(employee.empName + "   "+employee.salary);
        age2 = 10;

    }
}
