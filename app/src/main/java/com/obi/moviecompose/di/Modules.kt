package com.obi.moviecompose.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.obi.moviecompose.BuildConfig
import com.obi.moviecompose.data.Consts.BASE_URL
import com.obi.moviecompose.data.MovieApi
import com.obi.moviecompose.data.MoviesRepository
import com.obi.moviecompose.domain.GetAiringTodayTvShowsUseCase
import com.obi.moviecompose.domain.GetMovieCreditsUseCase
import com.obi.moviecompose.domain.GetMovieDetailsUseCase
import com.obi.moviecompose.domain.GetTopRatedMoviesUseCase
import com.obi.moviecompose.domain.GetTrendingMoviesUseCase
import com.obi.moviecompose.presentation.home.HomeViewModel
import com.obi.moviecompose.presentation.moviedetails.MovieDetailsViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(Interceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("accept", "application/json")
                .addHeader(
                    "Authorization",
                    "Bearer ${BuildConfig.authorizationToken}"
                )
                .build()
            chain.proceed(request)
        })

        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient.build())
            .build()
            .create(MovieApi::class.java)
    }
    single { Dispatchers.IO }
}
val dataModule = module {
    factory { MoviesRepository(get(), get()) }
}
val domainModule = module {
    factory { GetTopRatedMoviesUseCase(get()) }
    factory { GetTrendingMoviesUseCase(get()) }
    factory { GetAiringTodayTvShowsUseCase(get()) }
    factory { GetMovieDetailsUseCase(get()) }
    factory { GetMovieCreditsUseCase(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { MovieDetailsViewModel(get(), get()) }
}

