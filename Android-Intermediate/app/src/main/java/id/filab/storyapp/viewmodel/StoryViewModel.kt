package id.filab.storyapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import id.filab.storyapp.data.StoryDataSource
import id.filab.storyapp.dto.ListStory
import id.filab.storyapp.services.ApiService
import id.filab.storyapp.services.getApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class StoryViewModel(
    private val userViewModel: UserViewModel,
    private val apiService: ApiService = getApiService()
) : ViewModel() {
    val storiesPager = Pager(PagingConfig(pageSize = 20)) {
        StoryDataSource(
            token = "Bearer ${userViewModel.token}"
        )
    }.flow
    var storiesWithLocation by mutableStateOf<List<ListStory>>(emptyList())

    suspend fun getStoriesWithLocation() {
        try {
            val res = apiService.getAllStories(
                token = "Bearer ${userViewModel.token}",
                location = 1
            )
            if (res.listStory.isNotEmpty()) {
                storiesWithLocation = res.listStory
            }
        } catch (e: Exception) {
            storiesWithLocation = emptyList()
        }
    }

    suspend fun uploadStory(
        imageFile: File,
        desc: String,
    ): Boolean {
        try {
            val res = apiService.uploadStory(
                "Bearer ${userViewModel.token}",
                MultipartBody.Part.createFormData(
                    name = "photo",
                    filename = imageFile.name,
                    imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                ),
                description = desc.toRequestBody("text/plain".toMediaType())
            )
            if (!res.error) {
                return true
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }
}