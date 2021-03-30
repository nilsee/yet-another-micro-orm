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

/**
 *
 */
public class Application {

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String [] args) throws IOException {

        HRSystem hrSystem = new HRSystem();

        Employee employee = hrSystem.init();

        hrSystem.newEmplyee(employee);

        employee.setFirstName("Per");
        employee.setLastName("Hansen");
        hrSystem.changeEmployeeInformation(employee);

        hrSystem.offboardEmployee(employee);

    }

}
