package pl.momothecat.stats.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Created by szymon on 04.03.2017.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class SimpleExtra {

    private int slots;
    private int[] bike_uids;
    private int uid;
    private int number;
    private int empty_slots;
    private int free_bikes;
    private Date date;

    public static SimpleExtra.Builder newBuilder() {
        return new SimpleExtra.Builder();
    }

    public static class Builder {

        private int slots;
        private int[] bike_uids;
        private int uid;
        private int number;
        private int empty_slots;
        private int free_bikes;
        private Date date;

        public Builder setSlots(int slots) {
            this.slots = slots;
            return this;
        }

        public Builder setBike_uids(int[] bike_uids) {
            this.bike_uids = bike_uids;
            return this;
        }

        public Builder setUid(int uid) {
            this.uid = uid;
            return this;
        }

        public Builder setNumber(int number) {
            this.number = number;
            return this;
        }

        public Builder setEmpty_slots(int empty_slots) {
            this.empty_slots = empty_slots;
            return this;
        }

        public Builder setFree_bikes(int free_bikes) {
            this.free_bikes = free_bikes;
            return this;
        }

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }

        public SimpleExtra build() {
            return new SimpleExtra(slots, bike_uids, uid, number, empty_slots, free_bikes, date);
        }

        public Builder copy(SimpleExtra other) {
            return this.setBike_uids(other.bike_uids)
                    .setNumber(other.number)
                    .setSlots(other.slots)
                    .setUid(other.uid)
                    .setFree_bikes(other.free_bikes)
                    .setEmpty_slots(other.empty_slots)
                    .setDate(other.date);
        }

    }
}
