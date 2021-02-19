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
                .withEmail(generateRandomEmail(4)) // Unique constraint
                .withFirstName("Espen")
                .withLastName("Askeladd")
                .build();

        print(employeeRepository.readAll().iterator(), System.out);

        employee =  employeeRepository.create(employee);
        if(employee != null) {
            System.out.println(String.format("Employee %d created.", employee.getId()));
            print(employeeRepository.readAll().iterator(), System.out);
        } else {
            System.out.println("Employee could not be created.");
            System.exit(-1);
        }

        employee.setFirstName("Per");
        employee.setLastName("Hansen");
        if(employeeRepository.update(employee)) {
            System.out.println(String.format("Employee %d updated.", employee.getId()));
            print(employeeRepository.readAll().iterator(), System.out);
        }else {
            System.out.println(String.format("Employee %d could not be updated.", employee.getId()));
            System.exit(-1);
        }

        if(employeeRepository.delete(employee)) {
            System.out.println(String.format("Employee %d deleted.", employee.getId()));
            print(employeeRepository.readAll().iterator(), System.out);
        }else {
            System.out.println(String.format("Employee %d could not be deleted.", employee.getId()));
            System.exit(-1);
        }
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
        out.println("-------------------------------------------------------");
    }

    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz";
    public static String generateRandomEmail(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString() + "@example.com";
    }
}
