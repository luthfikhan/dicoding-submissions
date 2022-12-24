package id.filab.storyapp.utils

import java.util.*
import kotlin.concurrent.schedule

class Debounce {
    companion object {
        var timer: TimerTask? = null
    }

    fun debounce(delayMillis: Long, callback: () -> Unit) {
        timer?.cancel()
        timer = Timer().schedule(delayMillis) {
            callback()
        }
    }
}