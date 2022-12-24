package id.filab.storyapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import id.filab.storyapp.R
import id.filab.storyapp.ui.navigation.Routes
import id.filab.storyapp.viewmodel.UserViewModel
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun SplashScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val token = userViewModel.getToken(context).firstOrNull()
        userViewModel.token = token ?: ""
        val isLoggedIn = token?.isNotEmpty() ?: false

        navController.navigate(if (isLoggedIn) Routes.StoriesScreen.route else Routes.SignupScreen.route) {
            popUpTo(Routes.SplashScreen.route) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colors.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = stringResource(id = R.string.copyright),
            color = Color.White
        )
    }
}