# Cinestream

Cinestream is a modern Android movie discovery app built with Kotlin and Jetpack Compose. It features a Material 3 design system, immersive gradients, adaptive typography, and smooth Compose animations. The app integrates with TMDB API for movie data and Firebase for authentication and user management.

---

## ✨ Features

🎨 Advanced Design & Modern Interface
Material Design 3 with custom color palettes (Aurora/CoralPulse)
Glassmorphism Design transparent panels with stunning visual effects
Hero Carousels showcasing latest and most popular movies
Interactive cinematic statistics and immersive details

🔗 Integrated Profiles & Cloud Storage
Firebase Integration complete authentication and Firestore support
User profiles linked with cloud synchronization
Secure data storage for preferences and watch history

🎬 Comprehensive Movie Experience
TMDB Integration complete integration with The Movie Database API
Movies available with detailed descriptions
Trailer viewing capability directly within the app
Advanced search with responsive results

🌍 Multilingual & Accessibility
Dual Language Support Arabic and English interfaces
Customization Freedom choice of font type and size
Theme Options night mode or day mode selection
Favorites System add movies to personal favorites list
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
