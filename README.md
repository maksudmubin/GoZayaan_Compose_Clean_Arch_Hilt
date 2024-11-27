
# TripHaven - Compose Clean Architecture with Hilt

A modern Android application demonstrating a clean architecture approach, Jetpack Compose, and Hilt for dependency injection. This repository showcases best practices in Android development for building scalable, testable, and maintainable applications.

## ✨ Features

- **Jetpack Compose**: Fully declarative UI powered by Jetpack Compose.
- **Clean Architecture**: Separation of concerns with domain, data, and presentation layers.
- **Dependency Injection**: Powered by Hilt for easy and efficient dependency management.
- **Coroutines**: Asynchronous programming and reactive streams.
- **Material Design 3**: Adheres to modern UI/UX principles.
- **Error Handling**: Graceful error management with reusable patterns.

---

## 🚀 Technologies Used

- **Kotlin**: Primary programming language.
- **Jetpack Compose**: For declarative UI development.
- **Hilt**: Dependency injection framework.
- **Coroutines**: For background tasks and threading.
- **Retrofit**: Networking library for API communication.
- **Material Design 3**: Modern UI components and theming.

---

## 🛠 Architecture

This project follows **Clean Architecture**, ensuring the following:

1. **Presentation(UI) Layer**:
   - Contains `ViewModels` and UI-related logic using Jetpack Compose.
   - Handles state and events for the user interface.

2. **Domain Layer**:
   - Contains business logic, use cases, and interfaces.
   - Independent of any framework or platform-specific implementations.

3. **Data Layer**:
   - Handles data sources (e.g., REST APIs, databases).
   - Implements repository interfaces defined in the domain layer.

---

## 📂 Project Structure

```
GoZayaan_Compose_Clean_Arch_Hilt/
├── app/                      # Application module
│   ├── base/                 # Dependency injection setup, Utilities
│   ├── data/                 # Data layer (repositories, data sources)
│   ├── domain/               # Domain layer (use cases, interfaces)
│   ├── ui/                   # Presentation layer (UI, ViewModels, states)
└── build.gradle              # Build configuration
```

---

## 📦 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/maksudmubin/GoZayaan_Compose_Clean_Arch_Hilt.git
   ```
2. Open the project in **Android Studio** (Arctic Fox or later).
3. Sync the project with Gradle files.
4. Run the app on an emulator or physical device.

---

## 📝 Usage

- Explore the `presentation` package to understand UI development with Jetpack Compose.
- Check out the `domain` layer to see how use cases and business logic are structured.
- Dive into the `data` layer for REST API and database implementations.

---

## 💬 Contact

For any queries or suggestions, feel free to reach out:

- **Md. Maksudur Rahaman**
- [Linkedin Profile](https://www.linkedin.com/in/maksudmubin/)
- [GitHub Profile](https://github.com/maksudmubin)
