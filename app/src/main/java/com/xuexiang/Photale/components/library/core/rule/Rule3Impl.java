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

public class Rule3Impl extends AbsRule{

    public Rule3Impl(Context context) {
        super(context);
    }

    @Override
    public void measureChildren(int screenWidth, int screenHeight, int spacing, View... children) {
        measureManually(children[0], (screenWidth - spacing) / 2, (screenWidth - spacing) / 2);
        measureManually(children[1], (screenWidth - spacing) / 2, (screenWidth - spacing) / 2);
        measureManually(children[2], screenWidth, (screenWidth - spacing) / 2);
    }

    @Override
    public void layoutChildren(int screenWidth, int screenHeight, int spacing, View... children) {

        children[0].layout(0, 0, (screenWidth - spacing) / 2, (screenWidth - spacing) / 2);
        children[1].layout((screenWidth - spacing) / 2 + spacing, 0, screenWidth,
                (screenWidth - spacing) / 2);
        children[2].layout(0, spacing + (screenWidth - spacing) / 2, screenWidth, screenWidth);
    }
}
