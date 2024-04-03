package com.falikiali.tmdbapp.presentation.search

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.falikiali.tmdbapp.component.MediaCard
import com.falikiali.tmdbapp.component.PagingMediaCard
import com.falikiali.tmdbapp.component.SearchBar
import com.falikiali.tmdbapp.component.TopBar
import com.falikiali.tmdbapp.helper.Screens
import com.falikiali.tmdbapp.ui.theme.TMDBAppTheme

@Composable
fun SearchScreen(
    modifier: Modifier,
    searchState: SearchState,
    onEvent: (SearchUiEvent) -> Unit,
    navController: NavHostController
) {
    val mediaSearch = searchState.searchList?.collectAsLazyPagingItems()

    LaunchedEffect(key1 = searchState.searchQuery) {
        onEvent(SearchUiEvent.OnSearchMedia)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = searchState.searchQuery,
            onValueChange = { onEvent(SearchUiEvent.OnSearchQueryChange(it)) }
        ) {
            onEvent(SearchUiEvent.OnSearchMedia)
        }
        
        Spacer(modifier = Modifier.height(8.dp))

        if (mediaSearch != null) {
            PagingMediaCard(mediaSearch = mediaSearch, onClick = { media ->
                navController.navigate(Screens.Detail.route + "/${media.mediaType}/${media.id}")
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview() {
    TMDBAppTheme {
        val navController = rememberNavController()

        Scaffold(
            topBar = {
                TopBar(navController = navController)
            }
        ) {
            SearchScreen(
                modifier = Modifier.padding(it),
                searchState = SearchState(searchQuery = "testing"),
                onEvent = {},
                navController = navController
            )
        }
    }
}