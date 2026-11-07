
package com.example.insta

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ExplorePost(
    val username: String,
    val imageRes: Int
)

@Composable
fun SearchScreen() {

    var searchText by remember {
        mutableStateOf("")
    }

    val explorePosts = listOf(
        ExplorePost("natalie", R.drawable.post_natalie),
        ExplorePost("alex", R.drawable.post_alex),
        ExplorePost("zara", R.drawable.post_zara),
        ExplorePost("max", R.drawable.story_max),
        ExplorePost("natalie", R.drawable.story_natalie),
        ExplorePost("alex", R.drawable.story_alex),
        ExplorePost("zara", R.drawable.story_zara),
        ExplorePost("max", R.drawable.story_max),
        ExplorePost("natalie", R.drawable.post_natalie)
    )

    val filteredPosts = explorePosts.filter {
        it.username.contains(
            searchText,
            ignoreCase = true
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Text(
            text = "Explore",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = {
                Text("Search users...")
            },
            leadingIcon = {
                Text("⌕", fontSize = 28.sp)
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
            shape = androidx.compose.foundation.shape
                .RoundedCornerShape(14.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (filteredPosts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No results found",
                    color = Color.Gray
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(filteredPosts) { post ->

                    Image(
                        painter = painterResource(post.imageRes),
                        contentDescription = "${post.username} photo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
