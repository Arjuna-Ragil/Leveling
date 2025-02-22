package com.example.leveling.content.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.leveling.main.QuestContentTop
import kotlinx.coroutines.time.delay

@Composable
fun Daily(navControllerMain: NavController) {

    var addDaily by remember { mutableStateOf("") }
    var toDo by remember { mutableStateOf(listOf<String>()) }

    val currentTime = remember { mutableStateOf(getCurrentTime()) }
    val currentDate = remember { mutableStateOf(getCurrentDate()) }

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
        val (topBar, date, list, input, add) = createRefs()

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

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(MaterialTheme.colorScheme.primary)
                .border(3.dp ,MaterialTheme.colorScheme.outline)
                .constrainAs(list) {
                    top.linkTo(date.bottom)
                    bottom.linkTo(input.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
        ) {
            items(toDo) { toDo ->
                Text(text = toDo)
            }
        }


        TextField(
            value = addDaily,
            onValueChange = { addDaily = it},
            placeholder = { Text(text = "Add Daily")},
            modifier = Modifier
                .padding(start = 10.dp)
                .padding(vertical = 10.dp)
                .fillMaxWidth(0.7f)
                .border(3.dp, MaterialTheme.colorScheme.outline)
                .constrainAs(input) {
                    top.linkTo(list.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )

        Button(
            onClick = {
                if (addDaily.isNotBlank()) {
                toDo = toDo + addDaily
                addDaily = ""
                    }
            },
            shape = RectangleShape,
            modifier = Modifier
                .padding(end = 10.dp)
                .padding(vertical = 10.dp)
                .border(3.dp, MaterialTheme.colorScheme.outline)
                .constrainAs(add) {
                    top.linkTo(list.bottom)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        ) {
            Text(text = "Add")
        }
    }
}