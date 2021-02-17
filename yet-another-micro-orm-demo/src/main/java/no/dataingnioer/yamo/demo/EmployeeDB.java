package no.dataingnioer.yamo.demo;

import java.util.Iterator;
import java.util.List;

import no.dataingenioer.yamo.core.MicroORM;
import no.dataingenioer.yamo.core.YetAnotherMicroORM;
import no.dataingenioer.yamo.core.utils.ConnectionSettings;

public class EmployeeDB {

    public static void main(String [] args) throws Exception {

        ConnectionSettings connectionSettings = ConnectionSettings.getConnectionSettings(getConfigFile("configuration.properties"));

        MicroORM orm = new YetAnotherMicroORM(connectionSettings);

        Employee employee = new Employee();

        employee.setId( 11 );
        employee.setEmail( "n@example.com" ); // Unique constraint
        employee.setFirstName("C");
        employee.setLastName ("D");

        // testInsert(orm, employee);
        // testUpdate(orm, employee);
        testDelete(orm, employee);

        testSelect(orm);
    }

    private static String getConfigFile(String fileName) {
        return  System.getProperty("user.dir")+ "/yet-another-micro-orm-demo/src/main/java/no/dataingnioer/yamo/demo/" + fileName;
    }

    public static void testInsert(MicroORM yetAnotherMicroORM, Employee employee)
    {
        String sql = "INSERT INTO Employee(email, first_name, last_name) VALUES (:email, :first_name, :last_name)";
        try {
            if(yetAnotherMicroORM.insert(sql, employee)){
                System.out.println("Entity inserted");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testUpdate(MicroORM orm, Employee employee)
    {
        String sql = "UPDATE Employee SET email = :email, first_name = :first_name, last_name = :last_name WHERE id = :id";
        try {
            if(orm.update(sql, employee)){
                System.out.println("Entity updated");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testDelete(MicroORM orm, Employee employee)
    {
        String sql = "DELETE FROM Employee WHERE id = :id";
        try {
            if(orm.delete(sql, employee)){
                System.out.println("Entity deleted");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testSelect(MicroORM orm)
    {
        String sql = "SELECT * FROM Employee";
        try {
            List<Employee> result = orm.select(sql, Employee.class);
            Iterator<Employee> iterator = result.iterator();
            while (iterator.hasNext()) {
                Employee employee = iterator.next();
                System.out.print( "ID: " + employee.getId());
                System.out.print( ", Epost: " + employee.getEmail());
                System.out.print( ", Fornavn: " + employee.getFirstName());
                System.out.println( ", Etternavn: " + employee.getLastName());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
