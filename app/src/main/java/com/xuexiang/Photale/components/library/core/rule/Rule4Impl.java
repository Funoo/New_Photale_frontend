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
import android.view.View;

public class Rule4Impl extends AbsRule{

    public Rule4Impl(Context context) {
        super(context);
    }

    @Override
    public void measureChildren(int screenWidth, int screenHeight, int spacing, View... children) {

        for (View child : children) {

            measureManually(child, (screenWidth - spacing) / 2, (screenWidth - spacing) / 2);
        }
    }

    @Override
    public void layoutChildren(int screenWidth, int screenHeight, int spacing, View... children) {

        for (int index = 0; index < children.length; index++) {

            View child = children[index];

            int rowNum = index / 2;
            int columnNum = index % 2;

            int left = (child.getMeasuredWidth() + spacing) * columnNum;
            int top = (child.getMeasuredHeight() + spacing) * rowNum;
            int right = left + child.getMeasuredWidth();
            int bottom = top + child.getMeasuredHeight();

            child.layout(left, top, right, bottom);
        }
    }
}
