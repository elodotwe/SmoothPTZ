package com.jacobarau.smoothptz

import javax.inject.Inject

class Hello @Inject constructor() {
    fun getHi(): String {
        return "Hi!!!"
    }
}