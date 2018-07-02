package database.converters;

import android.arch.persistence.room.TypeConverter;

import weather_data.ResponseSchema;

public class WindConverter {
    @TypeConverter
    public String fromWindObj(ResponseSchema.Wind wind) {
        return String.format("%s;%s", wind.getSpeed(), wind.getDegrees());
    }

    @TypeConverter
    public ResponseSchema.Wind toWindObj(String data) {
        String[] params = data.split(";");
        return new ResponseSchema.Wind(Float.parseFloat(params[0]), Float.parseFloat(params[1]));
    }
}
