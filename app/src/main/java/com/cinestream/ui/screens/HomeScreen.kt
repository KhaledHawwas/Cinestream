package com.cinestream.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.cinestream.R
import com.cinestream.viewmodel.HomeViewModel
import com.cinestream.viewmodel.SettingsViewModel
import com.cinestream.viewmodel.ThemeViewModel
import com.cinestream.viewmodel.UserViewModel
import com.cinestream.data.MovieDetailsResponse
import com.cinestream.ui.theme.Aurora
import com.cinestream.ui.theme.CoralPulse
import com.cinestream.ui.theme.NeonMint
import com.cinestream.ui.theme.SurfaceBrushed
import com.cinestream.ui.theme.SurfaceElevated

@Composable
fun HomeScreen(
    navController: NavController,
    userId: String?,
    themeViewModel: ThemeViewModel,
    onMovieClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val settingsViewModel: SettingsViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()

    val popularMovies by homeViewModel.popularMovies.collectAsState()
    val topRatedMovies by homeViewModel.topRatedMovies.collectAsState()
    val upcomingMovies by homeViewModel.upcomingMovies.collectAsState()
    val trendingMovies by homeViewModel.trendingMovies.collectAsState()
    val userData by userViewModel.userData.collectAsState()
    val settings by settingsViewModel.userSettings.collectAsState()

    val customFont = remember(settings.fontType) {
        when (settings.fontType) {
            "Roboto" -> FontFamily(Font(R.font.roboto_regular))
            "Cairo" -> FontFamily(Font(R.font.cairo_regular))
            else -> FontFamily(Font(R.font.momo_regular))
        }
    }

    LaunchedEffect(userId) {
        homeViewModel.loadMovies()
        userViewModel.loadUserData()
        themeViewModel.loadDarkMode(defaultDarkMode = false)
    }

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF040B17), Color(0xFF0E1426), Color(0xFF0B0718))
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .background(backgroundBrush)
                .padding(horizontal = 20.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            item {
                HomeHeader(
                    userName = userData?.firstName ?: "User",
                    settings = settings,
                    customFont = customFont,
                    onSettingsClick = onSettingsClick,
                    onProfileClick = onProfileClick
                )
            }

            item {
                SearchPill(onSearchClick = onSearchClick, settings = settings, customFont = customFont)
            }

            item {
                TrendingCarousel(
                    movies = trendingMovies,
                    onMovieClick = onMovieClick,
                    userViewModel = userViewModel
                )
            }

            item {
                QuickStatsRow(settings = settings, userData?.favourites?.size ?: 0, userData?.watchlist?.size ?: 0)
            }

            item {
                HomeSection(
                    title = if (settings.language == "ar") "الأكثر شعبية" else "Popular right now",
                    movies = popularMovies,
                    onMovieClick = onMovieClick,
                    userViewModel = userViewModel
                )
            }

            item {
                HomeSection(
                    title = if (settings.language == "ar") "أعلى التقييمات" else "Critics choice",
                    movies = topRatedMovies,
                    onMovieClick = onMovieClick,
                    userViewModel = userViewModel
                )
            }

            item {
                HomeSection(
                    title = if (settings.language == "ar") "قادمة قريباً" else "Coming soon",
                    movies = upcomingMovies,
                    onMovieClick = onMovieClick,
                    userViewModel = userViewModel
                )
            }
        }
    }
}

@Composable
private fun HomeHeader(
    userName: String,
    settings: com.cinestream.viewmodel.UserSettings,
    customFont: FontFamily,
    onSettingsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = if (settings.language == "ar") "مرحبا" else "Welcome back",
                style = MaterialTheme.typography.labelLarge.copy(color = Color.White.copy(alpha = 0.7f))
            )
            Text(
                text = userName.ifBlank { "User" },
                style = MaterialTheme.typography.displaySmall.copy(
                    fontFamily = customFont,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontSize = settings.fontSize.sp
                )
            )
        }
        Row {
            IconButton(
                onClick = onSettingsClick,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.1f))
            ) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.1f))
            ) {
                Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
            }
        }
    }
}

@Composable
private fun SearchPill(
    onSearchClick: () -> Unit,
    settings: com.cinestream.viewmodel.UserSettings,
    customFont: FontFamily
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(SurfaceBrushed)
            .clickable { onSearchClick() }
            .padding(horizontal = 20.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = if (settings.language == "ar") "بحث سينمائي" else "Cinematic search",
                style = MaterialTheme.typography.titleMedium.copy(fontFamily = customFont, color = Color.White)
            )
            Text(
                text = if (settings.language == "ar") "اعثر على فيلم أحلامك" else "Find tonight's perfect film",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.7f))
            )
        }
        Icon(
            Icons.Default.Search,
            contentDescription = null,
            tint = NeonMint,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
private fun QuickStatsRow(
    settings: com.cinestream.viewmodel.UserSettings,
    favourites: Int,
    watchlist: Int
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = remember(screenWidth) { (screenWidth - 16.dp) / 2 }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatCard(
            label = if (settings.language == "ar") "مفضل" else "Favourites",
            value = favourites,
            accent = Aurora,
            modifier = Modifier.width(cardWidth)
        )
        StatCard(
            label = if (settings.language == "ar") "المشاهدة لاحقاً" else "Watchlist",
            value = watchlist,
            accent = CoralPulse,
            modifier = Modifier.width(cardWidth)
        )
    }
}

@Composable
private fun StatCard(
    label: String,
    value: Int,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(90.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceElevated),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.7f))
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.headlineMedium.copy(color = accent, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
private fun HomeSection(
    title: String,
    movies: List<MovieDetailsResponse>,
    onMovieClick: (Int) -> Unit,
    userViewModel: UserViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(color = Color.White, fontWeight = FontWeight.SemiBold)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movies) { movie ->
                PosterCard(movie = movie, userViewModel = userViewModel, onMovieClick = onMovieClick)
            }
        }
    }
}

@Composable
private fun TrendingCarousel(
    movies: List<MovieDetailsResponse>,
    onMovieClick: (Int) -> Unit,
    userViewModel: UserViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Spotlight Premieres",
            style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            items(movies) { movie ->
                HeroCard(movie = movie, userViewModel = userViewModel, onMovieClick = onMovieClick)
            }
        }
    }
}

@Composable
private fun HeroCard(
    movie: MovieDetailsResponse,
    userViewModel: UserViewModel,
    onMovieClick: (Int) -> Unit
) {
    val userData by userViewModel.userData.collectAsState()
    val isFavorite = userData?.favourites?.contains(movie.id.toString()) == true

    Card(
        modifier = Modifier
            .width(280.dp)
            .height(360.dp)
            .clip(RoundedCornerShape(32.dp))
            .clickable { onMovieClick(movie.id) },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath ?: movie.backdropPath}",
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xCC050912))))
                    .padding(18.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = movie.title ?: "Unknown title",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White, fontWeight = FontWeight.Bold),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${movie.voteAverage ?: 0.0} • ${movie.releaseDate ?: ""}",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.8f))
                    )
                }
            }
            IconButton(
                onClick = {
                    if (isFavorite) userViewModel.removeFromFavourites(movie.id.toString())
                    else userViewModel.addToFavourites(movie.id.toString())
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.35f))
            ) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = null,
                    tint = if (isFavorite) CoralPulse else Color.White
                )
            }
        }
    }
}

@Composable
private fun PosterCard(
    movie: MovieDetailsResponse,
    userViewModel: UserViewModel,
    onMovieClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .height(280.dp)
            .animateContentSize()
            .shadow(8.dp, RoundedCornerShape(24.dp))
            .clickable { onMovieClick(movie.id) },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceElevated)
    ) {
        Column {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(210.dp)
                    .fillMaxWidth()
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = movie.title ?: "",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = movie.overview ?: "",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.6f)),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}





