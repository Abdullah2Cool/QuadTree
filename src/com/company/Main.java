package com.company;

import processing.core.PApplet;

public class Main extends PApplet {

    float fOffset;
    int scale, rows, cols;
    int nW, nH;

    public static void main(String[] args) {
        String[] processingArgs = {"mySketch"};
        Main mySketch = new Main();
        PApplet.runSketch(processingArgs, mySketch);
    }

    public void settings() {
        nW = 600;
        nH = 600;
        size(nW, nH);
//        background(0);
        fOffset = 0;

        scale = 20;
        rows = nW / scale;
        cols = nH / scale;

        smooth();
    }

    public void draw() {
//        background(0);
        noStroke();
        for (int y = 0; y < cols; y++) {
            for (int x = 0; x < rows; x++) {
                float fFill = map(noise(x+ fOffset, y + fOffset), 0, 1, 0, 255);
                fill(fFill);
                rect(x * scale, y * scale, scale, scale);

                fill(255 - fFill);
                ellipse(x * scale + (scale / 2), y * scale + (scale / 2), scale, scale);
            }
        }

        fOffset += 0.005;

        System.out.println(frameRate);
    }
}
