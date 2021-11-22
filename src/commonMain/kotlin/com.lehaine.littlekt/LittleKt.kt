package com.lehaine.littlekt

import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.graphics.TextureData
import com.lehaine.littlekt.graphics.shader.ShaderProgram
import com.lehaine.littlekt.graphics.shader.fragment.FragmentShader
import com.lehaine.littlekt.graphics.shader.fragment.TexturedFragmentShader
import com.lehaine.littlekt.graphics.shader.vertex.TexturedQuadShader
import com.lehaine.littlekt.graphics.shader.vertex.VertexShader

/**
 * A [LittleKt] is the base of an [Application] when it is created, resumed, rendering, paused, or destroyed.
 * @author Colton Daily
 * @date 9/29/2021
 */
open class LittleKt(val application: Application) : Application by application {

    open fun create() {}

    open fun render(dt: Float) {}

    open fun resize(width: Int, height: Int) {}

    open fun resume() {}

    open fun pause() {}

    open fun dispose() {}
}

/**
 * Creates and loads a new [Texture].
 * @param filename the name of the image file to load
 */
fun <T : Application> T.readTexture(filename: String) =
    Texture(fileHandler.readTextureData(filename).get()).also { it.load(this) }

/**
 * Creates and loads a new [TextureData].
 * @param filename the name of the image file to load
 */
fun <T : Application> T.readTextureData(filename: String): TextureData = fileHandler.readTextureData(filename).get()

/**
 * Creates a new [ShaderProgram] for the specified shaders.
 * @param vertexShader the vertex shader to use. Defaults to [TexturedQuadShader].
 * @param fragmentShader the fragment shader to use. Defaults to [TexturedFragmentShader].
 */
fun <T : Application> T.createShader(
    vertexShader: VertexShader = TexturedQuadShader(),
    fragmentShader: FragmentShader = TexturedFragmentShader()
) =
    ShaderProgram(gl, vertexShader, fragmentShader)