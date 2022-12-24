package id.filab.wisataskh.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.filab.wisataskh.R

@Composable()
fun ProfileScreen() {
    val profiles = listOf(
        mapOf("title" to "Email", "value" to "luthfikhoirulanwar55@gmail.com"),
        mapOf("title" to "Phone", "value" to "081555767610"),
    )

    Scaffold(
        backgroundColor = MaterialTheme.colors.primaryVariant,
    ) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .height(270.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 80.dp))
                    .background(MaterialTheme.colors.primarySurface)
                    .padding(12.dp)
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier.size(120.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.fiprofile),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.padding(top = 20.dp))
                Text(
                    "Luthfi Khoirul Anwar",
                    color = Color.White.copy(0.9f),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W500
                )
                Spacer(modifier = Modifier.padding(top = 4.dp))
                Text(
                    "Fullstack Developer",
                    color = Color.White.copy(0.75f),
                    fontSize = 18.sp,
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                profiles.map { item ->
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Text(
                        text = item["title"]!!,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.padding(top = 4.dp))
                    Text(
                        text = item["value"]!!,
                        fontSize = 16.sp,
                        color = Color.White.copy(0.75f),
                    )
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Divider(color = Color.White.copy(0.75f))
                }
            }
        }
    }
}
