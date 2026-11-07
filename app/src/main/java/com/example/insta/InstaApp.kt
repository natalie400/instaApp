
package com.example.insta

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun InstaApp() {

    var selectedScreen by rememberSaveable {
        mutableIntStateOf(0)
    }

    val screens = listOf(
        "Home",
        "Search",
        "Create",
        "Reels",
        "Profile"
    )

    val symbols = listOf(
        "⌂",
        "⌕",
        "⊞",
        "▷",
        "◉"
    )

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                screens.forEachIndexed { index, title ->

                    NavigationBarItem(
                        selected = selectedScreen == index,
                        onClick = {
                            selectedScreen = index
                        },
                        icon = {
                            Text(
                                text = symbols[index],
                                fontSize = 27.sp,
                                fontWeight =
                                    if (selectedScreen == index)
                                        FontWeight.Bold
                                    else
                                        FontWeight.Normal
                            )
                        },
                        label = {
                            Text(
                                text = title,
                                fontSize = 10.sp
                            )
                        },
                        alwaysShowLabel = true,
                        colors =
                            NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.Black,
                                selectedTextColor = Color.Black,
                                indicatorColor =
                                    Color(0xFFF1F1F1),
                                unselectedIconColor =
                                    Color.Gray,
                                unselectedTextColor =
                                    Color.Gray
                            )
                    )
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedScreen) {
                0 -> InstaHomeScreen()
                1 -> SearchScreen()
                2 -> CreatePostScreen()
                3 -> ReelsScreen()
                4 -> ProfileScreen()
            }
        }
    }
}
