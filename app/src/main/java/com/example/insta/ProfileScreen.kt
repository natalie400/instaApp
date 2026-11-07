
package com.example.insta

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {

    var displayName by rememberSaveable {
        mutableStateOf("Natalie")
    }

    var bio by rememberSaveable {
        mutableStateOf(
            "Digital creator ✨\n" +
                    "Tech | Lifestyle | Adventures 🌍\n" +
                    "Creating memories one day at a time"
        )
    }

    var showEditDialog by remember {
        mutableStateOf(false)
    }

    val profilePosts = listOf(
        R.drawable.post_natalie,
        R.drawable.post_alex,
        R.drawable.post_zara,
        R.drawable.story_natalie,
        R.drawable.story_alex,
        R.drawable.story_zara,
        R.drawable.story_max,
        R.drawable.post_natalie,
        R.drawable.post_zara
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // PROFILE TOP BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "natalie",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "☰",
                fontSize = 28.sp,
                color = Color.Black
            )
        }

        HorizontalDivider(
            color = Color.LightGray,
            thickness = 0.5.dp
        )

        // PROFILE CONTENT
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            // PROFILE DETAILS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Image(
                    painter = painterResource(
                        R.drawable.story_natalie
                    ),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .border(
                            2.dp,
                            Color.LightGray,
                            CircleShape
                        ),
                    contentScale = ContentScale.Crop
                )

                ProfileStat("9", "Posts")
                ProfileStat("1.2K", "Followers")
                ProfileStat("850", "Following")
            }

            // DISPLAY NAME AND BIO
            Column(
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            ) {

                Text(
                    text = displayName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = bio,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = Color.Black
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // EDIT PROFILE BUTTON
            Button(
                onClick = {
                    showEditDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(9.dp)
            ) {
                Text(
                    text = "Edit Profile",
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // STORY HIGHLIGHTS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                ProfileHighlight(
                    "Travel",
                    R.drawable.story_alex
                )

                ProfileHighlight(
                    "Friends",
                    R.drawable.story_zara
                )

                ProfileHighlight(
                    "Lifestyle",
                    R.drawable.story_max
                )

                ProfileHighlight(
                    "Moments",
                    R.drawable.story_natalie
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            HorizontalDivider(
                color = Color.LightGray,
                thickness = 0.5.dp
            )

            // POSTS GRID HEADER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "▦  POSTS",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }

            // PHOTO GRID
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                items(profilePosts) { image ->

                    Image(
                        painter = painterResource(image),
                        contentDescription = "Profile post",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }

    // EDIT PROFILE DIALOG
    if (showEditDialog) {

        var editedName by remember {
            mutableStateOf(displayName)
        }

        var editedBio by remember {
            mutableStateOf(bio)
        }

        AlertDialog(
            onDismissRequest = {
                showEditDialog = false
            },
            title = {
                Text("Edit Profile")
            },
            text = {

                Column {

                    OutlinedTextField(
                        value = editedName,
                        onValueChange = {
                            editedName = it
                        },
                        label = {
                            Text("Display Name")
                        },
                        singleLine = true
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    OutlinedTextField(
                        value = editedBio,
                        onValueChange = {
                            editedBio = it
                        },
                        label = {
                            Text("Bio")
                        },
                        minLines = 3
                    )
                }
            },
            confirmButton = {

                TextButton(
                    onClick = {
                        displayName = editedName
                        bio = editedBio
                        showEditDialog = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {

                TextButton(
                    onClick = {
                        showEditDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

// PROFILE STATISTICS
@Composable
fun ProfileStat(
    count: String,
    label: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = count,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}

// STORY HIGHLIGHT
@Composable
fun ProfileHighlight(
    title: String,
    imageRes: Int
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(imageRes),
            contentDescription = title,
            modifier = Modifier
                .size(65.dp)
                .clip(CircleShape)
                .border(
                    2.dp,
                    Color.LightGray,
                    CircleShape
                ),
            contentScale = ContentScale.Crop
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = title,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}
