package com.example.leveling.content.quest.daily

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.leveling.content.quest.QuestCardContent
import com.example.leveling.content.quest.addDailyToFirestore
import com.example.leveling.content.quest.getCurrentDate
import com.example.leveling.content.quest.getCurrentTime
import com.example.leveling.main.QuestContentTop
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun Daily(navControllerMain: NavController) {
    val db = Firebase.firestore
    val todo = remember { mutableStateOf<List<QuestCardContent>>(emptyList()) }
    val user = FirebaseAuth.getInstance().currentUser

    val currentTime = remember { mutableStateOf(getCurrentTime()) }
    val currentDate = remember { mutableStateOf(getCurrentDate()) }

    user?.let {
        val userId = it.uid

        LaunchedEffect(Unit) {
            db.collection("Users").document(userId)
                .collection("Daily")
                .get()
                .addOnSuccessListener { document ->
                    todo.value = document.map { val quest = it.toObject(QuestCardContent::class.java)
                        quest.id = it.id
                        quest
                    }
                    Log.d("FireStore", "Daily Quest Retrieve: ${todo.value}")
                }
                .addOnFailureListener { e ->
                    Log.e("FireStore", "Failed to retrieve daily quest")
                }
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000L)
            currentTime.value = getCurrentTime()
            currentDate.value = getCurrentDate()
        }
    }

    ConstraintLayout (
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (topBar, date, list, button) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            QuestContentTop(navControllerMain)
        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .border(3.dp, MaterialTheme.colorScheme.outline)
                .padding(16.dp)
                .constrainAs(date) {
                    top.linkTo(topBar.bottom)
                    bottom.linkTo(list.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(text = currentDate.value)
            Text(text = currentTime.value)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(list) {
                    top.linkTo(date.bottom)
                    bottom.linkTo(button.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                contentPadding = PaddingValues(10.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .border(3.dp ,MaterialTheme.colorScheme.outline)

            ) {
                items(todo.value) { toDo ->
                    DailyQuestCard(toDo)
                }
            }
        }
            Button(
                onClick = {
                    navControllerMain.navigate("dailymodify")
                },
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(vertical = 10.dp)
                    .border(3.dp, MaterialTheme.colorScheme.outline)
                    .constrainAs(button) {
                        top.linkTo(list.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }

            ) {
                Text(text = "Modify")
            }

    }
}