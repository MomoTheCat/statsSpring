package pl.momothecat.stats.model;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by szymon on 04.03.2017.
 */
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleStation)) return false;

        SimpleStation that = (SimpleStation) o;

        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (mongoId != null ? !mongoId.equals(that.mongoId) : that.mongoId != null) return false;
        if (idNetwork != null ? !idNetwork.equals(that.idNetwork) : that.idNetwork != null) return false;
        if (extras != null ? !extras.equals(that.extras) : that.extras != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = mongoId != null ? mongoId.hashCode() : 0;
        result = 31 * result + (idNetwork != null ? idNetwork.hashCode() : 0);
        result = 31 * result + (extras != null ? extras.hashCode() : 0);
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
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