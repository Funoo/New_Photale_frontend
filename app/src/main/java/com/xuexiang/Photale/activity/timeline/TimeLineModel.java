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

package com.xuexiang.Photale.activity.timeline;

public class TimeLineModel {
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(int imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int type;

    public TimeLineModel(int type, int imageFileName, String productName, String productPrice, String month, String year) {
        this.type = type;
        this.imageFileName = imageFileName;
        this.productName = productName;
        this.productPrice = productPrice;
        this.month = month;
        this.year = year;
    }

    public int imageFileName;
//    public String imageFileName;
    public String productName;
    public String productPrice;
    public String month;
    public String year;
}