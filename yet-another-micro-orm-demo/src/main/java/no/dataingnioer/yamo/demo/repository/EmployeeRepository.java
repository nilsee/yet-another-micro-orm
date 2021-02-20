package no.dataingnioer.yamo.demo.repository;

import java.util.Collection;
import java.util.LinkedList;

import no.dataingenioer.yamo.core.MicroORM;
import no.dataingenioer.yamo.core.YetAnotherMicroORM;
import no.dataingenioer.yamo.core.utils.ConnectionSettings;
import no.dataingnioer.yamo.demo.model.Employee;

public class EmployeeRepository {

    private ConnectionSettings connectionSettings;

    private MicroORM orm;

    public EmployeeRepository(ConnectionSettings connectionSettings) {
        this.connectionSettings = connectionSettings;
    }

    private MicroORM getOrm() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(this.orm == null ) {
            this.orm = new YetAnotherMicroORM(this.connectionSettings);
        }
        return this.orm;
    }

    public Employee create(Employee employee) {
        String sql = "INSERT INTO Employee(email, first_name, last_name) VALUES (:email, :first_name, :last_name)";
        try {
            return getOrm().insert(sql, employee);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Employee read(int id) {
        String sql = "SELECT * FROM Employee WHERE Id = %d";
        try {
            return getOrm().selectSingle(sql, Employee.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Collection<Employee> readAll() {
        String sql = "SELECT * FROM Employee";
        try {
            return getOrm().select(sql, Employee.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new LinkedList<Employee>();
        }
    }

    public boolean update(Employee employee) {
        String sql = "UPDATE Employee SET email = :email, first_name = :first_name, last_name = :last_name WHERE id = :id";
        try {
             getOrm().update(sql, employee);
             return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean delete(Employee employee) {
        String sql = "DELETE FROM Employee WHERE id = :id";
        try {
            getOrm().delete(sql, employee);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
