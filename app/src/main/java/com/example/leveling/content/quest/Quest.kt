package com.example.leveling.content.quest

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leveling.R
import com.example.leveling.main.QuestTop
import com.example.leveling.ui.theme.LevelingTheme
import com.example.leveling.ui.theme.background

@Composable
fun Quest(navControllerMain: NavController) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .then(background())
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
        ) {
            QuestTop()

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .border(4.dp, MaterialTheme.colorScheme.outline, RectangleShape)
                    .clickable(
                        onClick = {
                            navControllerMain.navigate("daily")
                        }
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.daily),
                    contentDescription = "Daily",
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer(
                            alpha = 0.4f
                        )
                )
                Text(
                    text = "Daily"
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .border(4.dp, MaterialTheme.colorScheme.outline, RectangleShape)
                    .clickable(
                        onClick = {
                            navControllerMain.navigate("weekly")
                        }
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.weekly),
                    contentDescription = "Weekly",
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer(
                            alpha = 0.4f
                        )
                )
                Text(
                    text = "Weekly"
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .border(4.dp, MaterialTheme.colorScheme.outline, RectangleShape)
                    .clickable(
                        onClick = {
                            navControllerMain.navigate("monthlyearly")
                        }
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.monthlyearly),
                    contentDescription = "Monthly and Yearly",
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer(
                            alpha = 0.4f
                        )
                )
                Text(
                    text = "Monthly / Yearly"
                )
            }
        }
    }
}