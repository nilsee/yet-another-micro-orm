package no.dataingnioer.yamo.demo;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

import no.dataingenioer.yamo.core.utils.ConnectionSettings;

public class Application {

    public static void main(String [] args) throws IOException {

        ConnectionSettings connectionSettings = ConnectionSettings
                .getConnectionSettings(getConfigFile("configuration.properties"));

        EmployeeRepository employeeRepository = new EmployeeRepository(connectionSettings);

        EmployeeBuilder builder = new EmployeeBuilder();

        Employee employee = builder
                .withEmail("xyz@example.com") // Unique constraint
                .withFirstName("C")
                .withLastName("D")
                .build();

        if(employeeRepository.create(employee)) {
            System.out.println("Employee created.");
        }

        if(employeeRepository.update(employee)) {
            System.out.println("Employee updated.");
        }

        if(employeeRepository.delete(employee)) {
            System.out.println("Employee created.");
        }

        print(employeeRepository.readAll().iterator(), System.out);
    }

    private static String getConfigFile(String fileName) {
        return  System.getProperty("user.dir")+ "/yet-another-micro-orm-demo/src/main/java/no/dataingnioer/yamo/demo/" + fileName;
    }

    private static void print(Iterator<Employee> iterator, PrintStream out){
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            out.print( "ID: " + employee.getId());
            out.print( ", Epost: " + employee.getEmail());
            out.print( ", Fornavn: " + employee.getFirstName());
            out.println( ", Etternavn: " + employee.getLastName());
        }
    }
}
