package com.lehaine.littlekt.samples

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.ContextListener
import com.lehaine.littlekt.file.vfs.readBitmapFont
import com.lehaine.littlekt.file.vfs.readTexture
import com.lehaine.littlekt.graph.node.canvasLayer
import com.lehaine.littlekt.graph.node.component.HAlign
import com.lehaine.littlekt.graph.node.frameBuffer
import com.lehaine.littlekt.graph.node.viewport
import com.lehaine.littlekt.graph.node.node2d.camera2d
import com.lehaine.littlekt.graph.node.node2d.node2d
import com.lehaine.littlekt.graph.node.ui.TextureRect
import com.lehaine.littlekt.graph.node.ui.centerContainer
import com.lehaine.littlekt.graph.node.ui.label
import com.lehaine.littlekt.graph.node.ui.textureRect
import com.lehaine.littlekt.graph.sceneGraph
import com.lehaine.littlekt.graphics.gl.ClearBufferMask
import com.lehaine.littlekt.graphics.slice
import com.lehaine.littlekt.input.Key
import com.lehaine.littlekt.math.geom.Angle
import com.lehaine.littlekt.math.geom.degrees
import com.lehaine.littlekt.math.geom.radians
import com.lehaine.littlekt.util.viewport.ExtendViewport

/**
 * @author Colton Daily
 * @date 2/24/2022
 */
class CanvasCameraTest(context: Context) : ContextListener(context) {

    override suspend fun Context.start() {
        val pixelFont = resourcesVfs["m5x7_16.fnt"].readBitmapFont()
        val icon = resourcesVfs["icon_16x16.png"].readTexture()
        val graph = sceneGraph(context, ExtendViewport(240, 135)) {
            viewport {
                strategy = ExtendViewport(960, 540)

                canvasLayer {
                    val fbo = frameBuffer {
                        width = 240
                        height = 135

                        node2d {
                            rotation = 45.degrees
                            onReady += {
                                println("$name: $viewport")
                            }
                            onUpdate += {
                                if (input.isKeyPressed(Key.D)) {
                                    globalX += 1f
                                } else if (input.isKeyPressed(Key.A)) {
                                    globalX -= 1f
                                }

                                if (input.isKeyPressed(Key.S)) {
                                    globalY += 1f
                                } else if (input.isKeyPressed(Key.W)) {
                                    globalY -= 1f
                                }
                            }
                            onRender += { batch, camera ->
                                batch.draw(icon, globalX, globalY, rotation = globalRotation)
                            }
                            camera2d {
                                x = 50f
                                active = true
                            }
                        }

                        var rotation = Angle.ZERO
                        node2d {
                            x = 100f
                            y = 20f
                            onRender += { batch, camera ->
                                rotation += 0.01.radians
                                batch.draw(icon, globalX, globalY, scaleX = 2f, scaleY = 2f, rotation = rotation)
                            }
                        }
                    }

                    textureRect {
                        anchorRight = 1f
                        anchorBottom = 1f
                        stretchMode = TextureRect.StretchMode.KEEP_ASPECT_COVERED
                        flipY = true
                        onReady += {
                            slice = fbo.fboTexture.slice()
                            fbo.onFboChanged.connect(this) {
                                slice = it.slice()
                            }
                        }
                    }
                }

                canvasLayer {
                    node2d {
                        x = 50f
                        onReady += {
                            println("$name: $viewport")
                        }
                        onUpdate += {
                            if (input.isKeyPressed(Key.D)) {
                                globalX += 1f
                            } else if (input.isKeyPressed(Key.A)) {
                                globalX -= 1f
                            }

                            if (input.isKeyPressed(Key.S)) {
                                globalY += 1f
                            } else if (input.isKeyPressed(Key.W)) {
                                globalY -= 1f
                            }
                        }
                        onRender += { batch, camera ->
                            batch.draw(icon, globalX, globalY)
                        }
                        camera2d {
                            x = 50f
                            active = true
                        }
                    }

                    var rotation = Angle.ZERO
                    node2d {
                        x = 250f
                        y = 20f
                        onRender += { batch, camera ->
                            rotation += 0.01.radians
                            batch.draw(icon, globalX, globalY, scaleX = 2f, scaleY = 2f, rotation = rotation)
                        }
                    }
                }
            }

            centerContainer {
                anchorRight = 1f
                anchorBottom = 1f
                label {
                    text = "Should be centered label"
                    horizontalAlign = HAlign.CENTER
                    font = pixelFont

                    onReady += {
                        println("$name: ${viewport!!::class.simpleName} - $viewport")
                    }
                }
            }

        }.also {
            it.initialize()
            println(it.sceneViewport.treeString())
        }

        onResize { width, height ->
            graph.resize(width, height, true)
        }
        onRender { dt ->
            gl.clear(ClearBufferMask.COLOR_BUFFER_BIT)

            graph.update(dt)
            graph.render()

            if (input.isKeyJustPressed(Key.P)) {
                logger.info { stats }
            }

            if (input.isKeyJustPressed(Key.ESCAPE)) {
                close()
            }
        }
    }
}