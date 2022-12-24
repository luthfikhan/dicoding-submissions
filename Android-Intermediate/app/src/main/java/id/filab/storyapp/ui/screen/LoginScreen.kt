package id.filab.storyapp.ui.screen

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import id.filab.storyapp.R
import id.filab.storyapp.dto.LoginPayload
import id.filab.storyapp.extensions.isValidEmail
import id.filab.storyapp.extensions.isValidPassword
import id.filab.storyapp.ui.components.Field
import id.filab.storyapp.ui.components.FieldType
import id.filab.storyapp.ui.layouts.AuthLayout
import id.filab.storyapp.ui.navigation.Routes
import id.filab.storyapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navigateTo: (route: String) -> Unit,
    userViewModel: UserViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var showLoader by remember { mutableStateOf(false) }
    val loginPayload by remember { mutableStateOf(LoginPayload()) }
    val emailOffsetY = remember { Animatable(1500f) }
    val passOffsetY = remember { Animatable(1500f) }
    val btnOffsetY = remember { Animatable(1500f) }

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        emailOffsetY.animateTo(targetValue = 0f,animationSpec = tween(800))
        passOffsetY.animateTo(targetValue = 0f,animationSpec = tween(800))
        btnOffsetY.animateTo(targetValue = 0f,animationSpec = tween(800))
    }

    if (showLoader) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }

    fun submitLogin () {
        if (loginPayload.email.isValidEmail() && loginPayload.password.isValidPassword()) {
            showLoader = true

            coroutineScope.launch {
                val token = userViewModel.submitLogin(loginPayload)

                if (token.isNotEmpty()) {
                    userViewModel.saveToken(token, context)
                    navigateTo(Routes.StoriesScreen.route)
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.login_failed_text),
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        } else {
            Toast.makeText(context, context.getString(R.string.signup_toast_invalid_input), Toast.LENGTH_LONG).show()
        }
    }

    AuthLayout(
        title = stringResource(R.string.login_title),
        subTitle = stringResource(R.string.login_subtitle)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStartPercent = 12,
                        topEndPercent = 12
                    )
                )
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .offset{ IntOffset(0, emailOffsetY.value.toInt()) }
            ){
                Field(
                    fieldType = FieldType.EMAIL_FIELD,
                    label = stringResource(R.string.login_email_label),
                    onChange = {
                        loginPayload.email = it
                    }
                )
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Box(
                modifier = Modifier
                    .offset{ IntOffset(0, passOffsetY.value.toInt()) }
            ) {
                Field(
                    fieldType = FieldType.PASSWORD_FIELD,
                    label = stringResource(R.string.login_password_label),
                    onChange = {
                        loginPayload.password = it
                    }
                )
            }
            Spacer(modifier = Modifier.padding(top = 40.dp))
            Box(
                modifier = Modifier
                    .offset{ IntOffset(0, btnOffsetY.value.toInt()) }
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        submitLogin()
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(R.string.login_button_label),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        ),
                    )
                }
            }
        }
    }
}
