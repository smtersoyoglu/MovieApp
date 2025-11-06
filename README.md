# <img src="https://github.com/user-attachments/assets/42a58f81-fd38-45a5-b7c4-8bf500194f7e" alt="logo" width="50" height="60" />  MovieApp 
**Modern Android Movie Application built with Jetpack Compose, Clean Architecture, and TMDB API**  

---

##  Overview  
MovieApp is a modern Android application developed using **Jetpack Compose**, **MVVM**, and **Clean Architecture** principles.  
It provides users with a seamless experience to explore trending, popular, top-rated, and upcoming movies.  
Users can register and log in via **Firebase Authentication**, browse and search movies from **TMDB API**, save favorites locally using **Room Database**, and view detailed information about movies and actors.

---

##  Architecture  
The project follows **Clean Architecture** with **modular separation** of concerns:
```bash
com.smtersoyoglu.movieapp
│
├── data                         # Data layer: handles all data operations
│   ├── mapper                   # Maps DTOs ↔ Domain Models
│   ├── repository               # Repository implementations
│   │   └── source               # Data sources
│   │       ├── local            # Local data (Room Database)
│   │       ├── remote           # Remote data (TMDB API Service)
│   │       └── paging           # Paging sources for endless scroll
│   │           ├── MoviePagingSource.kt
│   │           └── SearchPagingSource.kt
│   ├── dto                      # Data Transfer Objects (API models)
│   ├── service                  # Retrofit API definitions
│   └── database                 # Room setup, entities, and DAO interfaces
│
├── domain                       # Business logic layer
│   ├── model                    # Domain Models
│   ├── repository               # Repository interfaces
│   └── usecase                  # UseCases (Single responsibility per feature)
│
├── presentation                 # UI layer with Jetpack Compose
│   ├── screen                   # Screens (Home, Detail, Search, Favorites, etc.)
│   ├── component                # Reusable Compose UI components
│   ├── viewmodel                # ViewModels (one per feature)
│   └── state                    # UI states and events
│
├── di                           # Hilt Dependency Injection Modules
│
├── navigation                   # Navigation Graph and Routes
│
└── common                       # Utilities, Constants, and Extension functions
 ```
---
## 🌟 App Features  

| Category                       | Description                                                        |
| ------------------------------ | ------------------------------------------------------------------ |
| 🔑 **Firebase Authentication** | Secure and easy login/register functionality                       |
| 🎬 **Movie Discovery**         | Explore trending, popular, top-rated, and upcoming movies          |
| 🔍 **Search with Pagination**  | Efficient movie search with endless scroll powered by **Paging 3** |
| ❤️ **Favorites**               | Save and manage favorite movies locally using **Room**             |
| 🎞️ **Movie Details**          | Rich information including genres, runtime, cast, and release date |
| ▶️ **Trailer Playback**        | Watch movie trailers directly via **YouTubePlayerView**            |
| ✨ **Custom Animations**        | **Lottie animations** for loading, empty, and error states         |
| 🎨 **Modern Compose UI**       | Built entirely with **Jetpack Compose** and **Material 3**         |
| 🌑 **Cinematic Theme**         | Dark UI with elegant white, red, and gold accent colors            |
| ⚡ **Reactive Performance**     | Powered by **Kotlin Flow** & **Coroutines** for real-time updates  |


---
## ⚙️ Technical Highlights

| Feature              | Implementation                                       |
| -------------------- | ---------------------------------------------------- |
| 🧩 **Architecture**  | Clean Architecture (Data-Domain-Presentation) + MVVM |
| 🌐 **Networking**    | Retrofit + Gson for RESTful API communication        |
| 💾 **Local Storage** | Room Database (Favorites)                            |
| 📡 **Pagination**    | Paging 3 (used in Home & Search screens)             |
| 🧠 **Reactivity**    | Kotlin Coroutines + Flow                             |
| 🧭 **Navigation**    | Navigation Compose + Hilt integration                |
| 🔠 **Typography**    | Custom Google Fonts for UI consistency               |
| 🌈 **System UI**     | Accompanist System UI Controller                     |
| 🧪 **Testing**       | JUnit & Espresso                                     |

---
## 🧰 Tech Stack

| Category                | Libraries / Tools                |
| ----------------------- | -------------------------------- |
| 💻 **Language**         | Kotlin                           |
| 🎨 **UI Framework**     | Jetpack Compose + Material 3     |
| 🧠 **Architecture**     | Clean Architecture + MVVM        |
| 🧩 **DI Framework**     | Hilt (Dagger)                    |
| 🌐 **Networking**       | Retrofit + Gson                  |
| 💾 **Database**         | Room                             |
| 📡 **Pagination**       | Paging 3                         |
| 🖼️ **Image Loading**   | Coil                             |
| ⚙️ **Async / Reactive** | Kotlin Coroutines & Flow         |
| 🔐 **Authentication**   | Firebase Auth                    |
| ✨ **Animations**        | Lottie                           |
| 🧭 **Navigation**       | Navigation Compose               |
| 🎥 **Video Playback**   | YouTubePlayerView                |
| 🔠 **Typography**       | Google Fonts                     |
| 🌈 **System UI**        | Accompanist System UI Controller |
| 🧪 **Testing**          | JUnit, Espresso                  |

---
## 📱 Screens
| Screen                  | Description                                                   |
| ----------------------- | ------------------------------------------------------------- |
| 🔐 **Login / Register** | User authentication via Firebase                              |
| 🏠 **Home**             | Displays trending, popular, and upcoming movies (with Paging) |
| 🔍 **Search**           | Search and browse movies (with Paging)                        |
| 🎞️ **Detail**          | Shows detailed movie info and trailer playback                |
| ❤️ **Favorites**        | Manage locally saved movies                                   |
| 👤 **Actor Detail**     | View detailed actor information and related movies            |

---
## 🚀 Future Improvements

| Feature                     | Description                                                              |
| --------------------------- | ------------------------------------------------------------------------ |
| 🌍 **Localization Support** | Multi-language support using **DataStore** for dynamic language settings |
| 📱 **Responsive Layouts**   | Optimize UI for tablets and large-screen devices                         |
| 💡 **Offline Mode**         | Cache movies locally to allow browsing without internet connection       |
| 🧠 **UI Testing**           | Add **Jetpack Compose Testing** support for automated UI validation      |


---
### Screenshoots


<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/05e53a87-8db1-4d93-b47b-efc888eb8173" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/f26a46b6-d74b-41cc-be33-f186bb08ce3a" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/42d4d8ab-2e7b-42cd-90a3-502b9d0154db" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/fa4c7c44-1546-4367-8dbd-7482c8ab55c2" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/cbc79b5f-2153-4af6-9b73-54bdf8e80dd3" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/38ee9645-a86e-4513-81d7-f2327b1a4b7e" width="290"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/9ab75721-5fa0-4b85-a901-76b39e835682" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/fd7796b4-14dd-4210-9086-e5d43cecfe79" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/0eb56e52-70be-45be-99d8-cd63f25a317e" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/b62610c9-b5a0-4ba6-a299-39af1ba76690" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/59e46e75-41a7-492d-896b-4785efb20d71" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/4f3b6b67-7aea-44b6-b134-e6893c8eb7f8" width="290"></td>
  </tr>
    <tr>
    <td><img src="https://github.com/user-attachments/assets/7e78183d-d458-4e71-9df7-c86afcd064eb" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/2e83bfc1-ea89-418b-96ec-4fad6694759e" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/2100c762-e52b-4c96-b325-e1b0e98d8ab1" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/90bd6e5c-3e89-4f8e-b892-c9002ad95d4d" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/a82e24fe-9f1d-4b6b-b51b-cb3cf5dd37d1" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/bab000b0-365f-49a5-ab41-97b9507dca87" width="290"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/64fa39b1-0cfc-4ba3-b777-249283116fcb" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/acba00d3-2a5a-48b6-93b8-3435830f9ed6" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/47b8d576-815d-4212-a41c-9c3889081e30" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/3b388bfd-f8e7-475d-81da-e5276f108d60" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/f57c3e32-b6d8-47e5-9380-12f39ce026c8" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/3bc71659-87fb-4a44-90b4-42d3b0069e37" width="290"></td>
  </tr>
</table>

---

## 🛠️ Installation & Setup

Follow the steps below to set up and run the **MovieApp** project locally:

| Step | Description |
| --- | --- |
| 🧩 **1. Clone the Repository** | <pre><code>git clone https://github.com/yourusername/MovieApp.git<br>cd MovieApp</code></pre> |
| 🔑 **2. Get TMDB API Key** | Obtain your API key from [The Movie Database (TMDB)](https://www.themoviedb.org/settings/api). |
| ⚙️ **3. Configure API Key** | Add your API key to the project's `local.properties` file: <pre><code>TMDB_API_KEY=your_api_key_here</code></pre> |
| 🔥 **4. Set up Firebase** | Add your `google-services.json` to the `app/` directory and enable **Email/Password** sign-in in the Firebase Console. |
| ▶️ **5. Run the App** | Open the project in **Android Studio (latest stable)**, sync Gradle, and click **Run ▶️** to launch on an emulator or device. |

> **Notes**
> - Minimum SDK: `24` (adjust in `build.gradle` if needed).  
> - JDK: `17` recommended.  
> - If you use CI, store `TMDB_API_KEY` and Firebase configs as secrets (do not commit them).

