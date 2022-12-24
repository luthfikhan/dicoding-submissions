package id.filab.storyapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.filab.storyapp.R
import id.filab.storyapp.dto.ListStory
import id.filab.storyapp.ui.navigation.Routes
import id.filab.storyapp.viewmodel.StoryViewModel
import id.filab.storyapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun StoriesScreen(
    userViewModel: UserViewModel,
    storyViewModel: StoryViewModel,
    navController: NavController,
) {
    var showLoader by remember { mutableStateOf(false) }
    var showMenus by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val stories = storyViewModel.storiesPager.collectAsLazyPagingItems()

    if (showLoader) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.StoryPickerScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.content_desc_add_new_story_fab)
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.StoriesMapScreen.route) }) {
                        Icon(Icons.Default.Map, "")
                    }
                    IconButton(onClick = { showMenus = !showMenus }) {
                        Icon(Icons.Default.MoreVert, "")
                    }
                    DropdownMenu(expanded = showMenus, onDismissRequest = { showMenus = false }) {
                        DropdownMenuItem(
                            onClick = {
                                coroutineScope.launch {
                                    userViewModel.deleteToken(context)
                                }
                                navController.navigate(Routes.SignupScreen.route){
                                    popUpTo(Routes.StoriesScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        ) {
                            Text(text = stringResource(R.string.menu_logout_text))
                        }
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            LazyColumn {
                items(stories) { item ->
                    item?.let {StoryCard(item, navController)}
                }
                stories.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> item {
                            Box(modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                        loadState.append is LoadState.Loading -> item {
                            Box(modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                        loadState.refresh is LoadState.Error -> item {
                            ErrorItem(
                                modifier = Modifier.fillParentMaxSize(),
                                onClickRetry = { refresh() }
                            )
                        }
                        loadState.append is LoadState.Error -> item {
                            ErrorItem(
                                onClickRetry = { retry() }
                            )
                        }
                    }
                }

            }
        }

    }
}

@Composable
fun StoryCard (item: ListStory, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(12.dp))
            .background(color = Color.White)
            .clickable {
                val itemJson = Json.encodeToString(
                    ListStory.serializer(),
                    item
                )
                val itemUrl = URLEncoder.encode(itemJson, StandardCharsets.UTF_8.toString())
                navController.navigate(Routes.StoryDetailScreen.withArgs(itemUrl))
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.photoUrl)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .height(460.dp)
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            text = "by ${
                if (item.name?.isNotEmpty() == true) item.name
                else stringResource(R.string.guest_text)
            }"
        )
    }
}

@Preview
@Composable
fun ErrorItem(
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Maaf terjadi kesalahan",
            maxLines = 1,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.padding(start = 8.dp))
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "Coba Lagi")
        }
    }
}
