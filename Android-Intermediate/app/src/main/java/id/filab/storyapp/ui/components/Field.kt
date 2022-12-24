package id.filab.storyapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.filab.storyapp.R
import id.filab.storyapp.extensions.isValidEmail
import id.filab.storyapp.extensions.isValidPassword
import id.filab.storyapp.utils.Debounce

enum class FieldType {
    PASSWORD_FIELD,
    EMAIL_FIELD,
    NORMAL
}

@Composable
fun Field(
    fieldType: FieldType = FieldType.NORMAL,
    onChange: (v: String) -> Unit,
    label: String = ""
) {

    var value by remember { mutableStateOf("") }
    var invalidValue by remember { mutableStateOf(false) }
    var secureText by remember { mutableStateOf(fieldType == FieldType.PASSWORD_FIELD) }

    val debounce = Debounce()

    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            singleLine = true,
            textStyle = TextStyle(color = Color.Gray, fontSize = 16.sp),
            label = {
                    Text(
                        text = label
                    )
            },
            onValueChange = {
                value = it
                onChange(it)
                invalidValue = false

                if (fieldType == FieldType.PASSWORD_FIELD || fieldType == FieldType.EMAIL_FIELD) {
                    debounce.debounce(delayMillis = 1000, callback = {
                        when (fieldType) {
                            FieldType.PASSWORD_FIELD -> {
                                if (it.isNotEmpty() && !it.isValidPassword()) {
                                    invalidValue = true
                                }
                            }
                            FieldType.EMAIL_FIELD -> {
                                if (it.isNotEmpty() && !it.isValidEmail()) {
                                    invalidValue = true
                                }
                            }
                            else -> {}
                        }
                    })
                }
            },
            isError = invalidValue,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (secureText) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (fieldType == FieldType.PASSWORD_FIELD) {
                    IconButton(onClick = { secureText = !secureText }) {
                        Icon(
                            imageVector =
                                if (secureText) Icons.Filled.VisibilityOff
                                else Icons.Filled.Visibility ,
                            contentDescription =
                                if (secureText) stringResource(id = R.string.field_password_visibility_off_description)
                                else stringResource(id = R.string.field_password_visibility_description),
                        )
                    }
                }
            }
        )

        if (invalidValue) {
            val errorPassText = stringResource(id = R.string.field_password_error_text)
            val errEmailText = stringResource(id = R.string.field_email_error_text)

            Spacer(modifier = Modifier.padding(top = 4.dp))
            Text(
                text = if (fieldType == FieldType.EMAIL_FIELD) errEmailText else errorPassText,
                color = Color.Red
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    Field(onChange = {})
}