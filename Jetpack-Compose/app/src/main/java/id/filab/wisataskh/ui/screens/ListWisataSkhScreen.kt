package id.filab.wisataskh.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.filab.wisataskh.R
import id.filab.wisataskh.model.WisataSkhModel
import id.filab.wisataskh.repositories.WisataSkhRepository
import id.filab.wisataskh.ui.navigation.Routes

@Composable()
fun ListWisataSkhScreen(listWisataSkh: List<WisataSkhModel> = WisataSkhRepository.wisataSkhModels, navController: NavController) {

    var searchText by remember {
        mutableStateOf("")
    }
    var listWisataSkhToShow by remember {
        mutableStateOf(listWisataSkh)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.ProfileScreen.route) }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "about_page"
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { value ->
                        searchText = value
                        listWisataSkhToShow = listWisataSkh.filter { item ->
                            item.title.contains(value, true) || item.description.contains(value, true)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    label = {
                         Text("Cari tempat wisata!")
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                )
            }
            if (listWisataSkhToShow.isNotEmpty()) {
                items(listWisataSkhToShow) { item ->
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .clip(shape = RoundedCornerShape(12.dp))
                            .clickable {
                                navController.navigate(Routes.DetailWisataSkhScreen.withArgs("${item.id}"))
                            }
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(item.image)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(460.dp)
                        )
                        Text(
                            text = item.title,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Gray.copy(0.8f))
                                .align(Alignment.BottomStart)
                                .padding(8.dp)
                        )
                    }
                }
            } else {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Tempat wisata tidak ditemukan",
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                    }
                }
            }
        }
    }
}
