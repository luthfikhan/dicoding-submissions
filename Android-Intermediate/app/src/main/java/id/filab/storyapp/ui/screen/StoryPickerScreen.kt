package id.filab.storyapp.ui.screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import id.filab.storyapp.R
import id.filab.storyapp.ui.components.Field
import id.filab.storyapp.utils.ComposeFileProvider
import id.filab.storyapp.utils.reduceFileImage
import id.filab.storyapp.utils.uriToFile
import id.filab.storyapp.viewmodel.StoryViewModel
import id.filab.storyapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun StoryPickerScreen(
    storyViewModel: StoryViewModel,
    goBack: () -> Unit
) {
    var showLoader by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var image by remember {
        mutableStateOf<Uri?>(null)
    }
    var showDialogPicker by remember {
        mutableStateOf(false)
    }
    var hasImage by remember {
        mutableStateOf(false)
    }
    var desc by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    val launcherGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let { uri: Uri ->
                hasImage = true
                image = uri
            }
        }
    )
    val launcherCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {
            hasImage = it
        }
    )

    fun uploadStory() {
        if (!hasImage || image == null || desc.isEmpty()) {
            Toast.makeText(
                context,
                context.getString(R.string.add_story_invalid_field_text),
                Toast.LENGTH_LONG
            ).show()
        } else {
            showLoader = true
            coroutineScope.launch {
                val success = storyViewModel.uploadStory(
                    imageFile = reduceFileImage(uriToFile(image!!, context)),
                    desc = desc,
                )
                if (success) {
                    goBack()
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.something_went_wrong),
                        Toast.LENGTH_LONG
                    ).show()
                }
                showLoader = false
            }
        }
    }

    if (showDialogPicker) {
        Dialog(
            properties = DialogProperties(dismissOnClickOutside = true),
            onDismissRequest = { showDialogPicker = false },
        ) {
            Column(
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(vertical = 12.dp, horizontal = 18.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            showDialogPicker = false
                            launcherGallery.launch("image/*")
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Image,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.padding(start = 8.dp))
                    Text(text = stringResource(R.string.story_picker_gallery_text))
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            showDialogPicker = false
                            val uri = ComposeFileProvider.getImageUri(context)
                            image = uri
                            launcherCamera.launch(uri)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.CameraEnhance,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.padding(start = 8.dp))
                    Text(text = stringResource(R.string.story_picker_camera_text))
                }
            }
        }
    }

    if (showLoader) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.story_picker_title_page))
                }
            )
        }
    ) {
        BoxWithConstraints {
            Box(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .heightIn(min = LocalConfiguration.current.screenHeightDp.dp)
                        .height(this@BoxWithConstraints.maxHeight)

                ) {
                    Spacer(modifier = Modifier.padding(top = 30.dp))
                    Box(
                        modifier = Modifier
                            .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
                            .width(300.dp)
                            .height(300.dp)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colors.primary,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .align(Alignment.CenterHorizontally),
                        contentAlignment = Alignment.Center
                    ) {
                        if (image == null || !hasImage) {
                            IconButton(
                                onClick = { showDialogPicker = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CameraAlt,
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.primary,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        } else {
                            Image(
                                painter = rememberAsyncImagePainter(image),
                                contentDescription = null
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    Field(
                        onChange = { desc = it },
                        label = stringResource(R.string.story_picker_label_desc)
                    )
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { uploadStory() }
                    ) {
                        Text(
                            modifier = Modifier.padding(6.dp),
                            text = stringResource(R.string.story_picker_button_text),
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
}

@Preview
@Composable
fun StoryPickerPreview() {
    StoryPickerScreen(
        storyViewModel = StoryViewModel(UserViewModel()),
        goBack = {}
    )
}