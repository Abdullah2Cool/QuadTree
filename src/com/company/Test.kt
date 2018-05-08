package com.company

import processing.core.PApplet

object Test : PApplet() {

    internal var fOffset: Float = 0f
    internal var fDelta: Float = 0.005f
    internal var scale: Int = 0
    internal var rows: Int = 0
    internal var cols: Int = 0
    internal var nW: Int = 0
    internal var nH: Int = 0

    override fun settings() {
        nW = 600
        nH = 600
        size(nW, nH)
        //        background(0);
        fOffset = 0f

        scale = 20
        rows = nW / scale
        cols = nH / scale

        smooth()
    }

    override fun draw() {
        //        background(0);
        noStroke()
        for (y in 0 until cols) {
            for (x in 0 until rows) {
                val fFill = PApplet.map(noise(x + fOffset, y + fOffset), 0f, 1f, 0f, 255f)

//                when(fFill) {
//                    in 0.0..0.5 -> fill(0f)
//                    in 0.3..0.4 -> fill(100f, 0f, 255f)
//                    in 0.5..0.6 -> fill(100f, 255f, 0f)
//                    in 0.7..0.8 -> fill(255f, 0f, 100f)
//                    in 0.6..1.0 -> fill(255f)
//                }
                fill(fFill, 0f, fFill)
                rect((x * scale).toFloat(), (y * scale).toFloat(), scale.toFloat(), scale.toFloat())

                fill(0f, 255 - fFill, 255 - fFill)
                ellipse((x * scale + scale / 2).toFloat(), (y * scale + scale / 2).toFloat(), scale.toFloat(), scale.toFloat())
            }
        }
        fOffset += fDelta
//        println(frameRate)
    }

    override fun mousePressed() {
        super.mousePressed()
        fDelta *= -1
        println(fDelta)
    }

    operator fun invoke(): PApplet? {
        return this
    }


}

fun main(args: Array<String>) {
    val processingArgs = arrayOf("mySketch")
    val mySketch = Test()
    PApplet.runSketch(processingArgs, mySketch)

}
