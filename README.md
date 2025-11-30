# Cinestream

Cinestream is a modern Android movie discovery app built with Kotlin and Jetpack Compose. It features a Material 3 design system, immersive gradients, adaptive typography, and smooth Compose animations. The app integrates with TMDB API for movie data and Firebase for authentication and user management.

---

## ✨ Features

- **Material 3 Design**: Custom color palettes (Aurora/CoralPulse), typography, and glassmorphism panels
- **Modern UI**: Hero carousels, cinematic statistics, immersive details, and responsive search
- **TMDB Integration**: Complete integration with The Movie Database API
- **Firebase Ready**: Authentication and Firestore support for user profiles and settings
- **Clean Architecture**: Well-organized package structure following Android best practices

---

## 🛠️ Tech Stack

- Kotlin + Jetpack Compose + Material 3
- Navigation Component + ViewModel + StateFlow
- Retrofit + Gson + Coroutines
- Coil for image loading
- Firebase Auth + Firestore

---

## 🚀 Firebase Integration

1. **Create a Firebase project** in the Firebase console
2. **Add an Android app** with package id `com.cinestream`
3. **Download** `google-services.json` and place it in `app/google-services.json`
4. **Enable products**:
   - Authentication → Email/Password provider
   - Firestore Database → in production or test mode
5. **Firestore Rules** (tighten for production):
   ```firebase
   rules_version = '2';
   service cloud.firestore {
     match /databases/{database}/documents {
       match /users/{userId} {
         allow read, update, delete: if request.auth != null && request.auth.uid == userId;
         allow create: if request.auth != null;
       }
     }
   }
   ```

---

## 🧭 Project Structure

```
app/
 └─ src/main/java/com/cinestream
    ├─ data/              ← Data models & DTOs
    ├─ network/           ← Retrofit API service
    ├─ ui/
    │   ├─ navigator/     ← MainActivity & Navigation
    │   ├─ screens/       ← Compose screens
    │   └─ theme/         ← Colors, typography, Material 3 theme
    └─ viewmodel/         ← ViewModels for state management
```

---

## ▶️ Building & Testing

```bash
./gradlew clean assembleDebug        # Build APK
./gradlew connectedAndroidTest      # Run instrumentation tests
./gradlew test                       # Run unit tests
```

Open the project in Android Studio, sync Gradle, and run as usual.

---

## 📄 License

MIT License
