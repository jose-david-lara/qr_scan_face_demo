package com.example.qr_scan_face_demo

class TestApiImpl : TestApi {
    override fun sayHello(name: String): String {
        return "Hola $name, soy Kotlin desde Pigeon 👋"
    }
}