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

package com.xuexiang.Photale.components.library.core;

import android.view.View;
import android.view.ViewGroup;

public interface Adapter {
    /**
     * 获取封面个数
     */
    int getCoverCount();

    /**
     * 获取封面 View
     */
    View getImage(ViewGroup viewGroup, int position);

    /**
     * 获取底部View
     */
    View getBottom(ViewGroup viewGroup);
}
