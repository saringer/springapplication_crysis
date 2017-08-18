package de.agdb.backend.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    public Contact() {

    }

    public Contact(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // An autogenerated id (unique for each user in the db)
    @Id
    @Column(name = "CONTACT_ID", updatable=false, nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRSTNAME")
    private String firstName = "";

    @Column(name = "LASTNAME")
    private String lastName = "";

    @Column(name = "BIRTHDATE")
    private LocalDate birthDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "EMAIL")
    private String email = "";

    @Column(name = "AGE")
    private int age;

    @Column(name = "FUNCTION")
    private String function;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Categories.class)
    @JoinTable(name = "CONTACT_CATEGORY", joinColumns = {@JoinColumn(name = "CONTACT_ID")}, inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID")})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<String> assignedCategories;

    public List<String> getAssignedCategories() {
        return this.assignedCategories;
    }

    public void setAssignedCategories(List<String> list) {
        this.assignedCategories = list;
    }

    public void addCategory(String categoryTitle) {
        this.assignedCategories.add(categoryTitle);
    }

    public void removeCategory(String categoryTitle) {
        for (int i=0;i<assignedCategories.size();i++) {
            if (assignedCategories.get(i).equals(categoryTitle)) {
                assignedCategories.remove(assignedCategories.get(i));
                break;
            }
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the value of email
     *
     * @return the value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the value of email
     *
     * @param email
     *            new value of email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status
     *            new value of status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get the value of birthDate
     *
     * @return the value of birthDate
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Set the value of birthDate
     *
     * @param birthDate
     *            new value of birthDate
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Get the value of lastName
     *
     * @return the value of lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the value of lastName
     *
     * @param lastName
     *            new value of lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the value of firstName
     *
     * @return the value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean compareEmailAddress(String email) {
        return this.email.equals(email);
    }




}
