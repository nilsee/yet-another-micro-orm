package no.dataingnioer.yamo.demo;

public class EmployeeBuilder {

    private Employee employee;

    public EmployeeBuilder() {
        employee = new Employee();
    }

    public EmployeeBuilder withId(int id){
        employee.setId(id);
        return this;
    }

    public EmployeeBuilder withEmail(String email){
        employee.setEmail(email);
        return this;
    }

    public EmployeeBuilder withFirstName(String firstName){
        employee.setFirstName(firstName);
        return this;
    }

    public EmployeeBuilder withLastName(String lastName){
        employee.setLastName(lastName);
        return this;
    }

    public Employee build(){
        return employee;
    }
}
