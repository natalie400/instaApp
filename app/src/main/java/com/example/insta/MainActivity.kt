
package com.example.insta


import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// MAIN ACTIVITY

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                InstaApp()
            }
        }
    }
}

// DATA MODELS

data class Story(
    val username: String,
    val imageRes: Int
)

data class Post(
    val id: Int,
    val username: String,
    val profileImage: Int,
    val postImage: Int,
    val caption: String,
    val likes: Int
)

// MAIN HOME SCREEN

@Composable
fun InstaHomeScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        InstaHeader()

        HorizontalDivider(
            color = Color.LightGray,
            thickness = 0.5.dp
        )

        StoriesSection()

        HorizontalDivider(
            color = Color.LightGray,
            thickness = 0.5.dp
        )

        PostsFeed()
    }
}

// TOP HEADER

@Composable
fun InstaHeader() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 14.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Insta",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "♡",
                fontSize = 30.sp,
                color = Color.Black
            )

            Text(
                text = "✉",
                fontSize = 27.sp,
                color = Color.Black
            )
        }
    }
}

// STORIES SECTION

@Composable
fun StoriesSection() {

    val stories = listOf(
        Story(
            "Your story",
            R.drawable.story_natalie
        ),
        Story(
            "alex",
            R.drawable.story_alex
        ),
        Story(
            "max",
            R.drawable.story_max
        ),
        Story(
            "zara",
            R.drawable.story_zara
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(
                vertical = 14.dp,
                horizontal = 12.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        stories.forEachIndexed { index, story ->

            StoryItem(
                story = story,
                isOwnStory = index == 0
            )
        }
    }
}

// INDIVIDUAL STORY

@Composable
fun StoryItem(
    story: Story,
    isOwnStory: Boolean
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(76.dp)
    ) {

        Box(
            modifier = Modifier.size(76.dp),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .size(76.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = if (isOwnStory) {
                                listOf(
                                    Color.LightGray,
                                    Color.LightGray
                                )
                            } else {
                                listOf(
                                    Color(0xFFFEDA75),
                                    Color(0xFFFA7E1E),
                                    Color(0xFFD62976),
                                    Color(0xFF962FBF)
                                )
                            }
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(
                        id = story.imageRes
                    ),
                    contentDescription = "${story.username} story",
                    modifier = Modifier
                        .size(68.dp)
                        .border(
                            width = 3.dp,
                            color = Color.White,
                            shape = CircleShape
                        )
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = story.username,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black
        )
    }
}

// POSTS FEED

@Composable
fun PostsFeed() {

    val posts = remember {
        listOf(
            Post(
                id = 1,
                username = "natalie",
                profileImage = R.drawable.story_natalie,
                postImage = R.drawable.post_natalie,
                caption = "Enjoying the little moments ✨",
                likes = 245
            ),
            Post(
                id = 2,
                username = "alex",
                profileImage = R.drawable.story_alex,
                postImage = R.drawable.post_alex,
                caption = "Another beautiful day 🌍",
                likes = 389
            ),
            Post(
                id = 3,
                username = "zara",
                profileImage = R.drawable.story_zara,
                postImage = R.drawable.post_zara,
                caption = "Good vibes only ❤️",
                likes = 512
            )
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        items(
            items = posts,
            key = { post -> post.id }
        ) { post ->

            PostItem(post = post)
        }
    }
}

// INDIVIDUAL POST

@Composable
fun PostItem(post: Post) {

    val context = LocalContext.current


    // LIKE STATE
    var isLiked by rememberSaveable(post.id) {
        mutableStateOf(false)
    }

    // SAVE STATE
    var isSaved by rememberSaveable(post.id) {
        mutableStateOf(false)
    }

    // COMMENTS DIALOG STATE
    var showComments by remember {
        mutableStateOf(false)
    }

    // COMMENTS LIST
    val comments = remember(post.id) {
        mutableStateListOf<String>()
    }

    // TOTAL LIKES
    val totalLikes = post.likes + if (isLiked) 1 else 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {

        // POST HEADER

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(
                    id = post.profileImage
                ),
                contentDescription = "${post.username} profile",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Text(
                text = post.username,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "⋮",
                fontSize = 24.sp,
                color = Color.Black
            )
        }

        // POST IMAGE

        Image(
            painter = painterResource(
                id = post.postImage
            ),
            contentDescription = "${post.username} post",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )

        // INTERACTION BUTTONS

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 14.dp,
                    vertical = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // LIKE BUTTON

            Text(
                text = if (isLiked) "♥" else "♡",
                fontSize = 30.sp,
                color = if (isLiked) {
                    Color.Red
                } else {
                    Color.Black
                },
                modifier = Modifier.clickable {
                    isLiked = !isLiked
                }
            )

            Spacer(
                modifier = Modifier.width(18.dp)
            )

            // COMMENT BUTTON

            Text(
                text = "▤",
                fontSize = 28.sp,
                color = Color.Black,
                modifier = Modifier.clickable {
                    showComments = true
                }
            )

            Spacer(
                modifier = Modifier.width(18.dp)
            )

            // SHARE BUTTON PLACEHOLDER


// FUNCTIONAL SHARE BUTTON

            Text(
                text = "➤",
                fontSize = 25.sp,
                color = Color.Black,
                modifier = Modifier.clickable {

                    // Content to share
                    val shareText = """
            Check out this post on Insta!

            @${post.username}
            ${post.caption}
        """.trimIndent()

                    // Create Android sharing intent
                    val shareIntent = Intent(
                        Intent.ACTION_SEND
                    ).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            shareText
                        )
                    }

                    // Open Android sharing menu
                    context.startActivity(
                        Intent.createChooser(
                            shareIntent,
                            "Share Insta Post"
                        )
                    )
                }
            )


            Spacer(
                modifier = Modifier.weight(1f)
            )

            // SAVE BUTTON

            Text(
                text = if (isSaved) "▣" else "▯",
                fontSize = 28.sp,
                color = Color.Black,
                modifier = Modifier.clickable {
                    isSaved = !isSaved
                }
            )
        }

        // NUMBER OF LIKES

        Text(
            text = "$totalLikes likes",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(
                horizontal = 14.dp
            )
        )

        // COMMENTS COUNT

        if (comments.isNotEmpty()) {

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "View all ${comments.size} comments",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier
                    .clickable {
                        showComments = true
                    }
                    .padding(
                        horizontal = 14.dp,
                        vertical = 6.dp
                    )
            )
        }

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        // POST CAPTION

        Row(
            modifier = Modifier.padding(
                horizontal = 14.dp
            )
        ) {

            Text(
                text = "${post.username} ",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )

            Text(
                text = post.caption,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        HorizontalDivider(
            color = Color(0xFFEEEEEE),
            thickness = 0.5.dp
        )
    }

    // COMMENTS DIALOG
    // Must remain outside the Column,
    // but inside the PostItem function.

    if (showComments) {

        CommentsDialog(
            comments = comments,
            onAddComment = { comment ->
                comments.add(comment)
            },
            onDismiss = {
                showComments = false
            }
        )
    }
}
