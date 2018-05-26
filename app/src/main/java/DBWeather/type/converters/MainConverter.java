package DBWeather.type.converters;

import android.arch.persistence.room.TypeConverter;

import model.ResponseSchema;

public class MainConverter {
    @TypeConverter
    public String fromMainObj(ResponseSchema.Main main) {
        return String.format("%s;%s;%s", main.getTemperature(), main.getPressure(), main.getHumidity());
    }

    @TypeConverter
    public ResponseSchema.Main toMainObj(String data) {
        String[] params = data.split(";");
        return new ResponseSchema.Main(Float.parseFloat(params[0]),
                Float.parseFloat(params[1]),
                Integer.parseInt(params[2]),
                0, 0, 0, 0);
    }
}
