/**
 * Copyright (c) 2015-present, Horcrux.
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */


package com.horcrux.svg;


import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.annotations.ReactProp;

import javax.annotation.Nullable;

import static android.graphics.PathMeasure.POSITION_MATRIX_FLAG;
import static android.graphics.PathMeasure.TANGENT_MATRIX_FLAG;

/**
 * Shadow node for virtual TSpan view
 */
public class TSpanShadowNode extends TextShadowNode {

    private Path mCache;
    private @Nullable String mContent;
    private TextPathShadowNode textPath;

    private static final String PROP_FONT_FAMILY = "fontFamily";
    private static final String PROP_FONT_SIZE = "fontSize";
    private static final String PROP_FONT_STYLE = "fontStyle";
    private static final String PROP_FONT_WEIGHT = "fontWeight";
    private static final String PROP_KERNING = "kerning";
    private static final String PROP_IS_KERNING_VALUE_SET = "isKerningValueSet";
    private static final String PROP_LETTER_SPACING = "letterSpacing";

    @ReactProp(name = "content")
    public void setContent(@Nullable String content) {
        mContent = content;
        markUpdated();
    }

    @Override
    public void draw(Canvas canvas, Paint paint, float opacity) {
        if (mContent != null) {
            drawPath(canvas, paint, opacity);
        } else {
            clip(canvas, paint);
            drawGroup(canvas, paint, opacity);
        }
    }

    @Override
    protected void releaseCachedPath() {
        mCache = null;
    }

    @Override
    protected Path getPath(Canvas canvas, Paint paint) {
        if (mCache != null) {
            return mCache;
        }

        if (mContent == null) {
            return getGroupPath(canvas, paint);
        }

        setupTextPath();

        pushGlyphContext();
        Path path = mCache = getLinePath(canvas, mContent, paint);
        popGlyphContext();

        path.computeBounds(new RectF(), true);

        return path;
    }

    private float getTextAnchorShift(float width) {
        float x = 0;

        switch (getComputedTextAnchor()) {
            case TEXT_ANCHOR_MIDDLE:
                x = -width / 2;
                break;
            case TEXT_ANCHOR_END:
                x = -width;
                break;
        }

        return x;
    }

    private Path getLinePath(Canvas canvas, String line, Paint paint) {
        ReadableMap font = applyTextPropertiesToPaint(paint, canvas);
        int length = line.length();
        Path path = new Path();

        if (length == 0) {
            return path;
        }

        float distance = 0;
        float startOffset = 0;
        float renderMethodScaling = 1;
        float textMeasure = paint.measureText(line);

        PathMeasure pm = null;
        if (textPath != null) {
            pm = new PathMeasure(textPath.getPath(), false);
            distance = pm.getLength();
            startOffset = PropHelper.fromPercentageToFloat(textPath.getStartOffset(), distance, 0, mScale);
            // String spacing = textPath.getSpacing(); // spacing = "auto | exact"
            String method = textPath.getMethod(); // method = "align | stretch"
            if ("stretch".equals(method)) {
                renderMethodScaling = distance / textMeasure;
            }
        }

        Path glyph;
        float width;
        Matrix matrix;
        String current;
        PointF glyphPoint;
        PointF glyphDelta;
        float glyphRotation;
        String previous = "";
        float previousWidth = 0;
        char[] chars = line.toCharArray();
        double kerningValue = font.getDouble(PROP_KERNING) * mScale;
        boolean isKerningValueSet = font.getBoolean(PROP_IS_KERNING_VALUE_SET);
        float glyphPosition = startOffset + getTextAnchorShift(textMeasure);

        for (int index = 0; index < length; index++) {
            glyph = new Path();
            current = String.valueOf(chars[index]);
            paint.getTextPath(current, 0, 1, 0, 0, glyph);
            width = paint.measureText(current) * renderMethodScaling;

            if (isKerningValueSet) {
                glyphPosition += kerningValue;
            } else {
                float bothWidth = paint.measureText(previous + current) * renderMethodScaling;
                float kerning = bothWidth - previousWidth - width;
                glyphPosition += kerning;
                previousWidth = width;
                previous = current;
            }

            glyphPoint = getGlyphPointFromContext(glyphPosition, width);
            glyphRotation = getNextGlyphRotationFromContext();
            glyphDelta = getGlyphDeltaFromContext();
            glyphPosition += width;
            matrix = new Matrix();

            if (textPath != null) {
                float halfway = width / 2;
                float midpoint = glyphPoint.x + glyphDelta.x + halfway;

                if (midpoint > distance ) {
                    break;
                } else if (midpoint < 0) {
                    continue;
                }

                pm.getMatrix(midpoint, matrix, POSITION_MATRIX_FLAG | TANGENT_MATRIX_FLAG);

                matrix.preTranslate(-halfway, glyphDelta.y);
                matrix.preScale(renderMethodScaling, 1);
                matrix.postTranslate(0, glyphPoint.y);
            } else {
                matrix.setTranslate(
                    glyphPoint.x + glyphDelta.x,
                    glyphPoint.y + glyphDelta.y
                );
            }

            matrix.preRotate(glyphRotation);
            glyph.transform(matrix);
            path.addPath(glyph);
        }

        return path;
    }

    private ReadableMap applyTextPropertiesToPaint(Paint paint, Canvas canvas) {
        ReadableMap font = getFontFromContext();

        paint.setTextAlign(Paint.Align.LEFT);

        float fontSize = (float)font.getDouble(PROP_FONT_SIZE) * mScale;
        float letterSpacing = (float)font.getDouble(PROP_LETTER_SPACING) * mScale;

        paint.setTextSize(fontSize);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            paint.setLetterSpacing(letterSpacing / fontSize);  // setLetterSpacing is only available from LOLLIPOP and on
        }

        int decoration = getTextDecoration();

        paint.setUnderlineText(decoration == TEXT_DECORATION_UNDERLINE);
        paint.setStrikeThruText(decoration == TEXT_DECORATION_LINE_THROUGH);

        boolean isBold = font.hasKey(PROP_FONT_WEIGHT) && "bold".equals(font.getString(PROP_FONT_WEIGHT));
        boolean isItalic = font.hasKey(PROP_FONT_STYLE) && "italic".equals(font.getString(PROP_FONT_STYLE));

        int fontStyle;
        if (isBold && isItalic) {
            fontStyle = Typeface.BOLD_ITALIC;
        } else if (isBold) {
            fontStyle = Typeface.BOLD;
        } else if (isItalic) {
            fontStyle = Typeface.ITALIC;
        } else {
            fontStyle = Typeface.NORMAL;
        }

        AssetManager a = getThemedContext().getResources().getAssets();

        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(a, "fonts/" + font.getString(PROP_FONT_FAMILY) + ".otf");
        } catch (Exception ignored) {
            try {
                tf = Typeface.createFromAsset(a, "fonts/" + font.getString(PROP_FONT_FAMILY) + ".ttf");
            } catch (Exception ignored2) {
                try {
                    tf = Typeface.create(font.getString(PROP_FONT_FAMILY), fontStyle);
                } catch (Exception ignored3) {
                }
            }
        }

        // NB: if the font family is null / unsupported, the default one will be used
        paint.setTypeface(tf);

        return font;
    }

    private void setupTextPath() {
        ReactShadowNode parent = getParent();

        while (parent != null) {
            if (parent.getClass() == TextPathShadowNode.class) {
                textPath = (TextPathShadowNode)parent;
                break;
            } else if (!(parent instanceof TextShadowNode)) {
                break;
            }

            parent = parent.getParent();
        }
    }
}
