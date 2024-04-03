package com.falikiali.tmdbapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.falikiali.tmdbapp.helper.Screens
import com.falikiali.tmdbapp.presentation.bookmark.BookmarkScreen
import com.falikiali.tmdbapp.presentation.bookmark.BookmarkViewModel
import com.falikiali.tmdbapp.presentation.home.HomeScreen
import com.falikiali.tmdbapp.presentation.home.HomeViewModel
import com.falikiali.tmdbapp.presentation.search.SearchScreen
import com.falikiali.tmdbapp.presentation.search.SearchViewModel

@Composable
fun BottomNavGraph(bottomNavController: NavHostController, navController: NavHostController, modifier: Modifier) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val homeState by homeViewModel.homeState.collectAsStateWithLifecycle()

    val searchViewModel = hiltViewModel<SearchViewModel>()
    val searchState by searchViewModel.searchMediaState.collectAsStateWithLifecycle()

    val bookmarkViewModel = hiltViewModel<BookmarkViewModel>()
    val bookmarkState by bookmarkViewModel.bookmarkState.collectAsStateWithLifecycle()

    NavHost(navController = bottomNavController, startDestination = Screens.Home.route) {
        composable(route = Screens.Home.route) {
            HomeScreen(
                modifier = modifier,
                homeState = homeState,
                onEvent = homeViewModel::onEvent,
                navController = navController
            )
        }

        composable(route = Screens.Search.route) {
            SearchScreen(
                modifier = modifier,
                searchState = searchState,
                onEvent = searchViewModel::onEvent,
                navController = navController
            )
        }

        composable(route = Screens.Bookmark.route) {
            BookmarkScreen(
                modifier = modifier,
                bookmarkState = bookmarkState,
                onEvent = bookmarkViewModel::onEvent,
                navController = navController
            )
        }
    }
}