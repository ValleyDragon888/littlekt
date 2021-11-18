package com.lehaine.littlekt.util.datastructure

/**
 * @author Colton Daily
 * @date 11/17/2021
 */
typealias Stack<TGen> = TGenStack<TGen>

// AUTOGENERATED: DO NOT MODIFY MANUALLY!


/**
 * A LIFO (Last In First Out) structure.
 */
class TGenStack<TGen>() : Collection<TGen> {
    private val items = mutableListOf<TGen>()

    override val size: Int get() = items.size
    override fun isEmpty() = size == 0

    constructor(vararg items: TGen) : this() {
        for (item in items) push(item)
    }

    fun push(v: TGen) { items.add(v) }
    fun pop(): TGen = items.removeAt(items.size - 1)
    fun peek(): TGen? = items.lastOrNull()

    override fun contains(element: TGen): Boolean = items.contains(element)
    override fun containsAll(elements: Collection<TGen>): Boolean = items.containsAll(elements)
    override fun iterator(): Iterator<TGen> = items.iterator()

    override fun hashCode(): Int = items.hashCode()
    override fun equals(other: Any?): Boolean = (other is TGenStack<*/*TGen*/>) && items == other.items
}


// Int

/**
 * A LIFO (Last In First Out) structure.
 */
class IntStack() : Collection<Int> {
    private val items = intArrayListOf()

    override val size: Int get() = items.size
    override fun isEmpty() = size == 0

    constructor(vararg items: Int) : this() {
        for (item in items) push(item)
    }

    fun push(v: Int) { items.add(v) }
    fun pop(): Int = items.removeAt(items.size - 1)
    fun peek(): Int? = items.lastOrNull()

    override fun contains(element: Int): Boolean = items.contains(element)
    override fun containsAll(elements: Collection<Int>): Boolean = items.containsAll(elements)
    override fun iterator(): Iterator<Int> = items.iterator()

    override fun hashCode(): Int = items.hashCode()
    override fun equals(other: Any?): Boolean = (other is IntStack) && items == other.items
}


// Double

/**
 * A LIFO (Last In First Out) structure.
 */
class DoubleStack() : Collection<Double> {
    private val items = doubleArrayListOf()

    override val size: Int get() = items.size
    override fun isEmpty() = size == 0

    constructor(vararg items: Double) : this() {
        for (item in items) push(item)
    }

    fun push(v: Double) { items.add(v) }
    fun pop(): Double = items.removeAt(items.size - 1)
    fun peek(): Double? = items.lastOrNull()

    override fun contains(element: Double): Boolean = items.contains(element)
    override fun containsAll(elements: Collection<Double>): Boolean = items.containsAll(elements)
    override fun iterator(): Iterator<Double> = items.iterator()

    override fun hashCode(): Int = items.hashCode()
    override fun equals(other: Any?): Boolean = (other is DoubleStack) && items == other.items
}


// Float

/**
 * A LIFO (Last In First Out) structure.
 */
class FloatStack() : Collection<Float> {
    private val items = floatArrayListOf()

    override val size: Int get() = items.size
    override fun isEmpty() = size == 0

    constructor(vararg items: Float) : this() {
        for (item in items) push(item)
    }

    fun push(v: Float) { items.add(v) }
    fun pop(): Float = items.removeAt(items.size - 1)
    fun peek(): Float? = items.lastOrNull()

    override fun contains(element: Float): Boolean = items.contains(element)
    override fun containsAll(elements: Collection<Float>): Boolean = items.containsAll(elements)
    override fun iterator(): Iterator<Float> = items.iterator()

    override fun hashCode(): Int = items.hashCode()
    override fun equals(other: Any?): Boolean = (other is FloatStack) && items == other.items
}