package com.company

import processing.core.PApplet
import processing.core.PApplet.*
import processing.core.PVector

class Point(x: Float, y: Float, var maxSize: Float = 7f, var minSize: Float = 2f) {

    var myColor: Int = Quadtree_Example.self.color(Quadtree_Example.self.random(255f), Quadtree_Example.self.random(255f), Quadtree_Example.self.random(255f))
    var radius: Float = (Math.random() * (maxSize - minSize) + minSize).toFloat()
    var acceleration: PVector = PVector(0f, 0f)
    var velocity: PVector = PVector((Math.random() * 5 - 2).toFloat(), (Math.random() * 5 - 2).toFloat())
    var Trail: ArrayList<PVector> = ArrayList()
    val TrailSize: Int = 15
    var position: PVector = PVector(x, y)

    fun show(bShowTrails: Boolean = false) {
        Quadtree_Example.self.apply {
            if (bShowTrails) {
                for (i in 0 until Trail.size) {
                    stroke(lerpColor(myColor - 500, myColor, i / TrailSize.toFloat()), 50f)
                    ellipse(Trail[i].x, Trail[i].y, radius, radius)
                }
            }
//            noFill()
//            strokeWeight(radius * 1.3f)
//            stroke(myColor, 50f)
            noStroke()
            fill(255)
            ellipse(position.x, position.y, radius, radius)
            fill(myColor, 100f)
            ellipse(position.x, position.y, radius * 2, radius * 2)
        }

        velocity.limit(6f)
    }

    fun update() {
        Trail.add(position)

        if (Trail.size > TrailSize) Trail.removeAt(0)

        velocity.add(acceleration)
        position.add(velocity)

        acceleration.mult(0f)

        Quadtree_Example.self.apply {
            when {
                position.x > width -> position.x = 0f
                position.x < 0 -> position.x = width.toFloat()
                position.y > height -> position.y = 0f
                position.y < 0 -> position.y = height.toFloat()
            }
//            when {
//                (x > width || x < 0) -> velocity.x *= -1
//                (y > height || y < 0) -> velocity.y *= -1
//            }
        }

    }

    fun collide(_point: Point): Boolean {
        if (_point.position.x - _point.radius > this.position.x + this.radius ||
                _point.position.x + _point.radius < this.position.x - this.radius) {
            return false
        } else if (_point.position.y - _point.radius > this.position.y + this.radius ||
                _point.position.y + _point.radius <
                this.position.y - this.radius) {
            return false
        }

        val _thisp = PVector(this.position.x, this.position.y)
        val vChord = position.sub(_point.position)
        vChord.normalize()
        _thisp.add(vChord.mult(-1f))

        this.position.x = _thisp.x
        this.position.y = _thisp.y

        return true
    }

    fun collide2(other: Point) {
        this.velocity.x = (this.velocity.x * (this.radius - other.radius) + (2 * other.radius * other.velocity.x)) / (this.radius + other.radius)
        this.velocity.y = (this.velocity.y * (this.radius - other.radius) + (2 * other.radius * other.velocity.y)) / (this.radius + other.radius)
        other.velocity.x = (other.velocity.x * (other.radius - this.radius) + (2 * this.radius * this.velocity.x)) / (this.radius + other.radius)
        other.velocity.y = (other.velocity.y * (other.radius - this.radius) + (2 * this.radius * this.velocity.y)) / (this.radius + other.radius)
    }

    fun collide3(others: Point) {
        val dx = others.position.x - position.x
        val dy = others.position.y - position.y
        val distance = sqrt(dx * dx + dy * dy)
        val minDist = others.radius + radius
        if (distance < minDist) {
            val angle = atan2(dy, dx)
            val targetX = position.x + cos(angle) * minDist
            val targetY = position.y + sin(angle) * minDist
            val ax = (targetX - others.position.x) * 0.8f
            val ay = (targetY - others.position.y) * 0.8f
            velocity.x -= ax
            velocity.y -= ay
            others.velocity.x += ax
            others.velocity.y += ay
        }
    }

}
