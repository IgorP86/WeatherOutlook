package cities_data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import database.converters.CoordinatesConverter;


@Entity(tableName = CitySchema.DB_CITIES)
public class CitySchema {

    @Ignore
    public static final String DB_CITIES = "dbCities";
    //для получения json
    @Ignore
    private Results[] results;
    @Ignore
    private String status;

    public CitySchema(int primaryKey, String cityName, String country, Results.Geometry.Location location) {
        this.primaryKey = primaryKey;
        this.cityName = cityName;
        this.country = country;
        this.location = location;
    }

    //для представления объекта в базе данных
    @PrimaryKey(autoGenerate = true)
    private int primaryKey;
    private String cityName;
    private String country;
    @TypeConverters({CoordinatesConverter.class})
    private Results.Geometry.Location location;

//Временный код
    @Ignore
    private String placeID;
    public CitySchema(String placeID, String cityName, String country) {
        this.placeID = placeID;
        this.cityName = cityName;
        this.country = country;
    }

    public CitySchema convertInTuple() {
        try {
            this.location = new Results.Geometry.Location(results[0].geometry.location.longitude,
                    results[0].geometry.location.latitude);
            this.cityName = results[0].fullName.split(",")[0];
            int index = results[0].addressComponents.length-1;
            this.country = results[0].addressComponents[index].short_name;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return this;
    }


    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Results.Geometry.Location getLocation() {
        return location;
    }

    public void setLocation(Results.Geometry.Location location) {
        this.location = location;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    //для получения json
    public static class Results {
        @SerializedName("formatted_address")
        private String fullName;
        @SerializedName("address_components")
        private AddressComponents[] addressComponents;
        private Geometry geometry;
        @SerializedName("place_id")
        private String placeID;

        private static class AddressComponents {
            private String long_name;
            private String short_name;
            private String[] types;
        }

        public static class Geometry {
            private Location location;

            public static class Location {
                @SerializedName("lat")
                private float latitude;
                @SerializedName("lng")
                private float longitude;

                public float getLatitude() {
                    return latitude;
                }

                public float getLongitude() {
                    return longitude;
                }

                public Location(float latitude, float longitude) {
                    this.latitude = latitude;
                    this.longitude = longitude;
                }
            }
        }
    }
}
