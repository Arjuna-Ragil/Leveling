package com.example.leveling.content.quest

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.leveling.login.Login
import com.example.leveling.ui.theme.LevelingTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore


data class QuestCardContent(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var reward: String = "",
    var done: Boolean = false,
)

@Composable
fun QuestCard(questCardContent: QuestCardContent) {
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
                        .collection("Quest")
                        .document(questCardContent.id)
                        .update("done", true)
                        .addOnSuccessListener {
                            done = true
                            Log.d("FireStore", "Quest Done")

                            db.collection("Users").document(userId)
                                .update(
                                    mapOf(
                                        "money" to FieldValue.increment(questCardContent.reward.toLong()),
                                        "exp" to FieldValue.increment(100)
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
fun AddQuestDialog( showDialog: Boolean, onDismiss:() -> Unit, onAdd: (String, String, String, Boolean) -> Unit) {
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
                    Text("Add New Quest")

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