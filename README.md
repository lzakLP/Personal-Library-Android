# Personal Library

A personal reading journal for Android, built with Java and XML.

The app is designed for individual use and works offline. It does not require an account, a server connection, or a database.

## Features

- Add books with a title, author, personal rating, and notes.
- Browse the books saved in your library.
- Search by book title or author.
- Remove books from the library.
- Persist entries locally in a JSON file.
- Keep the app usable without an internet connection.

## Technology

| Technology | Purpose |
| --- | --- |
| Java | Application logic and data models |
| XML | Android layouts and resources |
| Android SDK | Native Android functionality |
| JSON | Local file-based persistence |
| Gradle | Build configuration |

The app uses AndroidX and Material Components for Android. It does not use SQLite, Room, Firebase, or a remote backend.

## Project structure

```text
Personal-Library-Android/
├── app/
│   ├── build.gradle
│   └── src/main/
│       ├── java/com/lzaklp/personallibrary/
│       │   ├── MainActivity.java
│       │   ├── Book.java
│       │   ├── BookAdapter.java
│       │   └── BookStorage.java
│       ├── res/
│       │   ├── layout/
│       │   ├── drawable/
│       │   └── values/
│       └── AndroidManifest.xml
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## Requirements

- Android Studio with support for Android Gradle Plugin 8.13.2.
- JDK 17.
- Android SDK 36.
- An Android emulator or a physical Android device (minimum Android 6.0 / API 23).

## Run the app

1. Clone the repository.
2. Open the repository root directory in Android Studio.
3. Allow Android Studio to sync the Gradle project and download the required dependencies.
4. Install Android SDK 36 if Android Studio requests it.
5. Start an emulator or connect an Android device with USB debugging enabled.
6. Select the `app` run configuration and click **Run**.

To build a debug APK from Android Studio, select **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

## Local storage

Book entries are stored in the app's private internal storage in a file named `personal_library.json`.

The file is managed by Android and is not directly exposed to other apps. Data remains available between app launches, but clearing app data or uninstalling the app removes the library.

There is no cloud synchronization. Export and backup are not implemented yet.

## Development roadmap

- [x] Set up the Android project with Java and XML.
- [x] Create the book data model.
- [x] Implement book creation and listing.
- [x] Add search by title and author.
- [x] Implement deletion.
- [x] Persist books using local JSON storage.
- [ ] Add a dedicated book details screen.
- [ ] Implement editing of existing books.
- [ ] Improve filters and reading status.
- [ ] Add bookmarks for pages and passages.
- [ ] Add automated tests for storage and book management.
- [ ] Add screenshots and a demo GIF.
- [ ] Implement export and backup.

## Scope

The initial version focuses on recording and browsing a personal reading list.

Social networking, public profiles, book lending, external book catalogs, and cloud synchronization are outside the initial scope.
