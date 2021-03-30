package no.dataingnioer.yamo.demo.model;


import static java.time.LocalDate.now;
import static no.dataingnioer.yamo.demo.utils.MailUtils.generateRandomEmail;
import static no.dataingnioer.yamo.demo.utils.MailUtils.generateRandomName;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Helper class to generate test-cases
 */
public class EmployeeBuilder {

    /**
     *
     */
    protected Employee employee;

    /**
     *
     */
    protected Random random = new Random();

    /**
     *
     */
    public EmployeeBuilder() {

        employee = new Employee();
        employee.setCreatedBy(generateRandomName(10));
        employee.setCreated(LocalDateTime.now());
    }

    /**
     *
     * @param id
     * @return
     */
    public EmployeeBuilder withId(int id) {

        employee.setId(id);
        return this;
    }

    /**
     *
     * @param email
     * @return
     */
    public EmployeeBuilder withEmail(String email) {

        employee.setEmail(email);
        return this;
    }

    /**
     *
     * @return
     */
    public EmployeeBuilder withGeneratedEmail(String domain) {

        int count = randomCount(4, 8);
        String email = generateRandomEmail(count, domain);
        employee.setEmail(email);
        return this;
    }

    /**
     *
     * @return
     */
    public EmployeeBuilder withGeneratedEmail() {

        withGeneratedEmail("example.com");
        return this;
    }

    /**
     *
     * @param firstName
     * @return
     */
    public EmployeeBuilder withFirstName(String firstName) {

        employee.setFirstName(firstName);
        return this;
    }

    /**
     *
     * @return
     */
    public EmployeeBuilder withGeneratedFirstName() {

        int count = randomCount(2, 10);
        String firstName = generateRandomName(count);
        employee.setFirstName(firstName);
        return this;
    }

    /**
     *
     * @param lastName
     * @return
     */
    public EmployeeBuilder withLastName(String lastName) {

        employee.setLastName(lastName);
        return this;
    }

    /**
     *
     * @return
     */
    public EmployeeBuilder withGeneratedLastName() {

        int count = randomCount(4, 10);
        String lastName = generateRandomName(count);
        employee.setLastName(lastName);
        return this;
    }

    /**
     *
     * @return
     */
    public EmployeeBuilder withGeneratedEmployee() {

        return this
                 .withGeneratedEmail()
                 .withGeneratedFirstName()
                 .withGeneratedLastName();
    }

    /**
     *
     * @return
     */
    public Employee build(){

        return employee;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {

        return employee.toString();
    }

    /**
     *
     * @param min
     * @param max
     * @return
     */
    private int randomCount(int min, int max) {

        return random.nextInt((max - min) + 1) + min;
    }

}
