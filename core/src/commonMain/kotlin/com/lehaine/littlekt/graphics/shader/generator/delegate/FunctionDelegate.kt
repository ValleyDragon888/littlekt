package com.lehaine.littlekt.graphics.shader.generator.delegate

import com.lehaine.littlekt.graphics.shader.generator.GlslGenerator
import com.lehaine.littlekt.graphics.shader.generator.Instruction
import com.lehaine.littlekt.graphics.shader.generator.InstructionType
import com.lehaine.littlekt.graphics.shader.generator.type.Func
import com.lehaine.littlekt.graphics.shader.generator.type.GenType
import com.lehaine.littlekt.graphics.shader.generator.type.Variable
import kotlin.reflect.KProperty

/**
 * @author Colton Daily
 * @date 11/29/2021
 */

class FunctionDelegate<RT : GenType, F : Func<RT>>(
    private val funcType: ((GlslGenerator) -> F),
    private val body: () -> RT
) {
    private lateinit var func: F
    private lateinit var call: () -> RT

    operator fun provideDelegate(
        thisRef: GlslGenerator,
        property: KProperty<*>
    ): FunctionDelegate<RT, F> {
        func = funcType(thisRef)
        func.value = property.name
        thisRef.functionInstructions.add(
            Instruction(
                InstructionType.FUNC_DEFINED,
                "${func.typeName} ${func.value}()"
            )
        )
        thisRef.addAsFunctionInstruction = true
        val result = body()
        thisRef.addAsFunctionInstruction = false
        thisRef.functionInstructions.add(
            Instruction(
                InstructionType.END_FUNC
            )
        )
        call = {
            thisRef.addInstruction(Instruction(InstructionType.INVOKE_FUNC, "${func.value}()"))
            result
        }

        return this
    }

    operator fun getValue(thisRef: GlslGenerator, property: KProperty<*>): () -> RT {
        return call
    }
}

class FunctionDelegate1<RT : GenType, F : Func<RT>, T : Variable>(
    private val funcType: ((GlslGenerator) -> F),
    private val param1: ((GlslGenerator) -> T),
    private val body: (T) -> RT
) {
    private lateinit var func: F
    private lateinit var v: T
    private lateinit var call: (T) -> FunctionReturnDelegate<RT>

    operator fun provideDelegate(
        thisRef: GlslGenerator,
        property: KProperty<*>
    ): FunctionDelegate1<RT, F, T> {
        func = funcType(thisRef)
        func.value = property.name
        v = param1(thisRef)
        v.value = "p1"
        thisRef.functionInstructions.add(
            Instruction(
                InstructionType.FUNC_DEFINED,
                "${func.typeName} ${func.value}(${v.typeName} ${v.value})"
            )
        )
        thisRef.addAsFunctionInstruction = true
        val result = body(v)
        thisRef.functionInstructions.add(Instruction(InstructionType.DEFINE, "return ${result.value}"))

        thisRef.addAsFunctionInstruction = false
        thisRef.functionInstructions.add(
            Instruction(
                InstructionType.END_FUNC
            )
        )
        call = {
            FunctionReturnDelegate(result, "${func.value}(${it.value})")
        }

        return this
    }

    operator fun getValue(thisRef: GlslGenerator, property: KProperty<*>): (T) -> FunctionReturnDelegate<RT> {
        return call
    }
}

class FunctionDelegate2<RT : GenType, F : Func<RT>, A : Variable, B : Variable>(
    private val funcType: ((GlslGenerator) -> F),
    private val param1: ((GlslGenerator) -> A),
    private val param2: ((GlslGenerator) -> B),
    private val body: (A, B) -> RT
) {
    private lateinit var func: F
    private lateinit var p1: A
    private lateinit var p2: B
    private lateinit var call: (A, B) -> RT

    operator fun provideDelegate(
        thisRef: GlslGenerator,
        property: KProperty<*>
    ): FunctionDelegate2<RT, F, A, B> {
        func = funcType(thisRef)
        func.value = property.name
        p1 = param1(thisRef)
        p1.value = "p1"
        p2 = param2(thisRef)
        p2.value = "p2"

        thisRef.functionInstructions.add(
            Instruction(
                InstructionType.FUNC_DEFINED,
                "${func.typeName} ${func.value}(${p1.typeName} ${p1.value}, ${p2.typeName} ${p2.value})"
            )
        )
        thisRef.addAsFunctionInstruction = true
        val result = body(p1, p2)
        thisRef.addAsFunctionInstruction = false
        thisRef.functionInstructions.add(
            Instruction(
                InstructionType.END_FUNC
            )
        )

        call = { a, b ->
            thisRef.addInstruction(Instruction(InstructionType.INVOKE_FUNC, "${func.value}(${a.value}, ${b.value})"))
            result
        }

        return this
    }

    operator fun getValue(thisRef: GlslGenerator, property: KProperty<*>): (A, B) -> RT {
        return call
    }
}