package presenters;

import model.ResponseSchema;

public interface PresentData<T extends ResponseSchema> {
    void fillData(T re, Object... addition);
}
