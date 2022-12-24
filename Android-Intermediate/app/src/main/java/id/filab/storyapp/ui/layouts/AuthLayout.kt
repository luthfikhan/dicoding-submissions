package id.filab.storyapp.ui.layouts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthLayout(
    title: String,
    subTitle: String,
    child: @Composable (ColumnScope.() -> Unit)
) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        BoxWithConstraints {
            Box(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)

            ) {
                Column(
                    modifier = Modifier
                        .heightIn(min = LocalConfiguration.current.screenHeightDp.dp)
                        .height(this@BoxWithConstraints.maxHeight)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 40.dp)
                    ) {
                        Text(
                            text = title,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = subTitle,
                            fontSize = 18.sp
                        )
                    }
                    child()
                }
            }
        }
    }
}
