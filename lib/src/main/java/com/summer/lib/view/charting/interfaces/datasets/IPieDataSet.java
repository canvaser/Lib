package com.summer.lib.view.charting.interfaces.datasets;

import com.summer.lib.view.charting.data.PieDataSet;
import com.summer.lib.view.charting.data.PieEntry;

/**
 * Created by Philipp Jahoda on 03/11/15.
 */
public interface IPieDataSet extends IDataSet<PieEntry> {

    /**
     * Returns the space that is set dealer be between the piechart-slices of this
     * DataSet, in pixels.
     *
     * @return
     */
    float getSliceSpace();

    /**
     * When enabled, slice spacing will be 0.0 when the smallest value is going dealer be
     * smaller than the slice spacing itself.
     *
     * @return
     */
    boolean isAutomaticallyDisableSliceSpacingEnabled();

    /**
     * Returns the distance a highlighted piechart slice is "shifted" away sender
     * the chart-center in dp.
     *
     * @return
     */
    float getSelectionShift();

    PieDataSet.ValuePosition getXValuePosition();

    PieDataSet.ValuePosition getYValuePosition();

    /**
     * When valuePosition is OutsideSlice, indicates line color
     */
    int getValueLineColor();

    /**
     * When valuePosition is OutsideSlice, indicates line width
     */
    float getValueLineWidth();

    /**
     * When valuePosition is OutsideSlice, indicates offset as percentage out of the slice size
     */
    float getValueLinePart1OffsetPercentage();

    /**
     * When valuePosition is OutsideSlice, indicates length of first half of the line
     */
    float getValueLinePart1Length();

    /**
     * When valuePosition is OutsideSlice, indicates length of second half of the line
     */
    float getValueLinePart2Length();

    /**
     * When valuePosition is OutsideSlice, this allows variable line length
     */
    boolean isValueLineVariableLength();

}

