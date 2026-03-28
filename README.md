# NotesApp

A modern Android application for managing personal notes, built with **Jetpack Compose** and following **Clean Architecture** principles.

## Project Overview

NotesApp is designed to demonstrate a robust implementation of modern Android development tools and best practices. It features a reactive UI, a local persistent database, and a clear separation of concerns to ensure maintainability and testability.

## Features

- **Full CRUD Operations**: Create, Read, Update, and Delete notes seamlessly.
- **Persistent Storage**: Utilizes **Room** for a reliable local SQLite database.
- **Advanced Sorting**: Organize notes by Title, Date, or Color in both Ascending and Descending order.
- **Undo Functionality**: Accidental deletions can be reversed via an interactive Snackbar.
- **Note Customization**: Personalize notes with a variety of background colors.
- **Modern UI**: A responsive and fluid user interface built entirely with **Jetpack Compose**.
- **Type-safe Navigation**: Robust navigation between screens using the Compose Navigation component.

## Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Dependency Injection**: [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **Local Database**: [Room](https://developer.android.com/training/data-storage/room)
- **Asynchronous Programming**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
- **Navigation**: [Jetpack Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
- **Annotation Processing**: [KSP (Kotlin Symbol Processing)](https://kotlinlang.org/docs/ksp-overview.html)
- **Dependency Management**: Gradle Version Catalog (`libs.versions.toml`)
- **Architecture**: Clean Architecture with MVI/UDF (Unidirectional Data Flow)

## Architecture

The project is structured into three distinct layers to ensure a high degree of modularity and testability:

### 1. Domain Layer
The core of the application containing business logic. It is completely independent of other layers and frameworks.
- **Models**: Pure Kotlin data classes (e.g., `Note`).
- **Repositories**: Interfaces defining data operations.
- **Use Cases**: Encapsulated business logic for specific actions (e.g., `GetNotes`, `AddNote`, `DeleteNote`).

### 2. Data Layer
Handles data persistence and retrieval.
- **Room Database**: Manages the SQLite database and migrations.
- **DAOs**: Data Access Objects defining database queries.
- **Entities**: Room-specific data models.
- **Repository Implementation**: Concrete implementations of the domain repository interfaces.
- **Mappers**: Functions to convert between Data Entities and Domain Models.

### 3. Presentation Layer
Handles the UI and user interaction using a unidirectional data flow.
- **ViewModels**: Manage UI state using `State` and handle user `Events`.
- **State/Events**: Sealed classes defining the UI state and all possible user interactions.
- **Components**: Modular and reusable Compose UI elements (e.g., `NoteItem`, `OrderSection`).

## Project Structure

```text
com.bober.notesapp
├── data
│   ├── local (Room DB, DAOs, Entities)
│   └── repository (Repository Implementations)
├── di
│   └── AppModule (Hilt Modules)
├── domain
│   ├── model (Domain Data Classes)
│   ├── repository (Repository Interfaces)
│   ├── use_case (Business Logic / Interactors)
│   └── util (Sorting & Utility Logic)
└── presentation
    ├── add_edit_note (Add/Edit Screen & ViewModel)
    ├── notes (List Screen & ViewModel)
    ├── ui.theme (Compose Theme & Styling)
    └── util (Navigation & UI Helpers)
```
