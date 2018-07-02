package database.converters;

import android.arch.persistence.room.TypeConverter;

import cities_data.CitySchema;
import weather_data.ResponseSchema;

public class CoordinatesConverter {
    //Из Object в БД
    @TypeConverter
    public String fromCoordinatesObj(CitySchema.Results.Geometry.Location location) {
        return String.format("%s;%s", location.getLongitude(), location.getLatitude());
    }

    @TypeConverter
    public CitySchema.Results.Geometry.Location toCoordinatesObj(String data) {
        String[] params = data.split(";");
        return new CitySchema.Results.Geometry.Location(Float.parseFloat(params[0]),
                Float.parseFloat(params[1]));
    }

}
