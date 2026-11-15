<p align="center">
  <img
    src="https://i.pinimg.com/736x/85/21/49/852149a7474d13fd6d363575d58fecee.jpg"
    width="680"
  />
</p>

# Personal Library — V.2

A minimal personal book library for Android, built with Java and XML. Version 2 delivers the first functional application foundation, local book management, optional real-cover lookup, and English/Portuguese localization.

> **Project status:** Development build. The Android source and CI emulator smoke-test workflow are present on the `feature/android-app-foundation` branch. The emulator workflow has been configured but has not yet reported a completed run.

## Features

- Add, view, edit, search and delete books.
- Store each book's title, author, ISBN, personal rating and notes.
- Search by title, author or ISBN.
- Retrieve real book covers through the Google Books API.
- Search by ISBN when available, with title and author as a fallback.
- Persist book records locally in JSON.
- Use the interface in English or Portuguese, following the device language.
- Browse saved book records offline.

Book records are stored in the app's private internal storage. Internet access is only needed for optional cover lookup and remote cover-image loading; a failed lookup does not prevent a book from being saved.

## Technology

| Technology | Purpose |
| --- | --- |
| Java 17 | Application logic and data model |
| XML | Android layouts and localized resources |
| Android SDK 36 | Android platform and build target |
| Android Gradle Plugin 8.13.2 | Android build configuration |
| Gradle 8.13 | Build automation |
| Google Books API | Optional book-cover lookup |
| Picasso 2.8 | Remote cover-image loading |
| JSON | Local book-record persistence |

The application does not require an account, backend, remote database or cloud synchronization.

## Project structure

```text
Personal-Library-Android/
├── .github/
│   └── workflows/
│       └── android-emulator.yml
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
- Android SDK Platform 36.
- An Android emulator or physical Android device (minimum Android 6.0 / API 23).
- Internet access only if you want to retrieve or load online book covers.

## Installation and execution

### Option 1 — Android Studio

1. Clone the repository:

   ```bash
   git clone https://github.com/lzakLP/Personal-Library-Android.git
   ```

2. Open the project directory in Android Studio.
3. Switch to the development branch:

   ```bash
   git checkout feature/android-app-foundation
   ```

4. Configure JDK 17 in Android Studio if it is not already selected.
5. Install Android SDK Platform 36 through **Tools → SDK Manager** if required.
6. Wait for Gradle synchronization to finish and allow Android Studio to download the dependencies.
7. Start an emulator or connect a physical device with USB debugging enabled.
8. Select the `app` run configuration and click **Run**.

### Option 2 — Command line

The project currently does not include a Gradle Wrapper. Install Gradle 8.13 and configure the Android SDK, then run the following commands from the repository root:

```bash
gradle --version
gradle assembleDebug
```

To install the generated debug APK on a running emulator or connected device:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.lzaklp.personallibrary/.MainActivity
```

## Data and privacy

- Book records are saved in `personal_library.json` inside the application's private internal storage.
- Records remain available after normal application restarts.
- Clearing application data or uninstalling the app removes locally stored records.
- No account, server-side user database or synchronization service is used.
- Google Books requests are optional and are used to find cover images; they are not required for basic library management.

## Version 2 updates

- Established the native Android project structure using Java and XML.
- Added the book data model and local JSON storage layer.
- Implemented book creation, listing, details, editing and deletion.
- Added search by title, author and ISBN.
- Added personal ratings and notes.
- Added optional Google Books cover lookup and Picasso image loading.
- Added Portuguese and English string resources.
- Preserved the existing project banner image from the original README.
- Added a GitHub Actions workflow for an Android debug build and emulator launch smoke test.

## Simulated environment test — CI emulator smoke test

A GitHub Actions workflow is included at `.github/workflows/android-emulator.yml`. It is configured to run on pushes to `feature/android-app-foundation`, pull requests targeting `main`, and manual workflow dispatch.

The planned CI sequence is:

1. Check out the repository source.
2. Set up JDK 17, Gradle 8.13 and the Android SDK.
3. Build the debug APK with `gradle assembleDebug`.
4. Start an Android Emulator using the Pixel 2 profile, x86_64 image and API level 35 with Google APIs.
5. Install the generated APK using `adb install`.
6. Launch `com.lzaklp.personallibrary/.MainActivity`.
7. Check that the application process is running using `adb shell pidof`.

**Execution status:** This is a configured automated smoke-test scenario, not a passed test report. At the time this README was updated, GitHub Actions had returned no workflow runs for the repository. Therefore, the build, installation, launch and process check have not yet been confirmed in a running emulator. No claim of successful emulator testing is being made.

## Known limitations and improvement opportunities

1. **Android edge-to-edge and system insets:** Review window insets and safe content padding for Android 15 and 16, particularly with target SDK 36.
2. **Search empty state:** Improve the empty-state messaging so an empty library is clearly distinguished from a search that returns no matches.
3. **Persistence error handling:** Handle and report storage failures consistently for deletion and asynchronous cover updates, as well as book creation and editing.
4. **Automated test coverage:** Add unit tests for the book model and JSON storage, plus instrumentation tests for creating, editing, searching, deleting and restoring books after an application restart.
5. **Network and image failure cases:** Test Google Books timeouts, unavailable results, malformed responses, offline mode and image-loading failures.
6. **Android version coverage:** Run build and UI checks across representative API levels, including API 23 or 26, API 31, API 35 and API 36.
7. **Gradle Wrapper:** Add the official Gradle Wrapper so developers and CI use a consistent Gradle version without requiring a separate global installation.
8. **Backup and export:** Consider a user-controlled export/import feature to protect locally stored book records.

---

**Development branch:** `feature/android-app-foundation`  
**Default branch:** `main` (not modified as part of this update).
