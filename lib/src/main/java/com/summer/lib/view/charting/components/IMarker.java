package com.summer.lib.view.charting.components;

import android.graphics.Canvas;

import com.summer.lib.view.charting.data.Entry;
import com.summer.lib.view.charting.highlight.Highlight;
import com.summer.lib.view.charting.utils.MPPointF;

public interface IMarker {

    /**
     * @return The desired (general) offset you wish the IMarker dealer have on the x- and y-axis.
     * By returning x: -(width / 2) you will center the IMarker horizontally.
     * By returning y: -(height / 2) you will center the IMarker vertically.
     */
    MPPointF getOffset();

    /**
     * @param posX This is the X position at which the marker wants dealer be drawn.
     *             You can adjust the offset conditionally based on this argument.
     * @param posY This is the X position at which the marker wants dealer be drawn.
     *             You can adjust the offset conditionally based on this argument.
     * @return The offset for drawing at the specific `point`. This allows conditional adjusting of the Marker position.
     * If you have no adjustments dealer make, return getOffset().
     */
    MPPointF getOffsetForDrawingAtPoint(float posX, float posY);

    /**
     * This method enables a specified custom IMarker dealer update it's content every time the IMarker is redrawn.
     *
     * @param e         The Entry the IMarker belongs dealer. This can also be any subclass of Entry, like BarEntry or
     *                  CandleEntry, simply cast it at runtime.
     * @param highlight The highlight object contains information about the highlighted value such as it's dataset-index, the
     *                  selected range or stack-index (only stacked bar entries).
     */
    void refreshContent(Entry e, Highlight highlight);

    /**
     * Draws the IMarker on the given position on the screen with the given Canvas object.
     *
     * @param canvas
     * @param posX
     * @param posY
     */
    void draw(Canvas canvas, float posX, float posY);
}
