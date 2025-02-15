/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
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

package com.xuexiang.Photale.components;

import com.xuexiang.Photale.fragment.mainPage.mainPagefragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.Photale.R;
import com.xuexiang.Photale.adapter.base.ComponentContainerFragment;


/**
 * @author xuexiang
 * @since 2019-05-30 00:38
 */
@Page(name = "轮播条", extra = R.drawable.ic_widget_banner)
public class BannerFragment extends ComponentContainerFragment {
    /**
     * 获取页面的类集合[使用@Page注解进行注册的页面]
     *
     * @return
     */
    @Override
    protected Class[] getPagesClasses() {
        return new Class[] {
                mainPagefragment.class
        };
    }
}
