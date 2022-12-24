package id.filab.storyapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.filab.storyapp.R
import id.filab.storyapp.dto.ListStory

@Composable
fun StoryDetailScreen(
    storyItem: ListStory,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Story by ${
                        if (storyItem.name?.isNotEmpty() == true) storyItem.name
                        else stringResource(R.string.guest_text)
                    }")
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(storyItem.photoUrl)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))
                Text(
                    text = stringResource(R.string.story_detail_desc_text),
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.primary
                )
                Text(
                    text = storyItem.description ?: "",
                    color = Color.Gray
                )
            }
        }
    }
}
