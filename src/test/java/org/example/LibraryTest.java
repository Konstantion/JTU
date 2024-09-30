package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LibraryTest {
  private Library library;

  @BeforeEach
  void setUp() {
    List<Book> books =
        List.of(
            new Book(1, "A", Genre.FANTASY, "E", 6.0, LocalDate.of(2000, 10, 4)),
            new Book(2, "B", Genre.SCI_FI, "D", 7.0, LocalDate.of(2000, 10, 2)),
            new Book(3, "C", Genre.SCI_FI, "C", 10.0, LocalDate.of(2000, 10, 1)),
            new Book(4, "D", Genre.SCI_FI, "B", 9.0, LocalDate.of(2000, 10, 3)),
            new Book(5, "E", Genre.SCI_FI, "A", 8.0, LocalDate.of(2000, 10, 5)));
    library = Library.of(books);
  }

  private static void assertIdEqualsSequentially(List<Book> books, int... ids) {
    if (Objects.requireNonNull(books, "Books shouldn't be null").size() != ids.length) {
      fail(
          "Mismatch of size for params for idEqualsSequentially, books size %s, ids size: %s"
              .formatted(books.size(), ids.length));
    }

    for (var i = 0; i < books.size(); i++) {
      assertEquals(books.get(i).id(), ids[i]);
    }
  }

  @Test
  void shouldFail() {
    fail("Intentional fail");
  }

  @Test
  void shouldSortByName() {
    var sorted = library.sortedBy(Library.SortOption.NAME);

    assertIdEqualsSequentially(sorted, 1, 2, 3, 4, 5);
  }

  @Test
  void shouldSortByAuthor() {
    var sorted = library.sortedBy(Library.SortOption.AUTHOR);

    assertIdEqualsSequentially(sorted, 5, 4, 3, 2, 1);
  }

  @Test
  void shouldSortByPublishedAt() {
    var sorted = library.sortedBy(Library.SortOption.PUBLISHED_AT);

    assertIdEqualsSequentially(sorted, 3, 2, 4, 1, 5);
  }

  @Test
  void shouldSortByPrice() {
    var sorted = library.sortedBy(Library.SortOption.PRICE);

    assertIdEqualsSequentially(sorted, 1, 2, 5, 4, 3);
  }

  @Test
  void shouldReturnEmptyWhenLibraryEmpty() {
    var sorted = Library.of(List.of()).sortedBy(Library.SortOption.NAME);

    assertTrue(sorted.isEmpty());
  }

  @Test
  void shouldReturnSameBookWhenOneBook() {
    var expected = new Book(1, "A", Genre.FANTASY, "A", 1.0, LocalDate.of(2000, 1, 1));
    var library = Library.of(List.of(expected));

    assertEquals(expected, library.sortedBy(Library.SortOption.NAME).getFirst());
    assertEquals(expected, library.sortedBy(Library.SortOption.AUTHOR).getFirst());
    assertEquals(expected, library.sortedBy(Library.SortOption.PRICE).getFirst());
    assertEquals(expected, library.sortedBy(Library.SortOption.PUBLISHED_AT).getFirst());
  }

  @Test
  void shouldThrowNPEWhenNullBooks() {
    assertThrows(NullPointerException.class, () -> Library.of(null));
  }

  @Test
  void shouldThrowNPEWhenNullSortOption() {
    assertThrows(NullPointerException.class, () -> Library.of(List.of()).sortedBy(null));
  }

  @Test
  void shouldThrowNPEWhenNullPassedAsBook() {
    assertThrows(
        NullPointerException.class,
        () ->
            Library.of(
                new ArrayList<>() { // nullable array
                  {
                    add(null);
                  }
                }));
  }
}
