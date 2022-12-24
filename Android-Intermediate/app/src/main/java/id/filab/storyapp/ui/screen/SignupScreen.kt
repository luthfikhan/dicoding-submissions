package id.filab.storyapp.ui.screen

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import id.filab.storyapp.R
import id.filab.storyapp.dto.RegisterPayload
import id.filab.storyapp.extensions.isValidEmail
import id.filab.storyapp.extensions.isValidPassword
import id.filab.storyapp.ui.components.Field
import id.filab.storyapp.ui.components.FieldType
import id.filab.storyapp.ui.layouts.AuthLayout
import id.filab.storyapp.ui.navigation.Routes
import id.filab.storyapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    navigateTo: (route: String) -> Unit,
    userViewModel: UserViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var showLoader by remember { mutableStateOf(false) }
    val registerPayload by remember { mutableStateOf(RegisterPayload()) }
    val context = LocalContext.current

    val nameOffsetY = remember { Animatable(1500f) }
    val emailOffsetY = remember { Animatable(1500f) }
    val passOffsetY = remember { Animatable(1500f) }
    val btnOffsetY = remember { Animatable(1500f) }

    LaunchedEffect(key1 = Unit) {
        nameOffsetY.animateTo(targetValue = 0f,animationSpec = tween(800))
        emailOffsetY.animateTo(targetValue = 0f,animationSpec = tween(800))
        passOffsetY.animateTo(targetValue = 0f,animationSpec = tween(800))
        btnOffsetY.animateTo(targetValue = 0f,animationSpec = tween(800))
    }

    if (showLoader) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }

     fun submitRegister ()  {
        if (registerPayload.name.isNotEmpty() && registerPayload.email.isValidEmail() && registerPayload.password.isValidPassword()) {
            showLoader = true

            coroutineScope.launch {
                val success = userViewModel.submitRegister(registerPayload)

                if (success) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.signup_success_text),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.signup_failed_text),
                        Toast.LENGTH_LONG
                    ).show()
                }
                showLoader = false
            }
        } else {
            Toast.makeText(context, context.getString(R.string.signup_toast_invalid_input), Toast.LENGTH_LONG).show()
        }
    }

    val loginQAnnotation = buildAnnotatedString {
        val text = stringResource(R.string.signup_login_question_text)
        val start = text.indexOf("Login")
        val end = start + 5

        append(text)
        addStringAnnotation(
            start = start,
            end = end,
            tag = "login",
            annotation = "login"
        )
        addStyle(
            style = SpanStyle(color = MaterialTheme.colors.primary),
            start = start,
            end = end
        )
    }

    AuthLayout(
        title = stringResource(R.string.signup_title),
        subTitle = stringResource(R.string.signup_subtitle)
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
                    .offset{ IntOffset(0, nameOffsetY.value.toInt()) }
            ) {
                Field(
                    label = stringResource(R.string.signup_name_label),
                    onChange = {
                        registerPayload.name = it
                    }
                )
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Box(
                modifier = Modifier
                    .offset{ IntOffset(0, emailOffsetY.value.toInt()) }
            ) {
                Field(
                    fieldType = FieldType.EMAIL_FIELD,
                    label = stringResource(R.string.signup_email_label),
                    onChange = {
                        registerPayload.email = it
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
                    label = stringResource(R.string.signup_password_label),
                    onChange = {
                        registerPayload.password = it
                    }
                )
            }
            Spacer(modifier = Modifier.padding(top = 40.dp))
            Column(
                modifier = Modifier
                    .offset{ IntOffset(0, btnOffsetY.value.toInt()) }
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        submitRegister()
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(R.string.signup_button_label),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        ),
                    )
                }
                Spacer(modifier = Modifier.padding(top = 16.dp))
                ClickableText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    text = loginQAnnotation,
                    onClick = {
                        loginQAnnotation.getStringAnnotations("login", it, it)
                            .firstOrNull()?.let {
                                navigateTo(Routes.LoginScreen.route)
                            }
                    }
                )
            }
        }
    }
}
