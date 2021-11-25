package com.lehaine.littlekt

/**
 * @author Colton Daily
 * @date 11/17/2021
 */
class LittleKtContext(
    val applicationConfiguration: ApplicationConfiguration
) {

    fun start(gameBuilder: (app: Application) -> LittleKt) {
        val app = PlatformContext(applicationConfiguration)
        app.start(gameBuilder)
    }
}

expect class LittleKtProps

expect fun createLittleKtApp(action: LittleKtProps.() -> Unit): LittleKtContext