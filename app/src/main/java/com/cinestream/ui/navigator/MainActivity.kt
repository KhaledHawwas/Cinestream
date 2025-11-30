package com.cinestream.ui.navigator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cinestream.ui.screens.CategoryScreen
import com.cinestream.ui.screens.HomeScreen
import com.cinestream.ui.screens.MovieDetailsScreen
import com.cinestream.ui.screens.Profile
import com.cinestream.ui.screens.SearchScreen
import com.cinestream.ui.screens.SettingsScreen
import com.cinestream.ui.screens.SignInScreen
import com.cinestream.ui.screens.SignUpScreen
import com.cinestream.ui.screens.SplashScreen
import com.cinestream.ui.screens.TrailerScreen
import com.cinestream.viewmodel.ThemeViewModel
import com.cinestream.ui.theme.CineStreamTheme
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen for Android 12+
        val splashScreen = installSplashScreen()
        
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val isThemeLoaded by themeViewModel.isThemeLoaded.collectAsState()
            val isDarkMode by themeViewModel.isDarkMode.collectAsState()
            val systemDark = isSystemInDarkTheme()

            LaunchedEffect(Unit) {
                themeViewModel.loadDarkMode(defaultDarkMode = systemDark)
            }
            if (!isThemeLoaded) {
                SplashScreen(
                    onTimeout = { }
                )
                return@setContent
            }
            CineStreamTheme(darkTheme = isDarkMode) {
                MyApp(themeViewModel)
            }

        }
    }
}
@Composable
fun MyApp(
    themeViewModel: ThemeViewModel
) {
    val navController = rememberNavController()
    val cinematicGlow = Brush.linearGradient(
        listOf(
            Color(0x661A73FF),
            Color(0x6600DBC7),
            Color(0x6609071F)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(cinematicGlow)
    ) {
        NavHost(
            navController = navController,
            startDestination = "splash"
        ) {
            // ---------------------------
            // Splash Screen
            // ---------------------------
            composable("splash") {
                SplashScreen(
                    onTimeout = {
                        val user = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
                        val destination =
                            if (user != null && user.isEmailVerified) "home" else "signIn"
                        navController.navigate(destination) {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                )
            }

            // ---------------------------
            // Sign In Screen
            // ---------------------------
            composable("signIn") {
                SignInScreen(navController = navController)
            }

            // ---------------------------
            // Sign Up Screen
            // ---------------------------
            composable("signup") {
                SignUpScreen(navController = navController)
            }

            // ---------------------------
            // Home Screen
            // ---------------------------
            composable("home") {
                val userId = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
                HomeScreen(
                    navController = navController,
                    userId = userId,
                    themeViewModel = themeViewModel,
                    onMovieClick = { movieId: Int ->
                        navController.navigate("details/$movieId")
                    },
                    onSearchClick = {
                        navController.navigate("search_screen")
                    },
                    onProfileClick = {
                        navController.navigate("profile")
                    },
                    onSettingsClick = {
                        navController.navigate("settings")
                    }
                )
            }
            // ---------------------------
            // Search Screen
            // ---------------------------
            composable("search_screen") {
                SearchScreen(navController = navController)
            }

            // ---------------------------
            // Category Screen
            // ---------------------------
            composable(
                route = "category_screen/{genreId}/{genreName}",
                arguments = listOf(
                    navArgument("genreId") { type = NavType.IntType },
                    navArgument("genreName") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val genreId = backStackEntry.arguments?.getInt("genreId") ?: 0
                val genreNameEncoded = backStackEntry.arguments?.getString("genreName") ?: ""
                val genreName =
                    URLDecoder.decode(genreNameEncoded, StandardCharsets.UTF_8.toString())
                CategoryScreen(
                    genreId = genreId,
                    genreName = genreName,
                    navController = navController
                )
            }

            // ---------------------------
            // Movie Details Screen
            // ---------------------------
            composable(
                route = "details/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId")
                MovieDetailsScreen(movieId = movieId, navController = navController)
            }

            // ---------------------------
            // Profile Screen
            // ---------------------------
            composable("profile") {
                Profile(
                    navController = navController
                )
            }
            // ---------------------------
            // Trailer Screen
            // ---------------------------
            composable(
                route = "trailer/{videoKey}/{movieId}",
                arguments = listOf(
                    navArgument("videoKey") { type = NavType.StringType },
                    navArgument("movieId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val videoKey = backStackEntry.arguments?.getString("videoKey")
                val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0

                TrailerScreen(
                    navController = navController,
                    videoKey = videoKey,
                    movieId = movieId
                )
            }
            // ---------------------------
            // Settings Screen
            // ---------------------------
            composable("settings") {
                SettingsScreen(
                    onBackClick = { navController.popBackStack() },
                    themeViewModel = themeViewModel
                )
            }
        }
    }
}