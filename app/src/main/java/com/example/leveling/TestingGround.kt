package com.example.leveling

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.leveling.ui.theme.LevelingTheme
import com.example.leveling.ui.theme.onError

@Composable
fun Test() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Surface(
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, MaterialTheme.colorScheme.outline)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (quit, money, amount) = createRefs()
                Button(
                    onClick = {

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
                    text = "Money",
                    modifier = Modifier
                        .constrainAs(money) {
                            top.linkTo(parent.top)
                            bottom.linkTo(amount.top)
                            start.linkTo(quit.end)
                            end.linkTo(parent.end)
                        }
                        .padding(horizontal = 16.dp)
                )

                Text(
                    text = "1000" + " Gold",
                    modifier = Modifier
                        .constrainAs(amount) {
                            top.linkTo(money.bottom)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(quit.end)
                            end.linkTo(parent.end)
                        }
                        .padding(horizontal = 16.dp)
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
            Image(
                painter = painterResource(id = R.drawable.logo_leveling),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
                    .padding(10.dp)
            )

            Text(
                text = "Name"
            )

            Text(
                text = "Level: " + "90"
            )

            LinearProgressIndicator(
                progress = { 0.9f },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(30.dp)
                .background(MaterialTheme.colorScheme.primary)
                .border(3.dp, MaterialTheme.colorScheme.outline)
        ) {
            item {
                Text(text = "hello")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    LevelingTheme {
        Test()
    }
}