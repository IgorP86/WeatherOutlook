package presenters;

import android.graphics.drawable.Drawable;

import model.ResponseSchema;

public interface PresentData<T extends ResponseSchema> {
    void fillData(T re, Drawable... drawables);
}
