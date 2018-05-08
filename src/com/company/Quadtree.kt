package com.company

import processing.core.PConstants.CENTER


class Quadtree(var boundary: Rectangle, val capacity: Int = 4) {
    private var points: ArrayList<Point> = ArrayList()
    private lateinit var NE: Quadtree
    private lateinit var NW: Quadtree
    private lateinit var SE: Quadtree
    private lateinit var SW: Quadtree
    private var bDivided: Boolean = false
    private val pFuncs = Quadtree_Example.self

    fun insert(p: Point): Boolean {

        if (!boundary.contains(p)) {
            return false
        }

        if (points.size < capacity) {
            points.add(p)
            return true
        } else {
            if (!bDivided) {
                subdivide()
                bDivided = true
            }
            when {
                NE.insert(p) -> return true
                NW.insert(p) -> return true
                SE.insert(p) -> return true
                SW.insert(p) -> return true
            }
            return true
        }
    }

    private fun subdivide() {
        val x = boundary.x
        val y = boundary.y
        val w = boundary.w
        val h = boundary.h
        NE = Quadtree(Rectangle(x + w / 2, y - h / 2, w / 2, h / 2), capacity)
        NW = Quadtree(Rectangle(x - w / 2, y - h / 2, w / 2, h / 2), capacity)

        SE = Quadtree(Rectangle(x + w / 2, y + h / 2, w / 2, h / 2), capacity)
        SW = Quadtree(Rectangle(x - w / 2, y + h / 2, w / 2, h / 2), capacity)
    }

    fun query(range: Rectangle, found: ArrayList<Point> = ArrayList(), count: Int = 0): ArrayList<Point> {
        var c = count
        if (!boundary.intersects(range)) {
            return found
        } else {
            for (pt in points) {
                if (range.contains(pt)) found.add(pt)
                c++
            }
            if (bDivided) {
                NE.query(range, found, c)
                NW.query(range, found, c)
                SE.query(range, found, c)
                SW.query(range, found, c)
            }
//            println("Checks: $count")
            return found
        }
    }

    fun reset() {
        points.clear()
        if (bDivided) {
            NE.reset()
            NW.reset()
            SE.reset()
            SW.reset()
        }
        bDivided = false
    }

    fun show() {
        pFuncs.stroke(255)
        pFuncs.strokeWeight(1f)
        pFuncs.noFill()
        pFuncs.rectMode(CENTER)
        pFuncs.rect(boundary.x, boundary.y, boundary.w * 2, boundary.h * 2)
        if (bDivided) {
            NE.show()
            NW.show()
            SE.show()
            SW.show()
        }
    }
}




