package id.filab.storyapp.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import id.filab.storyapp.viewmodel.StoryViewModel

@Composable
fun StoriesMapScreen(storyViewModel: StoryViewModel) {
    val boundsBuilder = LatLngBounds.Builder()
    val indonesia = LatLng(-2.6052694, 119.2362267)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(indonesia, 3f)
    }
    val stories = storyViewModel.storiesWithLocation

    LaunchedEffect(Any()) {
        storyViewModel.getStoriesWithLocation()
    }

    LaunchedEffect(stories) {
        if (stories.isNotEmpty()) {
            stories.map {
                if (it.lat != null && it.lon != null) {
                    boundsBuilder.include(LatLng(it.lat!!, it.lon!!))
                }
            }

            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 20),
                1000
            )
        }
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
    ) {
        stories.map {
            if (it.lat !== null && it.lon !== null) {
                Marker(
                    state = MarkerState(LatLng(it.lat!!, it.lon!!)),
                    title = "Story by ${it.name}"
                )
            }
        }
    }
}