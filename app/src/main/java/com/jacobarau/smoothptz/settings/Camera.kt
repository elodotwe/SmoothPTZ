package com.jacobarau.smoothptz.settings

import java.util.*

data class Camera(var name: String, var streamURL: String, var uuid: String = UUID.randomUUID().toString())
