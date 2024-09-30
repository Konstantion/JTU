package org.example;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class Library {
  private final List<Book> books;

  private Library(List<Book> books) {
    this.books = books;
  }

  public static Library of(List<Book> books) {
    return new Library(List.copyOf(Objects.requireNonNull(books, "Books shouldn't be null")));
  }

  public List<Book> sortedBy(SortOption sortOption) {
    return books.stream()
        .sorted(Objects.requireNonNull(sortOption.comparator, "SortOption shouldn't be null"))
        .toList();
  }

  public enum SortOption {
    NAME(Comparator.comparing(Book::name, String::compareToIgnoreCase)),
    AUTHOR(Comparator.comparing(Book::author)),
    PUBLISHED_AT(Comparator.comparing(Book::publishedAt)),
    PRICE(Comparator.comparingDouble(Book::price));

    private final Comparator<Book> comparator;

    SortOption(Comparator<Book> comparator) {
      this.comparator = comparator;
    }
  }
}
