package pl.momothecat.stats.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by szymon on 04.03.2017.
 */

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class SimpleStation {

    public static final String ID_NETWORK = "idNetwork";
    public static final String EXTRAS = "extras";

    @Id
    private String mongoId;
    private List<SimpleExtra> extras;
    private double latitude;
    private double longitude;
    private String name;
    private String idNetwork;


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