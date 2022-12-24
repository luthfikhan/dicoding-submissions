package id.filab.storyapp.viewmodel

import id.filab.storyapp.StoryDummy
import id.filab.storyapp.fake.ApiServiceFake
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.File

class StoryViewModelTest {
    private lateinit var storyViewModel: StoryViewModel
    private val userViewModel = UserViewModel()

    @Before
    fun setUp() {
        storyViewModel = StoryViewModel(
            userViewModel = userViewModel,
            apiService = ApiServiceFake()
        )
    }

    @Test
    fun `Get stories with location`() = runBlocking {
        val mockResult = StoryDummy.generateStories()
        storyViewModel.getStoriesWithLocation()
        val actualResult = storyViewModel.storiesWithLocation

        assertNotNull(actualResult)
        assertEquals(actualResult, mockResult.listStory)
        assertTrue(actualResult[0].lat is Double && actualResult[0].lat != null)
    }

    @Test
    fun `Upload story test`() = runBlocking {
        val success = storyViewModel.uploadStory(File("/"), "desc")

        assertTrue(success)
    }
}