package no.dataingnioer.yamo.demo.repository;

import java.util.Collection;
import java.util.LinkedList;

import no.dataingenioer.yamo.core.MicroORM;
import no.dataingenioer.yamo.core.YetAnotherMicroORM;
import no.dataingenioer.yamo.core.query.QueryBuilder;
import no.dataingenioer.yamo.core.query.SimpleSqlBuilder;
import no.dataingenioer.yamo.core.configuration.ConnectionSettings;
import no.dataingenioer.yamo.core.repository.EntityRepository;
import no.dataingnioer.yamo.demo.model.Employee;

/**
 * Demo entity repository for Employee, implemented using MicorORM and QueryBuilder
 *
 * @Author Nils Einar Eide
 * @Email nils@dataingenioer.no
 */
public class EmployeeRepository implements EntityRepository<Employee>
{

    /**
     *
     */
    private ConnectionSettings connectionSettings;

    /**
     *
     */
    private MicroORM orm;

    /**
     *
     */
    private QueryBuilder queryBuilder;

    /**
     *
     * @param connectionSettings
     */
    public EmployeeRepository(ConnectionSettings connectionSettings) {

        this.connectionSettings = connectionSettings;
        this.queryBuilder = new SimpleSqlBuilder(Employee.class);
    }

    /**
     *
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    private MicroORM getOrmInstance()
            throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        if(this.orm == null) {

            this.orm = new YetAnotherMicroORM(this.connectionSettings);
        }

        return this.orm;
    }

    /**
     *
     * @param employee
     * @return
     */
    public Employee create(Employee employee) {

        // String sql = "INSERT INTO employee(email, first_name, last_name) VALUES (:email, :first_name, :last_name)";

        try {

            return getOrmInstance().insert(getQueryBuilder(), employee);
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public Employee read(int id) {

        // String sql = "SELECT * FROM employee WHERE id = %d";

        try {

            return getOrmInstance().selectSingle(getQueryBuilder(), Employee.class, id);
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public Collection<Employee> readAll() {

        // String sql = "SELECT * FROM employee";

        try {

            return getOrmInstance().select(getQueryBuilder(), Employee.class);
        } catch (Exception ex) {

            ex.printStackTrace();
            return new LinkedList<Employee>();
        }
    }

    /**
     *
     * @param employee
     * @return
     */
    public boolean update(Employee employee) {

        // String sql = "UPDATE employee SET email = :email, first_name = :first_name, last_name = :last_name WHERE id = :id";

        try {

             getOrmInstance().update(getQueryBuilder(), employee);
             return true;
        } catch (Exception ex) {

            ex.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param employee
     * @return
     */
    public boolean delete(Employee employee) {

        // String sql = "DELETE FROM employee WHERE id = :id";

        try {

            getOrmInstance().delete(getQueryBuilder(), employee);
            return true;
        } catch (Exception ex) {

            ex.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @return
     */
    public QueryBuilder getQueryBuilder() {

        return queryBuilder;
    }
}
