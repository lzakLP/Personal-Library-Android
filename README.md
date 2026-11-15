# Personal Library

A minimal personal reading journal for Android, built with Java and XML. The interface is available in Portuguese and English and follows the device language.

## Features

- Add, edit, view and remove books.
- Save title, author, ISBN, personal rating and notes.
- Search by title, author or ISBN.
- Look up real book covers through the Google Books API, using ISBN when provided and title/author as a fallback.
- Keep book records in private local JSON storage.
- Browse saved records offline; cover lookup requires an internet connection and is optional.

## Technology

| Technology | Purpose |
| --- | --- |
| Java 17 | Application logic and data model |
| XML | Android layouts and localized resources |
| Android SDK 36 | Native Android functionality |
| Google Books API | Optional book metadata and cover lookup |
| Picasso | Remote cover image loading and caching |
| JSON | Local file-based persistence |
| Gradle | Build configuration |

The app has no account, server-side user database, or cloud synchronization. Book records remain local to the device.

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
│       │   ├── values/
│       │   └── values-en/
│       └── AndroidManifest.xml
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## Requirements

- Android Studio with support for Android Gradle Plugin 8.13.2.
- JDK 17.
- Android SDK 36.
- An Android emulator or physical device running Android 6.0 / API 23 or later.

## Run

1. Clone the repository.
2. Check out the `feature/android-app-foundation` branch.
3. Open the repository root in Android Studio.
4. Allow Gradle to sync and download dependencies.
5. Install Android SDK 36 if requested.
6. Start an emulator or connect a device with USB debugging enabled.
7. Select the `app` run configuration and click **Run**.

A Gradle Wrapper has not been added yet. Use Android Studio's Gradle integration until the wrapper is configured.

## Storage and network behavior

Book data is stored in the app's private internal storage in `personal_library.json`. Data survives normal app restarts, but clearing app data or uninstalling the app removes it.

The Google Books lookup is an optional network feature. It runs after a book is saved; failure to retrieve a cover does not prevent the book from being stored. Existing book information remains available offline. Cover images are loaded from their saved image URLs when a connection is available.

## Roadmap

- [x] Java/XML Android project foundation.
- [x] Local JSON persistence.
- [x] Create, list, search and delete books.
- [x] Portuguese and English resources.
- [x] ISBN field and optional Google Books cover lookup.
- [x] Book details and editing.
- [ ] Reading status and genre filters.
- [ ] Bookmarks, quotes and reading history.
- [ ] Automated tests and backup/export.
- [ ] Screenshots and demo GIF.

## Scope

The initial release focuses on an individual, offline-first reading library. Social features, public profiles, lending workflows and cloud synchronization are out of scope.