/**
 * Copyright (c) 2015-present, Horcrux.
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */


package com.horcrux.svg;

import android.graphics.Matrix;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.common.ReactConstants;

import javax.annotation.Nullable;

/**
 * Shadow node for virtual RadialGradient definition view
 */
public class RadialGradientShadowNode extends DefinitionShadowNode {
    private String mFx;
    private String mFy;
    private String mRx;
    private String mRy;
    private String mCx;
    private String mCy;
    private ReadableArray mGradient;
    private Brush.BrushUnits mGradientUnits;

    private static final float[] sMatrixData = new float[9];
    private static final float[] sRawMatrix = new float[9];
    protected Matrix mMatrix = new Matrix();

    @ReactProp(name = "fx")
    public void setFx(String fx) {
        mFx = fx;
        markUpdated();
    }

    @ReactProp(name = "fy")
    public void setFy(String fy) {
        mFy = fy;
        markUpdated();
    }

    @ReactProp(name = "rx")
    public void setRx(String rx) {
        mRx = rx;
        markUpdated();
    }

    @ReactProp(name = "ry")
    public void setRy(String ry) {
        mRy = ry;
        markUpdated();
    }

    @ReactProp(name = "cx")
    public void setCx(String cx) {
        mCx = cx;
        markUpdated();
    }

    @ReactProp(name = "cy")
    public void setCy(String cy) {
        mCy = cy;
        markUpdated();
    }

    @ReactProp(name = "gradient")
    public void setGradient(ReadableArray gradient) {
        mGradient = gradient;
        markUpdated();
    }

    @ReactProp(name = "gradientUnits")
    public void setGradientUnits(int gradientUnits) {
        switch (gradientUnits) {
            case 0:
                mGradientUnits = Brush.BrushUnits.OBJECT_BOUNDING_BOX;
                break;
            case 1:
                mGradientUnits = Brush.BrushUnits.USER_SPACE_ON_USE;
                break;
        }
        markUpdated();
    }

    @ReactProp(name = "gradientTransform")
    public void setGradientTransform(@Nullable ReadableArray matrixArray) {
        if (matrixArray != null) {
            int matrixSize = PropHelper.toFloatArray(matrixArray, sMatrixData);
            if (matrixSize == 6) {
                sRawMatrix[0] = sMatrixData[0];
                sRawMatrix[1] = sMatrixData[2];
                sRawMatrix[2] = sMatrixData[4] * mScale;
                sRawMatrix[3] = sMatrixData[1];
                sRawMatrix[4] = sMatrixData[3];
                sRawMatrix[5] = sMatrixData[5] * mScale;
                sRawMatrix[6] = 0;
                sRawMatrix[7] = 0;
                sRawMatrix[8] = 1;
                mMatrix.setValues(sRawMatrix);
            } else if (matrixSize != -1) {
                FLog.w(ReactConstants.TAG, "RNSVG: Transform matrices must be of size 6");
            }
        } else {
            mMatrix = null;
        }

        markUpdated();
    }

    @Override
    protected void saveDefinition() {
        if (mName != null) {
            WritableArray points = Arguments.createArray();
            points.pushString(mFx);
            points.pushString(mFy);
            points.pushString(mRx);
            points.pushString(mRy);
            points.pushString(mCx);
            points.pushString(mCy);

            Brush brush = new Brush(Brush.BrushType.RADIAL_GRADIENT, points, mGradientUnits);
            brush.setGradientColors(mGradient);
            if (mMatrix != null) {
                brush.setGradientTransform(mMatrix);
            }

            SvgViewShadowNode svg = getSvgShadowNode();
            if (mGradientUnits == Brush.BrushUnits.USER_SPACE_ON_USE) {
                brush.setUserSpaceBoundingBox(svg.getCanvasBounds());
            }

            svg.defineBrush(brush, mName);
        }
    }
}
