package com.example.leveling.content.quest.weekly

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.leveling.content.quest.QuestCardContent
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

@Composable
fun WeeklyQuestCard(questCardContent: QuestCardContent) {
    var done by remember { mutableStateOf(questCardContent.done) }
    val user = Firebase.auth.currentUser
    val db = Firebase.firestore

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !done) {

                user?.let {
                    val userId = it.uid

                    db.collection("Users").document(userId)
                        .collection("Weekly")
                        .document(questCardContent.id)
                        .update("done", true)
                        .addOnSuccessListener {
                            done = true
                            Log.d("FireStore", "Quest Done")

                            db.collection("Users").document(userId)
                                .update(
                                    mapOf(
                                        "money" to FieldValue.increment(questCardContent.reward.toLong()),
                                        "exp" to FieldValue.increment(300)
                                    ))
                                .addOnSuccessListener {
                                    Log.d("FireStore", "Money and XP have been updated")
                                }
                                .addOnFailureListener { e ->
                                    Log.e("FireStore", "Failed to update money and XP, ${e.message}")
                                }
                        }
                        .addOnFailureListener { e ->
                            Log.e("FireStore", "Failed to update quest, ${e.message}")
                        }
                }
            },
        color = if (questCardContent.done || done) MaterialTheme.colorScheme.surfaceDim else MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, MaterialTheme.colorScheme.outline)
                .padding(10.dp)
        ) {

            Text(
                text = questCardContent.title
            )

            Text(
                text = questCardContent.description
            )

            Text(
                text = questCardContent.reward + " Gold"
            )
        }
    }
}

@Composable
fun ModifyWeeklyQuestCard(questCardContent: QuestCardContent, onEditClick: (QuestCardContent) -> Unit, onDeleteClick: (QuestCardContent) -> Unit) {
    val done by remember { mutableStateOf(questCardContent.done) }

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = if (questCardContent.done || done) MaterialTheme.colorScheme.surfaceDim else MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, MaterialTheme.colorScheme.outline)
                .padding(10.dp)
        ) {

            Text(
                text = questCardContent.title
            )

            Text(
                text = questCardContent.description
            )

            Text(
                text = questCardContent.reward + " Gold"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onEditClick(questCardContent)},
                    modifier = Modifier
                        .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
                ) {
                    Text("Edit")
                }

                Button(
                    onClick = { onDeleteClick(questCardContent)},
                    modifier = Modifier
                        .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
                ) {
                    Text("Delete")
                }
            }
        }
    }
}

@Composable
fun DeleteWeeklyDialog(questCardContent: QuestCardContent, onDismiss:() -> Unit, onDelete: (QuestCardContent) -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() }
        ) {
            Box(
                modifier = Modifier
                    .width(250.dp)
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.tertiary)
                    .border(3.dp, MaterialTheme.colorScheme.outline)
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        "Are you sure you want to delete this quest",
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = {
                            onDelete(questCardContent)
                            onDismiss()
                        }
                    ) {
                        Text("Delete")
                    }

                    Button(
                        onClick = { onDismiss() }
                    ) {
                        Text("Cancel")
                    }
                }
            }
    }

}

@Composable
fun EditWeeklyDialog( questCardContent: QuestCardContent, onDismiss:() -> Unit, onSave: (QuestCardContent) -> Unit) {
    var title by remember { mutableStateOf(questCardContent.title) }
    var description by remember { mutableStateOf(questCardContent.description) }
    var reward by remember { mutableStateOf(questCardContent.reward) }

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(400.dp)
                .background(MaterialTheme.colorScheme.tertiary)
                .border(3.dp, MaterialTheme.colorScheme.outline)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text("Edit Weekly Quest")

                TextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("Title") },
                    singleLine = true
                )

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text("Description") },
                )

                TextField(
                    value = reward,
                    onValueChange = { reward = it },
                    placeholder = { Text("Reward") },
                    singleLine = true
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            title = ""
                            description = ""
                            reward = ""
                            onDismiss()
                        }
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            onSave(questCardContent.copy(
                                title = title,
                                description = description,
                                reward = reward
                            ))
                            title = ""
                            description = ""
                            reward = ""
                            onDismiss()
                        }
                    ) {
                        Text("Add Quest")
                    }
                }
            }
        }
    }
}

@Composable
fun AddWeeklyDialog( showDialog: Boolean, onDismiss:() -> Unit, onAdd: (String, String, String, Boolean) -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var reward by remember { mutableStateOf("") }
    val done by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {

            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(400.dp)
                    .background(MaterialTheme.colorScheme.tertiary)
                    .border(3.dp, MaterialTheme.colorScheme.outline)
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text("Add New Weekly Quest")

                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = { Text("Title") },
                        singleLine = true
                    )

                    TextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("Description") },
                    )

                    TextField(
                        value = reward,
                        onValueChange = { reward = it },
                        placeholder = { Text("Reward") },
                        singleLine = true
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                title = ""
                                description = ""
                                reward = ""
                                onDismiss()
                            }
                        ) {
                            Text("Cancel")
                        }

                        Button(
                            onClick = {
                                onAdd(title, description, reward, done)
                                title = ""
                                description = ""
                                reward = ""
                                onDismiss()
                            }
                        ) {
                            Text("Add Quest")
                        }
                    }
                }
            }
        }
    }
}