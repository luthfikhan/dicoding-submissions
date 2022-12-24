package id.filab.storyapp.fake

import id.filab.storyapp.StoryDummy
import id.filab.storyapp.dto.*
import id.filab.storyapp.services.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ApiServiceFake: ApiService {
    override suspend fun register(body: RegisterPayload): RegisterResponse {
        return RegisterResponse(
            error = body.name == "error",
            message = body.name
        )
    }

    override suspend fun login(body: LoginPayload): LoginResponse {
        return LoginResponse(
            error = body.password == "error",
            message = body.email,
            loginResult = LoginResult(
                token = "token_${body.email}",
                userId = body.email,
                name = body.email
            )
        )
    }

    override suspend fun getAllStories(
        token: String,
        page: Int?,
        size: Int?,
        location: Int?
    ): StoryResponse {
        return StoryDummy.generateStories()
    }

    override suspend fun uploadStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ): UploadStoryResponse {
        return UploadStoryResponse(
            error = false,
            message = "success"
        )
    }

}