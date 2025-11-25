package fr.meritis.prsample;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.meritis.prsample.datamodel.Book;
import fr.meritis.prsample.datamodel.Document;
import fr.meritis.prsample.datamodel.Movie;
import fr.meritis.prsample.datamodel.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static fr.meritis.prsample.Configuration.BOOK_CATALOG_PATH;
import static fr.meritis.prsample.Configuration.MOVIE_CATALOG_PATH;
import static fr.meritis.prsample.Configuration.USER_LIST_PATH;

public class MediaLibrary {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Map<String, User> usersById = new HashMap<>();
    private final Map<String, Document> documentsById = new HashMap<>();

    public MediaLibrary() throws IOException {
        loadUsers();
        loadDocuments();
    }

    private void loadUsers() throws IOException {
        List<User> users = OBJECT_MAPPER.readValue(getClass().getClassLoader()
                .getResourceAsStream(USER_LIST_PATH), OBJECT_MAPPER.getTypeFactory()
                .constructCollectionType(List.class, User.class));
        users.forEach((user) -> usersById.put(user.getId(), user));
    }

    private void loadDocuments() throws IOException {
        List<Movie> movies = OBJECT_MAPPER.readValue(getClass().getClassLoader()
                .getResourceAsStream(MOVIE_CATALOG_PATH), OBJECT_MAPPER.getTypeFactory()
                .constructCollectionType(List.class, Movie.class));
        movies.forEach((movie) -> documentsById.put(movie.getId(), movie));
        List<Book> books = OBJECT_MAPPER.readValue(getClass().getClassLoader()
                .getResourceAsStream(BOOK_CATALOG_PATH), OBJECT_MAPPER.getTypeFactory()
                .constructCollectionType(List.class, Book.class));
        books.forEach((movie) -> documentsById.put(movie.getId(), movie));
    }

    void main() throws IOException {
        IO.println("Welcome to media library");
        MediaLibrary ml = new MediaLibrary();
        firstUserBorrowsFirstMovie(ml);
        User indy = indyBorrowsPotter(ml);
        printBorrowedDocuments(ml);
        indy.returnDocument(indy.getBorrowed().getFirst());
        printBorrowedDocuments(ml);
    }

    private static void firstUserBorrowsFirstMovie(MediaLibrary ml) {
        User firstUser = ml.usersById.values().iterator().next();
        List<Document> movies = ml.documentsById.values().stream().filter(document -> document instanceof Movie)
                .toList();
        Document firstMovie = movies.getFirst();
        firstUser.borrowDocument(firstMovie);
    }

    private static User indyBorrowsPotter(MediaLibrary ml) {
        User indy = ml.findUser("indiana.jones@hunter.cuny.edu").orElseThrow();
        List<Document> potterDocs = ml.findDocuments("Potter");
        Document firstPotterDoc = potterDocs.getFirst();
        indy.borrowDocument(firstPotterDoc);
        return indy;
    }

    private static void printBorrowedDocuments(MediaLibrary ml) {
        ml.documentsById.values().stream().filter(document -> document.getBorrowedBy() != null)
                .forEach(MediaLibrary::printBorrowedDocument);
    }

    private static void printBorrowedDocument(Document document) {
        IO.println(document + " has been borrowed by " + document.getBorrowedBy() + " and should be returned on " + document.getExpectedReturn());
    }

    private Optional<User> findUser(final String email) {
        return usersById.values().stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

    private List<Document> findDocuments(final String title) {
        return documentsById.values().stream()
                .filter(document -> document.getTitle().toLowerCase().contains(title.toLowerCase())).toList();
    }
}
