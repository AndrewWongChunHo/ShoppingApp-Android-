package com.example.project_text.models;

import android.media.Image;
import android.widget.ImageView;

public class category {

    int categoryImage;

    public category(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }
}
