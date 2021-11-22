package com.lehaine.littlekt

/**
 * @author Colton Daily
 * @date 11/6/2021
 */
fun main(args: Array<String>) {
    LittleKtAppBuilder(
        configBuilder = { ApplicationConfiguration("JVM - Display Test", 960, 540, true) },
        gameBuilder = { DisplayTest(it) })
        .start()
}