package training.cg.com.kotlin.extension

import android.content.SharedPreferences

fun SharedPreferences.put(func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.apply()
}

