package com.falikiali.tmdbapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.falikiali.tmdbapp.helper.Screens
import com.falikiali.tmdbapp.presentation.detail.DetailScreen
import com.falikiali.tmdbapp.presentation.detail.DetailViewModel
import com.falikiali.tmdbapp.presentation.list_media.ListMediaScreen
import com.falikiali.tmdbapp.presentation.list_media.ListMediaViewModel
import com.falikiali.tmdbapp.presentation.settings.SettingsScreen
import com.falikiali.tmdbapp.presentation.settings.SettingsViewModel
import com.falikiali.tmdbapp.presentation.similar_media.SimilarMediaScreen
import com.falikiali.tmdbapp.presentation.similar_media.SimilarMediaViewModel
import com.falikiali.tmdbapp.screens.MainScreen
import com.falikiali.tmdbapp.ui.theme.TMDBAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            val settingsState by settingsViewModel.settingsState.collectAsStateWithLifecycle()

            val listMediaViewModel = hiltViewModel<ListMediaViewModel>()
            val listMediaState by listMediaViewModel.listMediaState.collectAsStateWithLifecycle()

            val detailViewModel = hiltViewModel<DetailViewModel>()
            val detailState by detailViewModel.detailState.collectAsStateWithLifecycle()

            val similarMediaViewModel = hiltViewModel<SimilarMediaViewModel>()
            val similarMediaState by similarMediaViewModel.similarMedia.collectAsStateWithLifecycle()

            TMDBAppTheme(
                darkTheme = settingsState.isDarkTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = Screens.Main.route) {
                        composable(route = Screens.Main.route) {
                            MainScreen(navController = navController)
                        }

                        composable(
                            route = Screens.Setting.route,
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(750)) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, animationSpec = tween(750)) }
                        ) {
                            SettingsScreen(navController = navController, settingsState, settingsViewModel::onEvent)
                        }

                        composable(
                            route = Screens.List.route + "/{mediaType}/{mediaCategory}",
                            arguments = listOf(
                                navArgument("mediaType") { type = NavType.StringType },
                                navArgument("mediaCategory") { type = NavType.StringType },
                            ),
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(750)) },
                            popEnterTransition = { fadeIn(animationSpec = tween(750)) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(750)) }
                        ) {
                            val mediaType = it.arguments?.getString("mediaType")
                            val mediaCategory = it.arguments?.getString("mediaCategory")

                            ListMediaScreen(
                                mediaType = mediaType ?: "",
                                mediaCategory = mediaCategory ?: "",
                                navController = navController,
                                listMediaState = listMediaState,
                                onEvent = listMediaViewModel::onEvent
                            )
                        }

                        composable(
                            route = Screens.Detail.route + "/{mediaType}/{mediaId}",
                            arguments = listOf(
                                navArgument("mediaType") { type = NavType.StringType },
                                navArgument("mediaId") { type = NavType.IntType },
                            ),
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(750)) },
                            popEnterTransition = { fadeIn(animationSpec = tween(750)) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(750)) }
                        ) {
                            val mediaType = it.arguments?.getString("mediaType")
                            val mediaId = it.arguments?.getInt("mediaId", 0)

                            DetailScreen(
                                mediaType = mediaType ?: "",
                                mediaId = mediaId ?: 0,
                                navController = navController,
                                detailState = detailState,
                                onEvent = detailViewModel::onEvent
                            )
                        }

                        composable(
                            route = Screens.Similar.route + "/{mediaType}/{mediaId}",
                            arguments = listOf(
                                navArgument("mediaType") { type = NavType.StringType },
                                navArgument("mediaId") { type = NavType.IntType },
                            ),
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(750)) },
                            popEnterTransition = { fadeIn(animationSpec = tween(750)) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(750)) }
                        ) {
                            val mediaType = it.arguments?.getString("mediaType")
                            val mediaId = it.arguments?.getInt("mediaId", 0)

                            SimilarMediaScreen(
                                mediaType = mediaType ?: "",
                                mediaId = mediaId ?: 0,
                                navController = navController,
                                similarMediaState = similarMediaState,
                                onEvent = similarMediaViewModel::onEvent
                            )
                        }
                    }
                }
            }
        }
    }
}