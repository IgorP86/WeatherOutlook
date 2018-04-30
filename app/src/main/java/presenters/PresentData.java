package presenters;

import android.graphics.drawable.Drawable;

import model.ResponseSchema;

public interface PresentData {
    void fillData(ResponseSchema re, Drawable...drawables);
}
