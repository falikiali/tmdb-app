package com.falikiali.tmdbapp.presentation.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.falikiali.tmdbapp.ui.theme.TMDBAppTheme
import com.google.android.material.search.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    settingsState: SettingsState,
    onEvent: (SettingsUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Default
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "back from settings")
                    }
                }
            )
        },
    ) { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 18.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(imageVector = Icons.Filled.DarkMode, contentDescription = "dark mode")

                Column {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 4.dp),
                        text = "Dark Theme",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )

                    if (settingsState.isDarkTheme) {
                        Text(
                            modifier = Modifier
                                .width(250.dp),
                            text = "Switch off for using Dark Theme. Dark Theme is highly recommended for use at night or in dark places",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.SansSerif,
                            lineHeight = 14.sp
                        )
                    } else {
                        Text(
                            modifier = Modifier
                                .width(250.dp),
                            text = "Switch on for using Dark Theme. Dark Theme is highly recommended for use at night or in dark places",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.SansSerif,
                            lineHeight = 14.sp
                        )
                    }
                }

                Switch(checked = settingsState.isDarkTheme , onCheckedChange = {
                    onEvent(SettingsUiEvent.OnDarkThemeClicked(it))
                })
            }

            Spacer(modifier = Modifier.height(12.dp))

            Divider(thickness = 2.dp)
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingScreenPreview() {
    val navController = rememberNavController()

    TMDBAppTheme {
        SettingsScreen(
            navController = navController,
            settingsState = SettingsState(
                isDarkTheme = true
            ),
            onEvent = {}
        )
    }
}