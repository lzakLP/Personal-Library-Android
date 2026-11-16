<p align="center">
  <img
    src="https://i.pinimg.com/736x/85/21/49/852149a7474d13fd6d363575d58fecee.jpg"
    alt="Personal Library project artwork"
    width="680"
  />
</p>

# Personal Library

An Android application concept for organizing reading history, rating books, bookmarking pages, and saving personal notes.

> **Project status:** In development. The repository currently contains project documentation and configuration files only. The Android application source code and Gradle project have not yet been committed, so the app cannot currently be built or launched from this repository.

## Project overview

Personal Library is intended to work as a personal reading journal.

The planned experience focuses on recording books, reviewing reading progress, and keeping notes and memorable passages in one place.

The initial concept is an offline-first application for individual use, without account creation or a remote service.

## Planned features

- **Book records:** Save each book's title and author.
- **Reading list:** Browse saved books.
- **Editing and deletion:** Update or remove book records.
- **Ratings:** Assign a personal rating to each book.
- **Bookmarks:** Save page numbers or passages for later reference.
- **Notes:** Record opinions, reflections, and comments.
- **Search:** Find books by title.
- **Filters:** Filter by author, rating, and bookmark status.
- **Local persistence:** Keep records available after closing and reopening the app.

## Proposed technology stack

| Technology | Intended purpose |
| --- | --- |
| Java | Application logic and data models |
| Android SDK | Android platform and application components |
| XML | Screen layouts |
| JSON | Local data storage |
| Android Studio | Development environment |
| Gradle | Build automation |

These technologies describe the planned implementation, not a currently buildable application.

## Storage and offline behavior

The current design proposes storing book entries, ratings, bookmarks, and notes as JSON files in the app's internal storage.

The intended application will:

- Work without an internet connection.
- Require no account or login.
- Store data locally on the device.
- Avoid cloud synchronization in the initial scope.

Local data may be removed if the app's data is cleared or the application is uninstalled. Export and backup functionality are not part of the initial scope.

## Development roadmap

- [ ] Create the Android Studio project using Java and XML.
- [ ] Define the book data model.
- [ ] Implement book creation and listing.
- [ ] Add a book details screen.
- [ ] Implement editing and deletion.
- [ ] Add ratings and notes.
- [ ] Add page and passage bookmarks.
- [ ] Implement search and filters.
- [ ] Implement JSON-based local persistence.
- [ ] Verify offline behavior and data persistence.
- [ ] Review interface usability.
- [ ] Add screenshots and a demonstration.
- [ ] Document build requirements and installation steps.

## Build and run

Build instructions will be added after the Android Studio project and source code are committed.

Once available, the expected workflow will be:

1. Clone the repository.
2. Open the Android project in Android Studio.
3. Install the SDK components required by the project.
4. Wait for Gradle synchronization.
5. Start an emulator or connect an Android device.
6. Build and run the application.

The minimum Android version and Gradle requirements have not yet been established.

## Initial scope

The first version is intended to focus on recording and browsing personal reading history.

The following features are outside the initial scope:

- Social networking and public profiles.
- Book lending management.
- External book catalogs.
- Cloud synchronization.
- Data export and backup.
