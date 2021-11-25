package com.lehaine.littlekt

import com.lehaine.littlekt.graphics.GL
import com.lehaine.littlekt.graphics.GLVersion
import org.lwjgl.glfw.GLFW

/**
 * @author Colton Daily
 * @date 11/6/2021
 */
class LwjglGraphics(engineStats: EngineStats) : Graphics {
    override val gl: GL = LwjglGL(engineStats)

    internal var _glVersion: GLVersion = GLVersion.GL_30
        set(value) {
            (gl as LwjglGL)._glVersion = value
            field = value
        }
    internal var _width: Int = 0
    internal var _height: Int = 0
    internal var _backBufferWidth: Int = 0
    internal var _backBufferHeight: Int = 0

    override val width: Int
        get() = _backBufferWidth
    override val height: Int
        get() = _backBufferHeight
    override val backBufferWidth: Int
        get() = _backBufferWidth
    override val backBufferHeight: Int
        get() = _backBufferHeight

    override fun getType(): Graphics.GraphicsType = Graphics.GraphicsType.LWJGL3

    override fun getGLVersion(): GLVersion = _glVersion

    override fun supportsExtension(extension: String): Boolean {
        return GLFW.glfwExtensionSupported(extension)
    }
}