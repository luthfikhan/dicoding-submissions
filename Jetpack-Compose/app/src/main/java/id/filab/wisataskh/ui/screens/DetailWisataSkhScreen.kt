package id.filab.wisataskh.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.filab.wisataskh.repositories.WisataSkhRepository

@Composable()
fun DetailWisataSkhScreen(id: Int, wisataSkhRepository: WisataSkhRepository = WisataSkhRepository()) {
    val wisataDetail = wisataSkhRepository.getWisataSkhById(id)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(wisataDetail.title)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(12.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(wisataDetail.image)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Text(wisataDetail.description)
        }
    }
}