package id.filab.storyapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.filab.storyapp.dto.ListStory
import id.filab.storyapp.services.getApiService

class StoryDataSource(private val token: String): PagingSource<Int, ListStory>() {
    override fun getRefreshKey(state: PagingState<Int, ListStory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStory> {
        return try {
            val page = params.key ?: 1
            val response = getApiService().getAllStories(token, page, params.loadSize)
            LoadResult.Page(
                data = response.listStory,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.listStory.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}