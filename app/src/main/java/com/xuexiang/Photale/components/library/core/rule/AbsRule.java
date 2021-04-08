/*
 * Copyright (C) 2021 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.Photale.components.library.core.rule;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;

public abstract class AbsRule implements Rule {

    private Context mContext;

    public AbsRule(@NonNull Context context) {
        mContext = context;
    }

    protected int dp2px(float dp) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                mContext.getResources().getDisplayMetrics());
    }

    protected void measureManually(View target, int widthSize, int heightSize) {

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthSize, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightSize, View.MeasureSpec.EXACTLY);
        target.measure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void measureManually2(View target, int widthSize) {

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthSize, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec =
                View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
        target.measure(widthMeasureSpec, heightMeasureSpec);
    }
}
