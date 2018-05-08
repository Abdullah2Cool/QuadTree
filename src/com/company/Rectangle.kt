package com.company

class Rectangle(var x: Float, var y: Float, var w: Float, var h: Float) {
    fun contains(p: Point): Boolean {
        return (p.position.x >= x - w &&
                p.position.x <= x + w &&
                p.position.y >= y - h &&
                p.position.y <= y + h)
    }

    fun intersects(range: Rectangle): Boolean {
        return !(range.x - range.w > x + w ||
                range.x + range.w < x - w ||
                range.y - range.h > y + h ||
                range.y + range.h < y - h)
    }
}