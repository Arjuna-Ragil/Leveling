package com.example.leveling.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leveling.content.Home
import com.example.leveling.content.Profile
import com.example.leveling.content.quest.Quest
import com.example.leveling.content.Setting
import com.example.leveling.content.Shop
import com.example.leveling.content.quest.Daily
import com.example.leveling.content.quest.Monthlyearly
import com.example.leveling.content.quest.Weekly
import com.example.leveling.login.LoginViewModel
import com.example.leveling.ui.theme.background

@Composable
fun NavBar(navControllerSecondary: NavController) {
    val navControllerMain = rememberNavController()

    val navBarItems = listOf(
        NavbarItem(
            title = "Home",
            route = "home",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            hasNews = false,
        ),
        NavbarItem(
            title = "Profile",
            route = "profile",
            selectedIcon = Icons.Filled.AccountCircle,
            unSelectedIcon = Icons.Outlined.AccountCircle,
            hasNews = false,
        ),
        NavbarItem(
            title = "Quest",
            route = "quest",
            selectedIcon = Icons.Filled.Search,
            unSelectedIcon = Icons.Outlined.Search,
            hasNews = false,
        ),
        NavbarItem(
            title = "Shop",
            route = "shop",
            selectedIcon = Icons.Filled.ShoppingCart,
            unSelectedIcon = Icons.Outlined.ShoppingCart,
            hasNews = false,
        ),
        NavbarItem(
            title = "Setting",
            route = "setting",
            selectedIcon = Icons.Filled.Settings,
            unSelectedIcon = Icons.Outlined.Settings,
            hasNews = false,
        ),
    )

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar{
                navBarItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            navControllerMain.navigate(item.route)
                        },
                        label = { Text(text = item.title) },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (item.badgeCount != null) {
                                        Text(text = item.badgeCount.toString())
                                    } else if (item.hasNews) {
                                        Badge()
                                    }

                                }
                            ) {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else {
                                        item.unSelectedIcon

                                    },
                                    contentDescription = item.title
                                )

                            }
                        }
                    )
                }
            }
        }
    ){
        Surface(
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxSize()
                .then(background())
                .padding(it)
        ) {
            NavHost(navController = navControllerMain, startDestination = "Home") {
                composable("home") { Home(navControllerSecondary, LoginViewModel()) }
                composable("profile") { Profile() }
                composable("quest") { Quest(navControllerMain) }
                composable("daily") { Daily(navControllerMain) }
                composable("weekly") { Weekly() }
                composable("monthlyearly") { Monthlyearly() }
                composable("shop") { Shop() }
                composable("setting") { Setting() }
            }
        }
    }
}