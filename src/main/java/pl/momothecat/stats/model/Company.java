package pl.momothecat.stats.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Created by szymon on 01.03.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "network")
@Getter
@ToString
public class Company {

    /**
     * network : {"company":["JCDecaux"],
     * "href":"/v2/networks/velib",
     * "id":"velib",
     * "license":{"name":"Open Licence",
     * "url":"https://developer.jcdecaux.com/#/opendata/licence"},
     * "location":{"city":"Paris",
     * "country":"FR",
     * "latitude":48.856614,
     * "longitude":2.3522219},
     * "name":"Velib",
     * "stations":[{"empty_slots":0,
     * "extra":{"address":"1 RUE BUFFON - 75005 PARIS",
     * "banking":true,
     * "bonus":false,
     * "last_update":1486858891000,
     * "slots":24,
     * "status":"OPEN",
     * "uid":5035},
     * "free_bikes":24,
     * "id":"02665e08554c92766bb82a8e8fe453f3",
     * "latitude":48.84315817822931,
     * "longitude":2.363748444641593,
     * "name":"05035 - BUFFON AUSTERLITZ",
     * "timestamp":"2017-02-12T00:24:35.939000Z"},
     * {"empty_slots":0,
     */


    private String id;
    private String href;
    private LicenseBean license;
    private LocationBean location;
    private String name;
    private List<String> company;
    private List<StationsBean> stations;

    @Getter
    @ToString
    public static class LicenseBean {
        /**
         * name : Open Licence
         * url : https://developer.jcdecaux.com/#/opendata/licence
         */
        private String name;
        private String url;
    }

    @Getter
    @ToString
    public static class LocationBean {
        /**
         * city : Paris
         * country : FR
         * latitude : 48.856614
         * longitude : 2.3522219
         */

        private String city;
        private String country;
        private double latitude;
        private double longitude;
    }

    @Getter
    @ToString
    public static class StationsBean {
        /**
         * empty_slots : 0
         * extra : {"address":"1 RUE BUFFON - 75005 PARIS","banking":true,"bonus":false,"last_update":1486858891000,"slots":24,"status":"OPEN","uid":5035}
         * free_bikes : 24
         * id : 02665e08554c92766bb82a8e8fe453f3
         * latitude : 48.84315817822931
         * longitude : 2.363748444641593
         * name : 05035 - BUFFON AUSTERLITZ
         * timestamp : 2017-02-12T00:24:35.939000Z
         */

        private int empty_slots;
        private ExtraBean extra;
        private int free_bikes;
        private String id;
        private double latitude;
        private double longitude;
        private String name;
        private String timestamp;

        @Getter
        @ToString
        public static class ExtraBean {
            /**
             * address : 1 RUE BUFFON - 75005 PARIS
             * banking : true
             * bonus : false
             * last_update : 1486858891000
             * slots : 24
             * status : OPEN
             * uid : 5035
             */

            private String address;
            private boolean banking;
            private boolean bonus;
            private long last_update;
            private int slots;
            private String status;
            private int uid;
            private int number;
            private int[] bike_uids;
        }
    }

}

