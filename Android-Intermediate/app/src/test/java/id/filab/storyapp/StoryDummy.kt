package id.filab.storyapp

import id.filab.storyapp.dto.ListStory
import id.filab.storyapp.dto.StoryResponse

object StoryDummy {
    fun generateStories() :StoryResponse {
        val stories = arrayListOf<ListStory>()

        for (i in 1..10) {
            stories.add(
                ListStory(
                    id = i.toString(),
                    name = "story_${i}",
                    description = "story_desc_${i}",
                    lat = 10.0,
                    lon = 1.0,
                    photoUrl = "photo_url_$i"
                )
            )
        }

        return StoryResponse(
            error = false,
            message = "success",
            listStory = stories
        )
    }
}