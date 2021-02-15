package no.dataingenioer.yamo.demo;

import java.util.Iterator;
import java.util.List;


import no.dataingenioer.yamo.MicroORM;
import no.dataingenioer.yamo.YetAnotherMicroORM;
import no.dataingenioer.yamo.utils.ConnectionSettings;

public class YAMODemo {

    public static void main(String [] args) throws Exception {

        ConnectionSettings connectionSettings;
        connectionSettings = ConnectionSettings.getConnectionSettings("config");
        MicroORM yetAnotherMicroORM = new YetAnotherMicroORM(connectionSettings);

        //testInsert(yetAnotherMicroORM);

        //testUpdate(yetAnotherMicroORM);
        testSelect(yetAnotherMicroORM);
        System.out.println("----------");
        //testDelete(yetAnotherMicroORM);
        //testSelect(yetAnotherMicroORM);

    }

    public static void testInsert(MicroORM yetAnotherMicroORM)
    {
        String sql = "INSERT INTO Employee(email, first_name, last_name) VALUES (:email, :first_name, :last_name)";
        try {
            Employee employee = new Employee();
            employee.email = "a@example.com";
            employee.first_name = "A";
            employee.last_name = "B";
            if(yetAnotherMicroORM.insertQuery(sql, employee)){
                System.out.println("Entity inserted");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testUpdate(MicroORM yetAnotherMicroORM)
    {
        String sql = "UPDATE Employee SET email = :email, first_name = :first_name, last_name = :last_name WHERE id = :id";
        try {
            Employee employee = new Employee();
            employee.id = 3;
            employee.email = "b@example.com";
            employee.first_name = "C";
            employee.last_name = "D";
            if(yetAnotherMicroORM.updateQuery(sql, employee)){
                System.out.println("Entity updated");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testDelete(MicroORM yetAnotherMicroORM)
    {
        String sql = "DELETE FROM Employee WHERE id = :id";
        try {
            Employee employee = new Employee();
            employee.id = 3;
            if(yetAnotherMicroORM.deleteQuery(sql, employee)){
                System.out.println("Entity deleted");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testSelect(MicroORM yetAnotherMicroORM)
    {
        String sql = "SELECT * FROM Employee";
        try {
            List<Employee> result = yetAnotherMicroORM.selectQuery(sql, Employee.class);
            Iterator<Employee> iterator = result.iterator();
            while (iterator.hasNext()) {
                Employee employee = iterator.next();
                System.out.print( "ID: " + employee.id);
                System.out.print( ", Epost: " + employee.email);
                System.out.print( ", Fornavn: " + employee.first_name);
                System.out.println( ", Etternavn: " + employee.last_name);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
