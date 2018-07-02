package database.converters;

import android.arch.persistence.room.TypeConverter;

import weather_data.ResponseSchema;

public class SysConverter {

    @TypeConverter
    public String fromSysObj(ResponseSchema.Sys sys) {
        return String.format("%s;%s;%s", sys.getCountry(), sys.getSunrise(), sys.getSunset());
    }

    @TypeConverter
    public ResponseSchema.Sys toSysObj(String data) {
        String[] params = data.split(";");
        return new ResponseSchema.Sys(null, params[0], Long.parseLong(params[1]), Long.parseLong(params[2]));
    }
}
