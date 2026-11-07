
package com.example.insta

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Reel(
    val id: Int,
    val username: String,
    val imageRes: Int,
    val profileImage: Int,
    val caption: String,
    val likes: Int
)

@Composable
fun ReelsScreen() {

    val reels = remember {
        listOf(
            Reel(
                1,
                "natalie",
                R.drawable.post_natalie,
                R.drawable.story_natalie,
                "Living my best life ✨",
                1240
            ),
            Reel(
                2,
                "alex",
                R.drawable.post_alex,
                R.drawable.story_alex,
                "Adventure awaits 🌍",
                2850
            ),
            Reel(
                3,
                "zara",
                R.drawable.post_zara,
                R.drawable.story_zara,
                "Making memories ❤️",
                3560
            )
        )
    }

    val pagerState = rememberPagerState(
        pageCount = { reels.size }
    )

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->

        ReelItem(reel = reels[page])
    }
}

@Composable
fun ReelItem(reel: Reel) {

    var isLiked by rememberSaveable(reel.id) {
        mutableStateOf(false)
    }

    var isSaved by rememberSaveable(reel.id) {
        mutableStateOf(false)
    }

    var isFollowing by rememberSaveable(reel.id) {
        mutableStateOf(false)
    }

    var showComments by remember {
        mutableStateOf(false)
    }

    val likesCount = reel.likes + if (isLiked) 1 else 0

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        // Full-screen reel image
        Image(
            painter = painterResource(reel.imageRes),
            contentDescription = "Reel by ${reel.username}",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient overlay for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.25f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.75f)
                        )
                    )
                )
        )

        // Reels title
        Text(
            text = "Reels",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(18.dp)
        )

        // Right-side interaction buttons
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 105.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isLiked) "♥" else "♡",
                    fontSize = 38.sp,
                    color = if (isLiked) {
                        Color.Red
                    } else {
                        Color.White
                    },
                    modifier = Modifier.clickable {
                        isLiked = !isLiked
                    }
                )

                Text(
                    text = "$likesCount",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }

            Text(
                text = "▤",
                fontSize = 32.sp,
                color = Color.White,
                modifier = Modifier.clickable {
                    showComments = true
                }
            )

            Text(
                text = "➤",
                fontSize = 32.sp,
                color = Color.White
            )

            Text(
                text = if (isSaved) "▣" else "▯",
                fontSize = 32.sp,
                color = Color.White,
                modifier = Modifier.clickable {
                    isSaved = !isSaved
                }
            )
        }

        // Creator details
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.78f)
                .padding(start = 16.dp, bottom = 25.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(
                        reel.profileImage
                    ),
                    contentDescription = "Creator profile",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(
                    modifier = Modifier.width(10.dp)
                )

                Text(
                    text = reel.username,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

                Spacer(
                    modifier = Modifier.width(10.dp)
                )

                OutlinedButton(
                    onClick = {
                        isFollowing = !isFollowing
                    },
                    contentPadding = PaddingValues(
                        horizontal = 12.dp,
                        vertical = 0.dp
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (isFollowing) {
                            "Following"
                        } else {
                            "Follow"
                        },
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = reel.caption,
                color = Color.White,
                fontSize = 14.sp
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "♫ Original audio",
                color = Color.White,
                fontSize = 12.sp
            )
        }

        // Comments dialog
        if (showComments) {
            AlertDialog(
                onDismissRequest = {
                    showComments = false
                },
                title = {
                    Text("Comments")
                },
                text = {
                    Text("No comments yet. Be the first!")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showComments = false
                        }
                    ) {
                        Text("Close")
                    }
                }
            )
        }
    }
}
