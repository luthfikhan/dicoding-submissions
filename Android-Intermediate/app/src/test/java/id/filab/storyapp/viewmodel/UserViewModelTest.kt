package id.filab.storyapp.viewmodel

import id.filab.storyapp.dto.LoginPayload
import id.filab.storyapp.dto.RegisterPayload
import id.filab.storyapp.fake.ApiServiceFake
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserViewModelTest {
    private lateinit var userViewModel: UserViewModel

    @Before
    fun setUp() {
        userViewModel = UserViewModel(
            apiService = ApiServiceFake()
        )
    }

    @Test
    fun `Login test success`() = runBlocking {
        val token = userViewModel.submitLogin(
            LoginPayload(
                email = "luthfi@filab.id",
                password = "success"
            ),
        )

        assertTrue(token.isNotEmpty())
    }

    @Test
    fun `Login test error`() = runBlocking {
        val token = userViewModel.submitLogin(
            LoginPayload(
                email = "luthfi@filab.id",
                password = "error"
            ),
        )

        assertTrue(token.isEmpty())
    }

    @Test
    fun `Submit signup success`() = runBlocking {
        val success = userViewModel.submitRegister(
            RegisterPayload(
                email = "luthfi@filab.id",
                password = "password",
                name = "success"
            ),
        )

        assertTrue(success)
    }

    @Test
    fun `Submit signup error`() = runBlocking {
        val success = userViewModel.submitRegister(
            RegisterPayload(
                email = "luthfi@filab.id",
                password = "password",
                name = "error"
            ),
        )

        assertFalse(success)
    }
}