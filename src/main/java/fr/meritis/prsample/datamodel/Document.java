package fr.meritis.prsample.datamodel;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Document {

    private final String id;
    private String title;
    private List<String> authors;
    private User borrowedBy;
    private LocalDate expectedReturn;

    public Document() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(User borrowedBy) {
        if (isAvailable()) {
            this.borrowedBy = borrowedBy;
            this.expectedReturn = LocalDate.now().plusMonths(1);
        } else {
            throw new IllegalStateException("Document is not available");
        }
    }

    public boolean isAvailable() {
        return borrowedBy == null;
    }

    public LocalDate getExpectedReturn() {
        return expectedReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Document document = (Document) o;
        return Objects.equals(id, document.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Document{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", authors=" + authors + '}';
    }

    public void giveBack() {
        if (!isAvailable()) {
            this.borrowedBy = null;
        }
    }
}
