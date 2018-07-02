package ui_presenters;

import weather_data.ResponseSchema;

public interface PresentData<T extends ResponseSchema> {
    void fillData(T re, Object... addition);
}
