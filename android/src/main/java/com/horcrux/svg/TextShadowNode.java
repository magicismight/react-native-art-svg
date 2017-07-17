/**
 * Copyright (c) 2015-present, Horcrux.
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */


package com.horcrux.svg;

import javax.annotation.Nullable;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * Shadow node for virtual Text view
 */

public class TextShadowNode extends GroupShadowNode {

    static final int TEXT_ANCHOR_AUTO = 0;
    static final int TEXT_ANCHOR_START = 1;
    static final int TEXT_ANCHOR_MIDDLE = 2;
    static final int TEXT_ANCHOR_END = 3;

    static final int TEXT_DECORATION_NONE = 0;
    static final int TEXT_DECORATION_UNDERLINE = 1;
    static final int TEXT_DECORATION_OVERLINE = 2;
    static final int TEXT_DECORATION_LINE_THROUGH = 3;
    static final int TEXT_DECORATION_BLINK = 4;

    private int mTextAnchor = TEXT_ANCHOR_AUTO;
    private int mTextDecoration = TEXT_DECORATION_NONE;
    private @Nullable  ReadableArray mRotate;
    private @Nullable  ReadableArray mDeltaX;
    private @Nullable ReadableArray mDeltaY;
    private @Nullable String mPositionX;
    private @Nullable String mPositionY;

    @ReactProp(name = "textAnchor", defaultInt = TEXT_ANCHOR_AUTO)
    public void setTextAnchor(int textAnchor) {
        mTextAnchor = textAnchor;
        markUpdated();
    }

    @ReactProp(name = "textDecoration", defaultInt = TEXT_DECORATION_NONE)
    public void setTextDecoration(int textDecoration) {
        mTextDecoration = textDecoration;
        markUpdated();
    }

    @ReactProp(name = "rotate")
    public void setRotate(@Nullable ReadableArray rotate) {
        mRotate = rotate;
        markUpdated();
    }

    @ReactProp(name = "deltaX")
    public void setDeltaX(@Nullable ReadableArray deltaX) {
        mDeltaX = deltaX;
        markUpdated();
    }

    @ReactProp(name = "deltaY")
    public void setDeltaY(@Nullable ReadableArray deltaY) {
        mDeltaY = deltaY;
        markUpdated();
    }

    @ReactProp(name = "positionX")
    public void setPositionX(@Nullable String positionX) {
        mPositionX = positionX;
        markUpdated();
    }

    @ReactProp(name = "positionY")
    public void setPositionY(@Nullable String positionY) {
        mPositionY = positionY;
        markUpdated();
    }

    @ReactProp(name = "font")
    public void setFont(@Nullable ReadableMap font) {
        mFont = font;
        markUpdated();
    }

    @Override
    public void draw(Canvas canvas, Paint paint, float opacity) {
        if (opacity > MIN_OPACITY_FOR_DRAW) {
            setupGlyphContext();
            clip(canvas, paint);
            Path path = getGroupPath(canvas, paint);
            drawGroup(canvas, paint, opacity);
            releaseCachedPath();
        }
    }

    @Override
    protected Path getPath(Canvas canvas, Paint paint) {
        setupGlyphContext();
        Path groupPath = getGroupPath(canvas, paint);
        releaseCachedPath();
        return groupPath;
    }

    private int getTextAnchor() {
        return mTextAnchor;
    }

    int getTextDecoration() {
        int decoration = mTextDecoration;
        if (decoration != TEXT_DECORATION_NONE) {
            return decoration;
        }
        ReactShadowNode shadowNode = this.getParent();

        while (shadowNode instanceof GroupShadowNode) {
            if (shadowNode instanceof TextShadowNode) {
                decoration = ((TextShadowNode) shadowNode).getTextDecoration();
                if (decoration != TEXT_DECORATION_NONE) {
                    break;
                }
            }

            shadowNode = shadowNode.getParent();
        }

        return decoration;
    }

    int getComputedTextAnchor() {
        int anchor = mTextAnchor;
        if (anchor != TEXT_ANCHOR_AUTO) {
            return anchor;
        }
        ReactShadowNode shadowNode = this.getParent();

        while (shadowNode instanceof GroupShadowNode) {
            if (shadowNode instanceof TextShadowNode) {
                anchor = ((TextShadowNode) shadowNode).getTextAnchor();
                if (anchor != TEXT_ANCHOR_AUTO) {
                    break;
                }
            }

            shadowNode = shadowNode.getParent();
        }

        return anchor;
    }

    protected void releaseCachedPath() {
        traverseChildren(new NodeRunnable() {
            public boolean run(VirtualNode node) {
                TextShadowNode text = (TextShadowNode)node;
                text.releaseCachedPath();
                return true;
            }
        });
    }

    protected Path getGroupPath(Canvas canvas, Paint paint) {
        pushGlyphContext();
        Path groupPath = super.getPath(canvas, paint);
        popGlyphContext();

        return groupPath;
    }

    @Override
    protected void pushGlyphContext() {
        getTextRoot().getGlyphContext().pushContext(mFont, mRotate, mDeltaX, mDeltaY, mPositionX, mPositionY);
    }
}
