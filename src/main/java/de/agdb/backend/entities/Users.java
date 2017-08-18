package de.agdb.backend.entities;


import de.agdb.backend.entities.schedule_wrapper_objects.ScheduleWrapper;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
public class Users implements Serializable {


    // An autogenerated id (unique for each user in the db)
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "USERNAME")
    private String username;


    @NotNull
    @Column(name = "PASSWORD")
    private String password;


    // @NotNull
    @Column(name = "EMAIL")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Contact.class)
    @JoinTable(name = "USER_CONTACT", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "CONTACT_ID")})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Contact> contacts;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Categories.class)
    @JoinTable(name = "USER_CATEGORY", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID")})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Categories> categories;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ScheduleWrapper.class)
    @JoinTable(name = "USER_SCHEDULE", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "SCHEDULE_ID")})
    private List<ScheduleWrapper> schedules;


    // Public methods
    public Users() {
    }

    public List<Categories> getCategoriesStartingWith(String firstLetter) {
        List<Categories> resultList = new ArrayList<>();
        List<Categories> allCategories = getCategories();
        for (int i = 0; i < allCategories.size(); i++) {
            if (allCategories.get(i).getTitle().substring(0, 1).compareToIgnoreCase(firstLetter) == 0) {
                resultList.add(allCategories.get(i));
            }
        }

        return resultList;
    }

    public boolean hasCategoryStartingWith(String firstLetter) {
        boolean result = false;
        List<Categories> allCategories = getCategories();
        for (int i = 0; i < allCategories.size(); i++) {
            if (allCategories.get(i).getTitle().substring(0,1).compareToIgnoreCase(firstLetter) == 0) {
                result = true;
                break;
            }

        }
        return result;
    }


    // getters/setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String userPassword) {
        password = userPassword;
    }


    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public List<ScheduleWrapper> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleWrapper> schedules) {
        this.schedules = schedules;
    }

    // basic access

    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    public void addCategory(Categories category) {
        this.categories.add(category);
    }

    public void addSchedule(ScheduleWrapper schedule) {
        this.schedules.add(schedule);
    }

    public void removeSchedule(ScheduleWrapper schedule) {
        this.schedules.remove(schedule);
    }


}
