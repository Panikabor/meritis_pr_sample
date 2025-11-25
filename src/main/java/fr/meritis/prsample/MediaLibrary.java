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

public class MediaLibrary {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Map<String, User> usersById = new HashMap<>();
    private final Map<String, Document> documentsById = new HashMap<>();

    public MediaLibrary() throws IOException {
        loadUsers();
    }

    private void loadUsers() throws IOException {
        List<User> users = OBJECT_MAPPER.readValue(getClass().getClassLoader()
                .getResourceAsStream("users.json"), OBJECT_MAPPER.getTypeFactory()
                .constructCollectionType(List.class, User.class));
        users.forEach((user) -> usersById.put(user.getId(), user));
        List<Movie> movies = OBJECT_MAPPER.readValue(getClass().getClassLoader()
                .getResourceAsStream("movies.json"), OBJECT_MAPPER.getTypeFactory()
                .constructCollectionType(List.class, Movie.class));
        movies.forEach((movie) -> documentsById.put(movie.getId(), movie));
        List<Book> books = OBJECT_MAPPER.readValue(getClass().getClassLoader()
                .getResourceAsStream("books.json"), OBJECT_MAPPER.getTypeFactory()
                .constructCollectionType(List.class, Book.class));
        books.forEach((movie) -> documentsById.put(movie.getId(), movie));
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to media library");
        MediaLibrary ml = new MediaLibrary();
        firstUserBorrowsFirstMovie(ml);
        User indy = indyBorrowsPotter(ml);
        printBorrowed(ml);
        indy.returnDocument(indy.getBorrowed().get(0));
        printBorrowed(ml);
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

    private static void printBorrowed(MediaLibrary ml) {
        ml.documentsById.values().stream().filter(document -> document.getBorrowedBy() != null)
                .forEach(document -> System.out.println(document + " has been borrowed by " + document.getBorrowedBy() + " and should be returned on " + document.getExpectedReturn()));
    }

    private Optional<User> findUser(final String email) {
        return usersById.values().stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

    private List<Document> findDocuments(final String title) {
        return documentsById.values().stream()
                .filter(document -> document.getTitle().toLowerCase().contains(title.toLowerCase())).toList();
    }
}
