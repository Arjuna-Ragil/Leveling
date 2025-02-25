package com.example.leveling.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leveling.R
import com.example.leveling.content.quest.dailyReset
import com.example.leveling.content.quest.isNewDay
import com.example.leveling.ui.theme.background
import com.example.leveling.ui.theme.loginBackground
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

//Welcome Screen
@Composable
fun Title() {
    Text(
        text = "Leveling",
        style = MaterialTheme.typography.displayLarge,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun SubTitle1(navController: NavController) {

    val logo = if (isSystemInDarkTheme()) {
        painterResource(id = R.drawable.logo_leveling)
    } else {
        painterResource(id = R.drawable.logo_leveling_light)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
        ){
            Image(
                painter = logo,
                contentDescription = "Leveling Logo",
                modifier = Modifier
                    .padding(20.dp)
            )
        }

        Text(
            text = "Welcome to Leveling",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Button(
            onClick = { navController.navigate("2") },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 35.dp)
                .border(3.dp, MaterialTheme.colorScheme.outline, shape = CircleShape)
        ) { Text(text = "Next") }
    }
}

@Composable
fun SubTitle2(navController: NavController) {

    val logo = if (isSystemInDarkTheme()) {
        painterResource(id = R.drawable.bad_thought)
    } else {
        painterResource(id = R.drawable.bad_thought_light)
    }

     Box(
         modifier = Modifier
             .fillMaxSize()
             .padding(16.dp)
             .padding(horizontal = 20.dp)
         ,
         contentAlignment = Alignment.Center
     ) {
         ConstraintLayout {
             val (image, text, next, back) = createRefs()

             Box(
                 contentAlignment = Alignment.Center,
                 modifier = Modifier
                     .fillMaxWidth()
                     .fillMaxHeight(0.4f)
                     .constrainAs(image) {
                         top.linkTo(parent.top)
                         start.linkTo(parent.start)
                         end.linkTo(parent.end)}
             ){
                 Image(
                     painter = logo,
                     contentDescription = "Leveling Logo",
                     modifier = Modifier
                         .padding(vertical = 20.dp)
                 )
             }

             Text(
                 text = "Do you hate yourself?",
                 style = MaterialTheme.typography.bodyLarge,
                 color = MaterialTheme.colorScheme.onSurface,
                 modifier = Modifier
                     .padding(top = 12.dp)
                     .padding(bottom = 16.dp)
                     .constrainAs(text) {
                         top.linkTo(image.bottom)
                         start.linkTo(parent.start)
                         end.linkTo(parent.end)
                     })

             Button(
                 onClick = { navController.navigate("1") },
                 shape = RoundedCornerShape(50),
                 modifier = Modifier
                     .padding(end = 20.dp)
                     .border(3.dp, MaterialTheme.colorScheme.outline, shape = CircleShape)
                     .constrainAs(back) {
                         top.linkTo(text.bottom)
                         start.linkTo(parent.start)
                         end.linkTo(next.start)
                     }
             ) { Text(text = "Back") }

             Button(
                 onClick = { navController.navigate("3") },
                 shape = RoundedCornerShape(50),
                 modifier = Modifier
                     .padding(start = 20.dp)
                     .border(3.dp, MaterialTheme.colorScheme.outline, shape = CircleShape)
                     .constrainAs(next) {
                         top.linkTo(text.bottom)
                         end.linkTo(parent.end)
                         start.linkTo(back.end)
                     }
             ) { Text(text = "Next") }
         }
     }
}


@Composable
fun SubTitle3(navController: NavController) {

    val logo = if (isSystemInDarkTheme()) {
        painterResource(id = R.drawable.app_help)
    } else {
        painterResource(id = R.drawable.app_help_light)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
        ){
            Image(
                painter = logo,
                contentDescription = "Leveling Logo",
                modifier = Modifier
                    .padding(20.dp)
            )
        }

        Text(
            text = "Our app will make you better",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge)

        Button(
            onClick = {navController.navigate("2")},
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 35.dp)
                .border(3.dp, MaterialTheme.colorScheme.outline, shape = CircleShape)
        ) { Text(text = "back") }
    }
}

@Composable
fun WlcBtn(navController: NavController) {
    Button(
        onClick = { navController.navigate("login")
        },
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .border(3.dp, MaterialTheme.colorScheme.outline, shape = CircleShape)
    ) { Text(text = "Login") }
}

@Composable
fun WelcomeNav() {
    val navControllerWelcome = rememberNavController()
    NavHost(navController = navControllerWelcome, startDestination = "1") {
        composable("1") { SubTitle1(navControllerWelcome) }
        composable("2") { SubTitle2(navControllerWelcome) }
        composable("3") { SubTitle3(navControllerWelcome) }
    }
}

@Composable
fun Welcome(navControllerSecondary: NavController, authViewModel: LoginViewModel) {
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                val user = Firebase.auth.currentUser
                user?.let {
                    val userid = it.uid

                    if (isNewDay(context)) {
                        dailyReset(userid)
                    }
                }

                navControllerSecondary.navigate("main"){popUpTo("welcome") { inclusive = true }}
            }
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .then(loginBackground())
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(40.dp)
                .fillMaxSize(),
        ){
            val (title, subtitle ,wlcBtn) = createRefs()
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = 55.dp)
            ) {
                Title()
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(subtitle) {
                        top.linkTo(title.bottom)
                        bottom.linkTo(wlcBtn.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)}
            ) {
                WelcomeNav()
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .fillMaxWidth()
                    .constrainAs(wlcBtn) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                WlcBtn(navController = navControllerSecondary)
            }
        }
    }
}