package no.dataingnioer.yamo.demo.model;

import no.dataingenioer.yamo.core.annotations.Column;
import no.dataingenioer.yamo.core.annotations.Entity;
import no.dataingenioer.yamo.core.annotations.Exclude;
import no.dataingenioer.yamo.core.annotations.Id;
import no.dataingenioer.yamo.core.repository.AbstractEntity;

@Entity(tableName = "employee")
public class Employee extends AbstractEntity {

    @Id
    @Column(name = "id")
    private int id;

    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Exclude
    private String fullName;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        resetFullName();
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        resetFullName();
    }

    public String getFullName() {
        resetFullName();
        return fullName;
    }

    @Override
    public String toString() {

        return String.format( "ID: %d, Epost: '%s', Fornavn: '%s', Etternavn: '%s', Created by: '%s', Updated by: '%s'"
                , getId()
                , getEmail()
                , getFirstName()
                , getLastName()
                , getCreatedBy()
                , getUpdatedBy());
    }

    @Override
    public int getId() {

        return id;
    }

    @Override
    public void setId(int id) {

        this.id = id;
    }

    private void resetFullName() {

        this.fullName = String.format("%s, %s", this.lastName, this.firstName);
    }

}