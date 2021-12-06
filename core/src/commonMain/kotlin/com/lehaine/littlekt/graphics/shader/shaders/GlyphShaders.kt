package com.lehaine.littlekt.graphics.shader.shaders

import com.lehaine.littlekt.graphics.shader.FragmentShaderModel
import com.lehaine.littlekt.graphics.shader.ShaderParameter
import com.lehaine.littlekt.graphics.shader.VertexShaderModel
import com.lehaine.littlekt.graphics.shader.generator.type.mat.Mat4
import com.lehaine.littlekt.graphics.shader.generator.type.vec.Vec2
import com.lehaine.littlekt.graphics.shader.generator.type.vec.Vec4


/**
 * @author Colton Daily
 * @date 11/29/2021
 */
class GlyphVertexShader : VertexShaderModel() {
    val uProjTrans get() = parameters[0] as ShaderParameter.UniformMat4

    private val u_projTrans by uniform(::Mat4)
    private val a_position by attribute(::Vec2)
    private val a_color by attribute(::Vec4)
    private val a_texCoord0 by attribute(::Vec2)
    private var v_color by varying(::Vec4)
    private var v_texCoords by varying(::Vec2)

    init {
        v_texCoords = a_texCoord0
        v_color = a_color
        gl_Position = u_projTrans * vec4Lit(a_position, 0f, 1f)
    }
}

/**
 * @author Colton Daily
 * @date 11/28/2021
 */
class GlyphFragmentShader : FragmentShaderModel() {
    private val v_color by varying(::Vec4)
    private val v_texCoords by varying(::Vec2)

    init {
        If(v_texCoords.x * v_texCoords.x - v_texCoords.y gt 0f) {
            discard()
        }

        gl_FragColor = v_color
    }
}


/**
 * @author Colton Daily
 * @date 11/28/2021
 */
class GlyphOffscreenFragmentShader : FragmentShaderModel() {
    val uColor get() = parameters[0] as ShaderParameter.UniformVec4
    private val u_color by uniform(::Vec4)
    private val v_color by varying(::Vec4)
    private val v_texCoords by varying(::Vec2)

    init {
        If(v_texCoords.x * v_texCoords.x - v_texCoords.y gt 0f) {
            discard()
        }

        gl_FragColor =  u_color * ternary(
            gl_FrontFacing,
            left = "16.0 / 255.0".float,
            right = "1.0 / 255.0".float
        )
    }
}

