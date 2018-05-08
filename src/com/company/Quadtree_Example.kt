package com.company

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector

class Quadtree_Example : PApplet() {
    private lateinit var boundary: Rectangle
    private lateinit var qt: Quadtree
    private lateinit var range: Rectangle
    private lateinit var rangePoints: ArrayList<Point>
    private var allPoints: ArrayList<Point> = ArrayList()
    private var otherPoints: ArrayList<Point> = ArrayList()
    private var bShowBackground: Boolean = true
    private var bShowGrid: Boolean = false
    private var bDrawRange: Boolean = false

    override fun settings() {
        size(1800, 1000)
        boundary = Rectangle(width / 2f, height / 2f, width / 2f, height / 2f)
        qt = Quadtree(boundary, 4)
        for (i in 0 until 2000) {
//            val x = map(noise(i.toFloat() * random(100f)), 0f, 1f, 0f, width.toFloat())
//            val y = map(noise(i.toFloat() * random(100f)), 0f, 1f, 0f, height.toFloat())
            val x = random(width.toFloat())
            val y = random(height.toFloat())
            allPoints.add(Point(x, y, 6f, 2f))
        }

        range = Rectangle(250f, 250f, 22f, 22f)
        rangePoints = ArrayList()

        smooth()
    }

    override fun draw() {
        if (bShowBackground) background(51f)

        for (pt in allPoints) {
            range.x = pt.position.x
            range.y = pt.position.y
            otherPoints = qt.query(range)
            for (other in otherPoints) {
                if (pt != other) {
//                    if (pt.collide(other)) {
//                        pt.collide2(other)
//                    }
                    pt.collide3(other)
                }
            }
            pt.update()
            qt.insert(pt)
        }
        if (bShowGrid) {
            qt.show()
            bShowBackground = true
        }
        for (pt in allPoints) pt.show(false)
        if (bDrawRange) drawRange()
        qt.reset()
        println(frameRate)
    }

    fun drawRange() {
        range.x = mouseX.toFloat()
        range.y = mouseY.toFloat()

        rangePoints = qt.query(range)

        rectMode(CENTER)
        noFill()
        strokeWeight(1f)
        stroke(0f, 255f, 0f)
        rect(range.x, range.y, range.w * 2, range.h * 2)


        fill(0f, 255f, 0f)
        for (pt in rangePoints) ellipse(pt.position.x, pt.position.y, 10f, 10f)
    }

    override fun keyPressed() {
        if (bDrawRange) {
            when (keyCode) {
                UP -> range.h += 2
                DOWN -> range.h -= 2
                LEFT -> range.w -= 2
                RIGHT -> range.w += 2
            }
        }
    }

    override fun keyReleased() {
        when (key) {
            ' ' -> bShowBackground = !bShowBackground
            PConstants.ENTER -> bShowGrid = !bShowGrid
            'r' -> bDrawRange = !bDrawRange
        }
    }

    operator fun invoke(): PApplet? {
        return this
    }

    companion object {
        lateinit var self: Quadtree_Example
    }

    init {
        self = this
    }
}

fun main(args: Array<String>) {
    val processingArgs = arrayOf("mySketch")
    val mySketch = Quadtree_Example()
    PApplet.runSketch(processingArgs, mySketch)

}