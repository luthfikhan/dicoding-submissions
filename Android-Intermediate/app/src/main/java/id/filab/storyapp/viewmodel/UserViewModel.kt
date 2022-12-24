package id.filab.storyapp.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import id.filab.storyapp.dto.LoginPayload
import id.filab.storyapp.dto.RegisterPayload
import id.filab.storyapp.services.ApiService
import id.filab.storyapp.services.getApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserViewModel(private val apiService: ApiService = getApiService()): ViewModel() {
    var token by mutableStateOf("")

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
        val TOKEN_KEY = stringPreferencesKey("token")
    }

    fun getToken(context: Context): Flow<String?> {
        val t = context.dataStore.data.map {
            it[TOKEN_KEY]
        }
        return t
    }
    suspend fun saveToken(t: String, context: Context) {
        token = t
        context.dataStore.edit {
            it[TOKEN_KEY] = t
        }
    }
    suspend fun deleteToken(context: Context) {
        token = ""
        context.dataStore.edit {
            it.remove(TOKEN_KEY)
        }
    }

    suspend fun submitRegister (registerPayload: RegisterPayload) :Boolean {
        try {
            val res = apiService.register(registerPayload)
            if (!res.error) {
                return true
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }

    suspend fun submitLogin (loginPayload: LoginPayload): String  {
        try {
            val res = apiService.login(loginPayload)
            if (!res.error && res.loginResult?.token != null) {
                return res.loginResult.token
            }
        } catch (e: Exception) {
            return ""
        }
        return ""
    }
}