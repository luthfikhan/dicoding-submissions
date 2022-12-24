package id.filab.storyapp.extensions

import android.util.Patterns

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.length >= 6
}