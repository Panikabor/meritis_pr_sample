package fr.meritis.prsample.datamodel;

import java.util.*;


public class User {
    private final String id;
    private final List<Document> borrowed = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String email;

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


    public String getFullName() {
        return getFirstName() + " " + getLastName();
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
        document.setBorrowedBy(this);
        borrowed.add(document);
        IO.println(document.getTitle() + " was borrowed by user: " + getFullName());
    }

    public void returnDocument(Document document) {
        if (!borrowed.contains(document)) {
            throw new IllegalArgumentException(document + " was not borrowed by " + this);
        }
        document.giveBack();
        borrowed.remove(document);
        IO.println(document.getTitle() + " returned by " + getFullName());
    }

    public List<Document> getBorrowed() {
        return Collections.unmodifiableList(borrowed);
    }
}
