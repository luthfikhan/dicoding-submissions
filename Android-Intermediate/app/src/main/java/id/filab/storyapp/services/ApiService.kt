package id.filab.storyapp.services

import id.filab.storyapp.BuildConfig
import id.filab.storyapp.dto.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @POST("register")
    suspend fun register(@Body body: RegisterPayload): RegisterResponse

    @POST("login")
    suspend fun login(@Body body: LoginPayload): LoginResponse

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int? = 0
    ): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): UploadStoryResponse
}

fun getApiService(): ApiService {
    val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder()
        .also {
            if(BuildConfig.DEBUG) {
                it.addInterceptor(loggingInterceptor)
            }
        }
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://story-api.dicoding.dev/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    return retrofit.create(ApiService::class.java)
}