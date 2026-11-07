
package com.example.insta

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.graphics.BitmapFactory

@Composable
fun CreatePostScreen() {

    val context = LocalContext.current

    var selectedImageUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    var caption by rememberSaveable {
        mutableStateOf("")
    }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            selectedImageUri = uri
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // TOP BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "New Post",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            TextButton(
                enabled = selectedImageUri != null,
                onClick = {
                    Toast.makeText(
                        context,
                        "Post created successfully!",
                        Toast.LENGTH_SHORT
                    ).show()

                    selectedImageUri = null
                    caption = ""
                }
            ) {
                Text(
                    text = "Share",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        HorizontalDivider()

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // IMAGE PREVIEW
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(horizontal = 16.dp)
                .background(
                    Color(0xFFF2F2F2),
                    RoundedCornerShape(12.dp)
                )
                .clickable {
                    photoPicker.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts
                                .PickVisualMedia.ImageOnly
                        )
                    )
                },
            contentAlignment = Alignment.Center
        ) {

            if (selectedImageUri != null) {

                val bitmap = remember(selectedImageUri) {
                    try {
                        context.contentResolver.openInputStream(
                            selectedImageUri!!
                        )?.use { stream ->
                            BitmapFactory.decodeStream(stream)
                        }
                    } catch (e: Exception) {
                        null
                    }
                }

                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Selected photo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = "Unable to preview image",
                        color = Color.Gray
                    )
                }

            } else {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "＋",
                        fontSize = 60.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = "Tap to select a photo",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // SELECT PHOTO BUTTON
        Button(
            onClick = {
                photoPicker.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts
                            .PickVisualMedia.ImageOnly
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Choose from Gallery")
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // CAPTION INPUT
        OutlinedTextField(
            value = caption,
            onValueChange = {
                caption = it
            },
            label = {
                Text("Write a caption...")
            },
            placeholder = {
                Text("Share your moment ✨")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            minLines = 3,
            maxLines = 5,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Your post will be shared with your followers.",
            color = Color.Gray,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
