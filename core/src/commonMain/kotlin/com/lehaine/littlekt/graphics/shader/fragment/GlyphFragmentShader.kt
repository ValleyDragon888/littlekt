package com.lehaine.littlekt.graphics.shader.fragment

import com.lehaine.littlekt.graphics.shader.FragmentShaderModel
import com.lehaine.littlekt.graphics.shader.generator.type.vec.Vec2
import com.lehaine.littlekt.graphics.shader.generator.type.vec.Vec4

/**
 * @author Colton Daily
 * @date 11/28/2021
 */
class GlyphFragmentShader : FragmentShaderModel() {
    private val color by uniform(::Vec4)
    private val coord by varying(::Vec2)

    init {
        If(coord.x * coord.x - coord.y gt 0f) {
            discard()
        }

        var frontFaces by float(1f / 255f)
        If(gl_frontFacing) {
            frontFaces = (16f / 255f).lit
        }
        gl_FragColor = color * frontFaces
    }
}