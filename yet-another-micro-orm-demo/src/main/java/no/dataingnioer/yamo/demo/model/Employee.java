package no.dataingnioer.yamo.demo.model;

import no.dataingenioer.yamo.core.annotations.Entity;
import no.dataingenioer.yamo.core.annotations.Column;
import no.dataingenioer.yamo.core.annotations.Exclude;
import no.dataingenioer.yamo.core.annotations.Id;

@Entity
public class Employee {

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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) { this.email = email;  }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.fullName = this.firstName + " " + this.lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.fullName = this.firstName + " " + this.lastName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "ID: " + getId() +
                ", Epost: " + getEmail() +
                ", Fornavn: " + getFirstName() +
                ", Etternavn: " + getLastName();
    }

}