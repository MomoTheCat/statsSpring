package pl.momothecat.stats.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by szymon on 04.03.2017.
 */
@JsonDeserialize(builder=SimpleStation.Builder.class)
public class SimpleStation {

    public static final String MONGO_ID = "mongoId";
    public static final String ID_NETWORK = "idNetwork";
    public static final String EXTRAS = "extras";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String NAME = "name";

    @Id
    private String mongoId;
    private String idNetwork;
    private List<SimpleExtra> extras;
    private double latitude;
    private double longitude;
    private String name;

    public List<SimpleExtra> getExtras() {
        return extras;
    }

    public String getMongoId() {
        return mongoId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getIdNetwork() {
        return idNetwork;
    }

    private SimpleStation(String mongoId, List<SimpleExtra> extras, double latitude, double longitude,
                          String name, String idNetwork) {
        this.mongoId = mongoId;
        this.extras = extras;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.idNetwork = idNetwork;
    }

    @Override
    public String toString() {
        return "SimpleStation{" +
                "mongoId='" + mongoId +
                ", extras=" + extras +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", name=" + name +
                ", idNetwork=" + idNetwork +
                '}';
    }

    public static SimpleStation.Builder newBuilder() {
        return new SimpleStation.Builder();
    }

    @JsonPOJOBuilder(buildMethodName="build", withPrefix="set")
    public static class Builder {
        private String mongoId;
        private String idNetwork;
        private List<SimpleExtra> extras;
        private double latitude;
        private double longitude;
        private String name;

        public Builder setMongoId(String mongoId) {
            this.mongoId = mongoId;
            return this;
        }

        public Builder setExtras(List<SimpleExtra> extras) {
            this.extras = extras;
            return this;
        }

        public Builder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setIdNetwork(String idNetwork) {
            this.idNetwork = idNetwork;
            return this;
        }

        public SimpleStation build() {
            return new SimpleStation(mongoId, extras, latitude, longitude, name, idNetwork);
        }

        public Builder copy(SimpleStation other) {
            return this.setMongoId(other.mongoId)
                    .setExtras(other.extras)
                    .setLatitude(other.latitude)
                    .setLongitude(other.longitude)
                    .setName(other.name)
                    .setIdNetwork(other.idNetwork);
        }
    }

}