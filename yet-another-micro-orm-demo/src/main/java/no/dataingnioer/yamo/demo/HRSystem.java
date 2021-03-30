package no.dataingnioer.yamo.demo;

import static no.dataingenioer.yamo.core.configuration.ConnectionSettings.getConnectionSettings;
import static no.dataingnioer.yamo.demo.utils.FileUtils.getConfigFilePath;
import static no.dataingnioer.yamo.demo.utils.MailUtils.generateRandomName;
import static no.dataingnioer.yamo.demo.utils.PrintUtils.printEmployeeList;

import java.io.IOException;

import no.dataingenioer.yamo.core.configuration.ConnectionSettings;
import no.dataingenioer.yamo.core.repository.EntityRepository;
import no.dataingnioer.yamo.demo.model.Employee;
import no.dataingnioer.yamo.demo.model.EmployeeBuilder;
import no.dataingnioer.yamo.demo.repository.EmployeeRepository;

public class HRSystem {

    private EntityRepository<Employee> employeeRepository;

    public HRSystem() throws IOException {

        String configFileName = getConfigFilePath("configuration.properties");

        ConnectionSettings connectionSettings = getConnectionSettings(configFileName);

        this.employeeRepository = new EmployeeRepository(connectionSettings);
    }

    public Employee init() {

        System.out.println(employeeRepository.getQueryBuilder());
        System.out.println("-------------------------------------------------------");

        EmployeeBuilder builder = new EmployeeBuilder();

        Employee employee = builder
                .withGeneratedEmployee()
                .build();

        printEmployeeList(employeeRepository.readAll().iterator(), System.out);

        return employee;
    }

    public void newEmplyee(Employee employee) {

        employee = employeeRepository.create(employee);
        if (employee != null) {

            System.out.println(String.format("Employee %d created.", employee.getId()));
            printEmployeeList(employeeRepository.readAll().iterator(), System.out);
        } else {

            System.out.println("Employee could not be created.");
            System.exit(-1);
        }
    }

    public void changeEmployeeInformation(Employee employee) {

        employee.setUpdatedBy(generateRandomName(10));

        if (employeeRepository.update(employee)) {

            System.out.println(String.format("Employee %d updated.", employee.getId()));
            printEmployeeList(employeeRepository.readAll().iterator(), System.out);
        } else {

            System.out.println(String.format("Employee %d could not be updated.", employee.getId()));
            System.exit(-1);
        }
    }

    public void offboardEmployee(Employee employee) {

        if(employeeRepository.delete(employee)) {

            System.out.println(String.format("Employee %d deleted.", employee.getId()));
            printEmployeeList(employeeRepository.readAll().iterator(), System.out);
        } else {

            System.out.println(String.format("Employee %d could not be deleted.", employee.getId()));
            System.exit(-1);
        }
    }

}
