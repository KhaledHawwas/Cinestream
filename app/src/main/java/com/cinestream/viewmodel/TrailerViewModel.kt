package com.cinestream.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinestream.BuildConfig
import com.cinestream.network.RetrofitInstance
import com.cinestream.data.CastMember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TrailerViewModel : ViewModel() {

    private val api = RetrofitInstance.api
    private val apiKey = BuildConfig.TMDB_API_KEY

    private val _cast = MutableStateFlow<List<CastMember>>(emptyList())
    val cast: StateFlow<List<CastMember>> = _cast

    fun getMovieCast(movieId: Int, language: String) {
        viewModelScope.launch {
            try {
                val langCode = if (language == "ar") "ar-SA" else "en-US"

                val response = api.getMovieCredits(movieId, apiKey, langCode)
                _cast.value = response.cast.take(15)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}




