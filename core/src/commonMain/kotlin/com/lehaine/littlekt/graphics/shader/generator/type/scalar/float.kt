package com.lehaine.littlekt.graphics.shader.generator.type.scalar

import com.lehaine.littlekt.graphics.shader.generator.GlslGenerator
import com.lehaine.littlekt.graphics.shader.generator.delegate.ComponentDelegate
import com.lehaine.littlekt.graphics.shader.generator.str
import com.lehaine.littlekt.graphics.shader.generator.type.BoolResult
import com.lehaine.littlekt.graphics.shader.generator.type.GenType

/**
 * @author Colton Daily
 * @date 11/25/2021
 */
class GLFloat(override val builder: GlslGenerator) : GenType {

    override val typeName: String = "float"
    override var value: String? = null

    constructor(builder: GlslGenerator, value: String) : this(builder) {
        this.value = value
    }

    operator fun times(a: Float) = GLFloat(builder, "(${this.value} * ${a.str()})")
    operator fun div(a: Float) = GLFloat(builder, "(${this.value} / ${a.str()})")
    operator fun plus(a: Float) = GLFloat(builder, "(${this.value} + ${a.str()})")
    operator fun minus(a: Float) = GLFloat(builder, "(${this.value} - ${a.str()})")

    operator fun times(a: GLFloat) = GLFloat(builder, "(${this.value} * ${a.value})")
    operator fun div(a: GLFloat) = GLFloat(builder, "(${this.value} / ${a.value})")
    operator fun plus(a: GLFloat) = GLFloat(builder, "(${this.value} + ${a.value})")
    operator fun minus(a: GLFloat) = GLFloat(builder, "(${this.value} - ${a.value})")

    operator fun unaryMinus() = GLFloat(builder, "-(${this.value})")

    infix fun eq(a: GLFloat) = BoolResult("(${this.value} == ${a.value})")
    infix fun gte(a: GLFloat) = BoolResult("(${this.value} >= ${a.value})")
    infix fun gt(a: GLFloat) = BoolResult("(${this.value} > ${a.value})")
    infix fun lte(a: GLFloat) = BoolResult("(${this.value} <= ${a.value})")
    infix fun lt(a: GLFloat) = BoolResult("(${this.value} < ${a.value})")

    infix fun lt(a: Float) = BoolResult("(${this.value} < ${a.str()})")
    infix fun gt(a: Float) = BoolResult("(${this.value} > ${a.str()})")
}

fun floatComponent() = ComponentDelegate(::GLFloat)