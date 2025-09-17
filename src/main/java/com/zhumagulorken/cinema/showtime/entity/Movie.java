package com.zhumagulorken.cinema.showtime.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private String duration;

    @Column(name = "rating")
    private BigDecimal rating;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Show> shows =  new ArrayList<>();

    // Getter and Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}
