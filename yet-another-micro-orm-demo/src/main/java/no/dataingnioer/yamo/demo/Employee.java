package no.dataingnioer.yamo.demo;

import no.dataingenioer.yamo.core.annotations.Entity;
import no.dataingenioer.yamo.core.annotations.Column;
import no.dataingenioer.yamo.core.annotations.Exclude;

@Entity
public class Employee {

    private int id;

    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Exclude
    private String fullName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.fullName = this.firstName + " " + this.lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.fullName = this.firstName + " " + this.lastName;
    }

    public String getFullName() {
        return fullName;
    }
}