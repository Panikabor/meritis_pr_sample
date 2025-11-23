package fr.meritis.prsample.datamodel;

import java.util.Collections;
import java.util.List;

public class Book extends Document {
    private int pageNumber;
    private List<BookType> types;

    public Book() {
        super();
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<BookType> getTypes() {
        return types == null ? Collections.emptyList() : Collections.unmodifiableList(types);
    }

    public void setTypes(List<BookType> types) {
        this.types = types;
    }

    public void addType(BookType type) {
        this.types.add(type);
    }

    @Override
    public String toString() {
        return "Book{" + "id='" + getId() + '\'' + ", title='" + getTitle() + '\'' + ", authors=" + getAuthors() + "," +
                " pageNumber=" + pageNumber + ", types=" + types + '}';
    }
}
