package no.dataingnioer.yamo.demo;

import static no.dataingenioer.yamo.core.utils.ConnectionSettings.getConnectionSettings;
import static no.dataingnioer.yamo.demo.utils.MailUtils.generateRandomEmail;
import static no.dataingnioer.yamo.demo.utils.PrintUtils.print;

import java.io.IOException;

import no.dataingenioer.yamo.core.utils.ConnectionSettings;
import no.dataingnioer.yamo.demo.model.Employee;
import no.dataingnioer.yamo.demo.model.EmployeeBuilder;
import no.dataingnioer.yamo.demo.repository.EmployeeRepository;

public class Application {

    public static void main(String [] args) throws IOException {

        ConnectionSettings connectionSettings = getConnectionSettings(getConfigFile("configuration.properties"));

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

}
