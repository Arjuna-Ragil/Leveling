package com.example.leveling.main

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Composable
fun QuestTop() {
    val db = Firebase.firestore
    var gold by remember { mutableStateOf(0) }
    val user = Firebase.auth.currentUser

    user?.let {
        val userId = it.uid

        LaunchedEffect(userId) {
            db.collection("Users").document(userId)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.e("FireStore", "Failed to get gold")
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        gold = snapshot.getLong("money")?.toInt() ?: 0
                        Log.d("FireStore", "Gold: $gold")
                    }
                }
        }
    }


    Surface(
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp)
                .padding(horizontal = 20.dp)
        ) {
            val (title, money) = createRefs()
            Text(
                text = "Quest",
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }

            )

            Text(
                text =  "$gold Gold",
                modifier = Modifier
                    .constrainAs(money) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }

            )
        }
    }
}

@Composable
fun QuestContentTop(navControllerMain: NavController) {
    val db = Firebase.firestore
    var gold by remember { mutableStateOf(0) }
    val user = Firebase.auth.currentUser

    user?.let {
        val userId = it.uid

        LaunchedEffect(userId) {
            db.collection("Users").document(userId)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.e("FireStore", "Failed to get gold")
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        gold = snapshot.getLong("money")?.toInt() ?: 0
                        Log.d("FireStore", "Gold: $gold")
                    }
                }
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .padding(horizontal = 20.dp)
        ) {
            val (back,title, money) = createRefs()
            IconButton(
                onClick = {
                    navControllerMain.navigate("quest")
                },
                modifier = Modifier
                    .constrainAs(back) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)},
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back"
                )
            }

            Text(
                text = "Quest",
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(back.end)
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

            )
        }
    }
}

@Composable
fun ShopTop() {
    val db = Firebase.firestore
    var gold by remember { mutableStateOf(0) }
    val user = Firebase.auth.currentUser

    user?.let {
        val userId = it.uid

        LaunchedEffect(userId) {
            db.collection("Users").document(userId)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.e("FireStore", "Failed to get gold")
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        gold = snapshot.getLong("money")?.toInt() ?: 0
                        Log.d("FireStore", "Gold: $gold")
                    }
                }
        }
    }


    Surface(
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp)
                .padding(horizontal = 20.dp)
        ) {
            val (title, money) = createRefs()
            Text(
                text = "Shop",
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }

            )

            Text(
                text =  "$gold Gold",
                modifier = Modifier
                    .constrainAs(money) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }

            )
        }
    }
}

@Composable
fun InventoryTop() {
    val db = Firebase.firestore
    var gold by remember { mutableStateOf(0) }
    val user = Firebase.auth.currentUser

    user?.let {
        val userId = it.uid

        LaunchedEffect(userId) {
            db.collection("Users").document(userId)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.e("FireStore", "Failed to get gold")
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        gold = snapshot.getLong("money")?.toInt() ?: 0
                        Log.d("FireStore", "Gold: $gold")
                    }
                }
        }
    }


    Surface(
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp)
                .padding(horizontal = 20.dp)
        ) {
            val (title, money) = createRefs()
            Text(
                text = "Inventory",
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }

            )

            Text(
                text =  "$gold Gold",
                modifier = Modifier
                    .constrainAs(money) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }

            )
        }
    }
}