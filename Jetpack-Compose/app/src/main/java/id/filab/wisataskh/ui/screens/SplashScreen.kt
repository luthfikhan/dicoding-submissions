package id.filab.wisataskh.ui.screens

import android.os.Handler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import id.filab.wisataskh.ui.navigation.Routes

@Composable
fun SplashScreen(
    navController: NavController,
) {
    LaunchedEffect(Unit) {
        Handler().postDelayed({
            navController.navigate(Routes.ListWisataSkhScreen.route) {
                popUpTo(Routes.SplashScreen.route) {
                    inclusive = true
                }
            }
        }, 1000)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = id.filab.wisataskh.R.string.app_name),
            textAlign = TextAlign.Center,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}