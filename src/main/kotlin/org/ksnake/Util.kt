package org.ksnake

fun getResource(filename: String): String {
    return App::class.java.getResource(filename).toString()
}
