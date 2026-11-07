
package com.example.insta

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CommentsDialog(
    comments: List<String>,
    onAddComment: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var newComment by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Comments")
        },
        text = {
            Column {
                if (comments.isEmpty()) {
                    Text("No comments yet. Be the first!")
                } else {
                    LazyColumn(
                        modifier = Modifier.heightIn(
                            max = 250.dp
                        )
                    ) {
                        items(comments) { comment ->
                            Text(
                                text = comment,
                                modifier = Modifier.padding(
                                    vertical = 8.dp
                                )
                            )
                            HorizontalDivider()
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                OutlinedTextField(
                    value = newComment,
                    onValueChange = {
                        newComment = it
                    },
                    placeholder = {
                        Text("Write a comment...")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = newComment.isNotBlank(),
                onClick = {
                    onAddComment(newComment.trim())
                    newComment = ""
                }
            ) {
                Text("Post")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
