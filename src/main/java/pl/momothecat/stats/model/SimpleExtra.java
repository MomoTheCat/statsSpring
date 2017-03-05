package pl.momothecat.stats.model;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by szymon on 04.03.2017.
 */
public class SimpleExtra {

    private int slots;
    private int[] bike_uids;
    private int uid;
    private int number;
    private int empty_slots;
    private int free_bikes;
    private Date date;

    private SimpleExtra(int slots, int[] bike_uids, int uid, int number, int empty_slots, int free_bikes, Date date) {
        this.slots = slots;
        this.bike_uids = bike_uids;
        this.uid = uid;
        this.number = number;
        this.empty_slots = empty_slots;
        this.free_bikes = free_bikes;
        this.date = date;
    }

    public int getSlots() {
        return slots;
    }

    public int[] getBike_uids() {
        return bike_uids;
    }

    public int getUid() {
        return uid;
    }

    public int getNumber() {
        return number;
    }

    public int getEmpty_slots() {
        return empty_slots;
    }

    public int getFree_bikes() {
        return free_bikes;
    }

    public Date getDate() {
        return date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleExtra)) return false;

        SimpleExtra that = (SimpleExtra) o;

        if (slots != that.slots) return false;
        if (uid != that.uid) return false;
        if (number != that.number) return false;
        if (empty_slots != that.empty_slots) return false;
        if (free_bikes != that.free_bikes) return false;
        if (!Arrays.equals(bike_uids, that.bike_uids)) return false;
        return date != null ? date.equals(that.date) : that.date == null;

    }

    @Override
    public int hashCode() {
        int result = slots;
        result = 31 * result + Arrays.hashCode(bike_uids);
        result = 31 * result + uid;
        result = 31 * result + number;
        result = 31 * result + empty_slots;
        result = 31 * result + free_bikes;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SimpleExtra{" +
                "slots=" + slots +
                ", bike_uids=" + Arrays.toString(bike_uids) +
                ", uid=" + uid +
                ", number=" + number +
                ", empty_slots=" + empty_slots +
                ", free_bikes=" + free_bikes +
                ", date=" + date +
                '}';
    }

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
