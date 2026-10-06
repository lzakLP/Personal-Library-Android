# Personal Library 

An Android application built with Java and XML to organize your reading history, rate books, bookmark pages, and save personal notes.

> **Status:** In development. The features below describe the planned project scope.

## About the Project

Personal Library is a personal reading journal for Android.

It offers a simple way to browse your reading history, find books by author, and revisit notes and memorable passages.

The app is designed for individual use, without requiring an account, an internet connection, or a database.

## Planned Features

- **Book entries:** Record each book's title and author.
- **Reading list:** View all saved books.
- **Editing and deletion:** Update book information or remove entries.
- **Ratings:** Give each book a personal rating.
- **Bookmarks:** Mark pages or passages to revisit later.
- **Comments:** Save opinions, reflections, and notes about each book.
- **Search:** Find books by title.
- **Filters:** Filter books by author, rating, and whether they have bookmarks.
- **Local persistence:** Keep entries available after closing and reopening the app.

## Technologies

| Technology | Purpose |
| --- | --- |
| Java | Application logic and data models |
| Android SDK | Android platform features and components |
| XML | Screen layouts |
| JSON | Planned format for storing entries in local files |
| Android Studio | Development environment |
| Gradle | Build automation and dependency management |

## Storage and Offline Access

Book entries, ratings, bookmarks, and comments will be saved as JSON files in the app's internal storage.

The project will not use SQLite, Room, Firebase, or other databases.

The app is designed to work entirely offline:

- No account or login required.
- No server connection required.
- No synchronization between devices.

> **Storage limitation:** Local files may be removed when clearing app data or uninstalling the app. Export and backup features are outside the initial scope.

## Getting Started

The instructions below apply once the Android project is available in this repository.

### Prerequisites

- Android Studio compatible with the project's configuration.
- The Android SDK required by the project.
- An Android emulator or a physical device with USB debugging enabled.

### Running the App

1. Clone this repository or download its contents.
2. Open the project folder in Android Studio.
3. Wait for Gradle synchronization to finish.
4. Install any SDK components requested by Android Studio.
5. Start an emulator or connect an Android device.
6. Select the target device.
7. Click **Run ▶** to build and launch the app.

> The minimum Android version and specific build requirements will be documented after the initial project setup.

## 🗺️ Development Roadmap

- [ ] Set up the Android project with Java and XML layouts.
- [ ] Create the book data model.
- [ ] Implement book creation and listing.
- [ ] Add a book details screen.
- [ ] Implement editing and deletion.
- [ ] Add ratings and comments.
- [ ] Add page and passage bookmarks.
- [ ] Implement search and filters.
- [ ] Save and load entries using JSON files.
- [ ] Verify offline functionality and data persistence.
- [ ] Review the interface and usability.
- [ ] Add screenshots and a demo GIF.
- [ ] Complete setup instructions and project documentation.

## Initial Scope

The first version focuses on recording and browsing personal reading history.

The following features are outside the initial scope:

- Social networking and public profiles.
- Book lending management.
- External book catalogs.
- Cloud synchronization.
- Data export and backup.
