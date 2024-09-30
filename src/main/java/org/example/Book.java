package org.example;

import java.time.LocalDate;

public record Book(
    long id, String name, Genre genre, String author, double price, LocalDate publishedAt) {}
