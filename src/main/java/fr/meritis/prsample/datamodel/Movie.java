package fr.meritis.prsample.datamodel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.meritis.prsample.json.DurationDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Movie extends Document {
    private List<String> actors;
    @JsonDeserialize(using = DurationDeserializer.class)
    private Duration duration;
    private List<MovieType> types;

    public Movie() {
        super();
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public void addActor(String actor) {
        if (actors == null) {
            actors = new ArrayList<>();
        }
        this.actors.add(actor);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public List<MovieType> getTypes() {
        return types;
    }

    public void setTypes(List<MovieType> types) {
        this.types = types;
    }

    public void addType(MovieType type) {
        if (types == null) {
            types = new ArrayList<>();
        }
        this.types.add(type);
    }

    @Override
    public String toString() {
        return "Movie{" + "id='" + getId() + '\'' + ", title='" + getTitle() + '\'' + ", authors=" + getAuthors() + "," +
                " pageNumber=" + actors + ", duration=" + duration + ", types=" + types + '}';
    }
}
