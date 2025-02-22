package com.example.leveling.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leveling.ui.theme.loginBackground
import com.example.leveling.ui.theme.secondary
import com.example.leveling.ui.theme.secondaryLight
import com.example.leveling.ui.theme.tertiary
import com.example.leveling.ui.theme.tertiaryLight
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

//login
@Composable
fun LoginMethod(navControllerSecondary: NavHostController, navControllerLogin: NavHostController) {
    val googleAuthClient = LoginGoogleClient(LocalContext.current)
    val context = LocalContext.current

    var isSignInRequested by remember { mutableStateOf(false) }

    LaunchedEffect(isSignInRequested) {
        if (isSignInRequested) {

            val isSignInSuccesfully = googleAuthClient.signIn()

            if (isSignInSuccesfully) {
                navControllerSecondary.navigate("main") {popUpTo("login") { inclusive = true }}
            } else {
                Toast.makeText(context, "Sign in with Google failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = {
                navControllerLogin.navigate("SignIn")
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .border(3.dp, MaterialTheme.colorScheme.outline, shape = CircleShape)
        ) {
            Text(text = "Sign In")
        }

        Button(
            onClick = {
                navControllerLogin.navigate("SignUp")
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .border(3.dp, MaterialTheme.colorScheme.outline, shape = CircleShape)
        ) {
            Text(text =  "Sign Up")
        }

        Text(
            text = "Sign in with other method",
            color = MaterialTheme.colorScheme.onSurface
        )

        Button(
            onClick = {
                isSignInRequested = true
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .border(3.dp, MaterialTheme.colorScheme.outline, shape = CircleShape)
        ) {
            Text(text = "Google")
        }
    }
}

@Composable
fun SignIn(navControllerSecondary: NavHostController, navControllerLogin: NavHostController, authViewModel: LoginViewModel) {
    val db = Firebase.firestore

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                navControllerSecondary.navigate("main") {
                    popUpTo("login") { inclusive = true }
                }
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    val textFieldColor = if (isSystemInDarkTheme()) {

        TextFieldDefaults.colors(
            focusedContainerColor = tertiary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = secondary,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = secondary,
            cursorColor = Color.White,
        )
    } else {

        TextFieldDefaults.colors(
            focusedContainerColor = tertiaryLight,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = secondaryLight,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = secondaryLight,
            cursorColor = Color.Black,
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
    ) {
        TextField(
            value = email,
            onValueChange = {email = it},
            placeholder = { Text(text = "Email") },
            singleLine = true,
            colors = textFieldColor,
        )

        TextField(
            value = password,
            onValueChange = {password = it},
            placeholder = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            colors = textFieldColor
        )

        Button(
            onClick = {
                authViewModel.signin(email, password)
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .border(3.dp, MaterialTheme.colorScheme.outline, shape = CircleShape)
        ) {
            Text(text = "Sign In")
        }

        Text(
            text = "Don't have an account?",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .clickable {
                    navControllerLogin.navigate("SignUp") {
                        popUpTo("SignIn") {inclusive = true}
                    }
                }
        )
    }
}

@Composable
fun SignUp(navControllerSecondary: NavHostController, navControllerLogin: NavHostController, authViewModel: LoginViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                navControllerSecondary.navigate("main") {
                    popUpTo("login") { inclusive = true }
                }
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    val textFieldColor = if (isSystemInDarkTheme()) {

        TextFieldDefaults.colors(
            focusedContainerColor = tertiary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = secondary,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = secondary,
            cursorColor = Color.White,
        )
    } else {

        TextFieldDefaults.colors(
            focusedContainerColor = tertiaryLight,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = secondaryLight,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = secondaryLight,
            cursorColor = Color.Black,
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
    ) {
        TextField(
            value = email,
            onValueChange = {email = it},
            placeholder = { Text(text = "Email") },
            singleLine = true,
            colors = textFieldColor
        )

        TextField(
            value = password,
            onValueChange = {password = it},
            placeholder = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            colors = textFieldColor,
            modifier = Modifier
                .padding(vertical = 7.dp)
        )

        TextField(
            value = confirmPassword,
            onValueChange = {confirmPassword = it},
            placeholder = { Text(text = "Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            colors = textFieldColor,
        )

        Button(
            onClick = {
                authViewModel.signup(email, password, confirmPassword)
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp)
                .padding(horizontal = 30.dp)
                .border(3.dp, MaterialTheme.colorScheme.outline, shape = CircleShape)
        ) {
            Text(text = "Sign Up")
        }

        Text(
            text = "Already have an account?",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .clickable {
                    navControllerLogin.navigate("SignIn") {
                        popUpTo("SignUp") {inclusive = true}
                    }
                }
        )
    }
}

@Composable
fun LoginNav(navControllerSecondary: NavHostController) {
    val navControllerLogin = rememberNavController()

    NavHost(navController = navControllerLogin, startDestination = "loginMethod") {
        composable("loginMethod") { LoginMethod(navControllerSecondary, navControllerLogin) }
        composable("SignIn") { SignIn(navControllerSecondary, navControllerLogin, LoginViewModel()) }
        composable("SignUp") { SignUp(navControllerSecondary, navControllerLogin, LoginViewModel()) }
    }
}

@Composable
fun Login(navControllerSecondary: NavHostController) {

    val logo = if (isSystemInDarkTheme()) {
        painterResource(id = com.example.leveling.R.drawable.logo_leveling)
    } else {
        painterResource(id = com.example.leveling.R.drawable.logo_leveling_light)
    }

    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .then(loginBackground())
    ) {
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

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f)
                ){
                    LoginNav(navControllerSecondary)
                }

            }
    }
}