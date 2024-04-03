package com.falikiali.tmdbapp.helper

sealed class Screens(val route: String) {
    object Home: Screens("home")
    object Search: Screens("search")
    object Bookmark: Screens("bookmarks")

    object Main: Screens("main")
    object Setting: Screens("setting")
    object Detail: Screens("detail")
    object Similar: Screens("similar")
    object List: Screens("list")
}
