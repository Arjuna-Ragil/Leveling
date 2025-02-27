package com.example.leveling.content.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.leveling.content.quest.QuestCardContent
import com.example.leveling.content.quest.daily.DailyQuestCard
import com.example.leveling.login.AuthState
import com.example.leveling.login.LoginGoogleClient
import com.example.leveling.login.LoginViewModel
import com.example.leveling.ui.theme.background
import com.example.leveling.ui.theme.onError
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun Home(navControllerSecondary: NavController, navControllerMain: NavController,authViewModel: LoginViewModel) {
    val googleAuthClient = LoginGoogleClient(LocalContext.current)

    var isSignOutRequested by remember { mutableStateOf(false) }

    LaunchedEffect(isSignOutRequested) {
        if (isSignOutRequested) {
            googleAuthClient.signOut()
        }
    }

    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navControllerSecondary.navigate("login") {popUpTo("main") { inclusive = true }}
            else -> Unit
        }
    }

    val db = Firebase.firestore
    val todo = remember { mutableStateOf<List<QuestCardContent>>(emptyList()) }
    var gold by remember { mutableStateOf(0) }
    var level by remember { mutableStateOf(0) }
    var name by remember { mutableStateOf("") }
    val user = FirebaseAuth.getInstance().currentUser

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

            db.collection("Users").document(userId)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.e("FireStore", "Failed to get gold")
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        gold = snapshot.getLong("money")?.toInt() ?: 0
                        level = snapshot.getLong("level")?.toInt() ?: 0
                        name = snapshot.getString("name") ?: ""
                        Log.d("FireStore", "Gold: $gold")
                    }
                }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .then(background())
    ) {
        Surface(
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (quit, title, money) = createRefs()
                Button(
                    onClick = {
                        authViewModel.signout()
                        isSignOutRequested = true
                    },
                    colors = ButtonColors(
                        containerColor = com.example.leveling.ui.theme.error,
                        contentColor = onError,
                        disabledContentColor = onError,
                        disabledContainerColor = com.example.leveling.ui.theme.error,
                    ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .constrainAs(quit) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                        .padding(16.dp)
                        .border(4.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
                ) {
                    Text(text = "Quit")
                }
                Text(
                    text = "Home",
                    modifier = Modifier
                        .constrainAs(title) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(quit.end)
                            end.linkTo(money.start)
                        }
                )

                Text(
                    text = "$gold Gold",
                    modifier = Modifier
                        .constrainAs(money) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                        .padding(horizontal = 20.dp)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = user?.photoUrl,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
            )

            Text(
                text = if (name == "") "Guest" else name,
                modifier = Modifier
                    .padding(top = 10.dp)
            )

            Text(
                text = "Level: $level"
            )

            LinearProgressIndicator(
                progress = levelProgress(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(MaterialTheme.colorScheme.secondary),
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp)
                .padding(horizontal = 30.dp)
                .background(MaterialTheme.colorScheme.primary)
                .border(3.dp, MaterialTheme.colorScheme.outline)
        ) {
            item {
                Text(
                    text = "Daily Quest",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }
            items(todo.value) { toDo ->
                DailyQuestCard(navControllerMain,toDo)
            }
        }
    }
}
