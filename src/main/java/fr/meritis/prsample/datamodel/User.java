package fr.meritis.prsample.datamodel;

import java.util.*;


public class User {
    private final String id;
    private String firstName;
    private String lastName;
    private String email;
    private final List<Document> borrowed = new ArrayList<>();

    public User() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + '}';
    }

    public void borrowDocument(Document document) {
        System.out.println("Borrowing document: " + document.getTitle() + " by user: " + getFirstName() + " " + getLastName());
        document.setBorrowedBy(this);
        borrowed.add(document);
    }

    public void returnDocument(Document document) {
        System.out.println("Returning document: " + document.getTitle());
        document.giveBack();
        borrowed.remove(document);
    }

    public List<Document> getBorrowed() {
        return Collections.unmodifiableList(borrowed);
    }
}
