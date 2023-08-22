# TMDB
TMDB is an Android application based on information from the TMDB API (https://www.themoviedb.org/).

## Techs Used
- [Kotlin](https://kotlinlang.org/) - is a programming language that makes coding concise, cross-platform, and fun.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - For asynchronous operations, providing a way to write asynchronous code in a more sequential and readable manner.
- [Android Architecture Components](https://developer.android.com/topic/architecture) - A collection of libraries that help you design robust, testable, and maintainable apps.
  - MVVM Architecture (Model - View - ViewModel): A design pattern that separates the UI components (View) from the data (Model) and uses a ViewModel to handle the communication between them.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes, making it lifecycle aware.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module, enabling easier code interactions with views
  - [Room Database](https://developer.android.com/training/data-storage/room) - An Android library that acts as an ORM (Object-Relational Mapping) and wraps Android's native SQLite database for easier database operations
  - Paging3 - Allows loading data in pages and presenting it in a RecyclerView for more efficient handling of large datasets.
- [Dagger-Hilt](https://dagger.dev/hilt/) - A standard library for incorporating Dagger dependency injection into an Android application, simplifying the setup and management of dependencies.
- [Retrofit & Gson](https://github.com/square/retrofit) - Used for constructing REST APIs and handling JSON serialization/deserialization.
- [OkHttp3](https://github.com/square/okhttp) - Provides capabilities for implementing interceptors, logging, and mocking web servers, making it easier to work with HTTP requests.
- [Glide](https://github.com/bumptech/glide) - An image loading and caching library for Android, focused on providing smooth scrolling and efficient image loading in apps.


## Images

|   Movies |  Details | Favorites |  Search  |
| -------- | -------- | --------  | -------- |  
| ![Imagen 1](https://github.com/AylinArias/TMDB/blob/master/home.png) | ![Imagen 2](https://github.com/AylinArias/TMDB/blob/master/detailmovie.png) | ![Imagen 3](https://github.com/AylinArias/TMDB/blob/master/favorite%20movies.png) | ![Imagen 4](https://github.com/AylinArias/TMDB/blob/master/search.png) | 
