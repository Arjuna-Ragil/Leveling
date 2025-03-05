package com.example.leveling.content.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.leveling.R
import com.example.leveling.main.ShopTop
import com.example.leveling.ui.theme.background
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun Shop() {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val user = Firebase.auth.currentUser

    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .then(background())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            ShopTop()

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val (five, ten, twenty, fifty, hundred, fiveHundred, oneMillion, fiveMillion, customexchange) = createRefs()

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .border(2.dp, MaterialTheme.colorScheme.outline)
                        .constrainAs(five) {
                            top.linkTo(parent.top)
                            bottom.linkTo(twenty.top)
                            start.linkTo(parent.start)
                            end.linkTo(ten.start)
                        }
                        .clickable {
                            val user = Firebase.auth.currentUser
                            user?.let {
                                val userId = it.uid
                                exchange(userId, context, 500, "5K")
                            }
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.five),
                            contentDescription = "five thousand",
                            modifier = Modifier
                                .size(100.dp)
                        )

                        Text("5K")
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .border(2.dp, MaterialTheme.colorScheme.outline)
                        .constrainAs(ten) {
                            top.linkTo(parent.top)
                            bottom.linkTo(fifty.top)
                            start.linkTo(five.end)
                            end.linkTo(parent.end)
                        }
                        .clickable {
                            val user = Firebase.auth.currentUser
                            user?.let {
                                val userId = it.uid
                                exchange(userId, context, 1000, "10K")
                            }
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ten),
                            contentDescription = "five thousand",
                            modifier = Modifier
                                .size(100.dp)
                        )

                        Text("10K")
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .border(2.dp, MaterialTheme.colorScheme.outline)
                        .constrainAs(twenty) {
                            top.linkTo(five.bottom)
                            bottom.linkTo(hundred.top)
                            start.linkTo(parent.start)
                            end.linkTo(fifty.start)
                        }
                        .clickable {
                            val user = Firebase.auth.currentUser
                            user?.let {
                                val userId = it.uid
                                exchange(userId, context, 2000, "20K")
                            }
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.twenty),
                            contentDescription = "twenty thousand",
                            modifier = Modifier
                                .size(100.dp)
                        )

                        Text("20K")
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .border(2.dp, MaterialTheme.colorScheme.outline)
                        .constrainAs(fifty) {
                            top.linkTo(ten.bottom)
                            bottom.linkTo(fiveHundred.top)
                            start.linkTo(twenty.end)
                            end.linkTo(parent.end)
                        }
                        .clickable {
                            val user = Firebase.auth.currentUser
                            user?.let {
                                val userId = it.uid
                                exchange(userId, context, 5000, "50K")
                            }
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.fifty),
                            contentDescription = "fifty thousand",
                            modifier = Modifier
                                .size(100.dp)
                        )

                        Text("50K")
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .border(2.dp, MaterialTheme.colorScheme.outline)
                        .constrainAs(hundred) {
                            top.linkTo(twenty.bottom)
                            bottom.linkTo(oneMillion.top)
                            start.linkTo(parent.start)
                            end.linkTo(fiveHundred.start)
                        }
                        .clickable {
                            val user = Firebase.auth.currentUser
                            user?.let {
                                val userId = it.uid
                                exchange(userId, context, 10000, "100K")
                            }
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hundred),
                            contentDescription = "hundred thousand",
                            modifier = Modifier
                                .size(100.dp)
                        )

                        Text("100K")
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .border(2.dp, MaterialTheme.colorScheme.outline)
                        .constrainAs(fiveHundred) {
                            top.linkTo(fifty.bottom)
                            bottom.linkTo(fiveMillion.top)
                            start.linkTo(hundred.end)
                            end.linkTo(parent.end)
                        }
                        .clickable {
                            val user = Firebase.auth.currentUser
                            user?.let {
                                val userId = it.uid
                                exchange(userId, context, 50000, "500K")
                            }
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.fivehundred),
                            contentDescription = "five hundred thousand",
                            modifier = Modifier
                                .size(100.dp)
                        )

                        Text("500K")
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .border(2.dp, MaterialTheme.colorScheme.outline)
                        .constrainAs(oneMillion) {
                            top.linkTo(hundred.bottom)
                            bottom.linkTo(customexchange.top)
                            start.linkTo(parent.start)
                            end.linkTo(fiveMillion.start)
                        }
                        .clickable {
                            val user = Firebase.auth.currentUser
                            user?.let {
                                val userId = it.uid
                                exchange(userId, context, 100000, "1M")
                            }
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.onemillion),
                            contentDescription = "one million",
                            modifier = Modifier
                                .size(100.dp)
                        )

                        Text("1M")
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .border(2.dp, MaterialTheme.colorScheme.outline)
                        .constrainAs(fiveMillion) {
                            top.linkTo(fiveHundred.bottom)
                            bottom.linkTo(customexchange.top)
                            start.linkTo(oneMillion.end)
                            end.linkTo(parent.end)
                        }
                        .clickable {
                            val user = Firebase.auth.currentUser
                            user?.let {
                                val userId = it.uid
                                exchange(userId, context, 500000, "5M")
                            }
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.fivemillion),
                            contentDescription = "five million",
                            modifier = Modifier
                                .size(100.dp)
                        )

                        Text("5M")
                    }
                }

                Button(
                    onClick = { showDialog = true},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .padding(horizontal = 20.dp)
                        .border(3.dp, MaterialTheme.colorScheme.outline, CircleShape)
                        .constrainAs(customexchange) {
                            top.linkTo(fiveMillion.bottom)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                             end.linkTo(parent.end)
                        }
                ) {
                    Text("Custom Exchange")
                }
            }
        }
        user?.let {
            val userId = it.uid

            CustomExhangeDialog(
                userId = userId,
                context = context,
                showDialog = showDialog,
                onDismissRequest = { showDialog = false }
            )
        }
    }
}