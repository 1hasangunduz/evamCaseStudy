package com.kavak.data;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
public class GetData {

    private static final Locale LOCALE_TR = new Locale("tr");

    public static String ariaLabel(LocalDate date) {
        return date.getDayOfMonth() + " " +
                getMonthName(date.getMonthValue()) + " " +
                date.getYear();
    }

    public static String getMonthName(int monthNumber) {
        return LocalDate.of(2025, monthNumber, 1)
                .getMonth()
                .getDisplayName(TextStyle.FULL, LOCALE_TR);
    }

    public static String getKayakDateSelector(LocalDate date) {
        return "div.vn3g-button[aria-label*='" + ariaLabel(date) + "']";
    }

    public enum PassengerType {
        ADULT("Yetişkin"),
        STUDENT("Öğrenci"),
        CHILD("Çocuk"),
        INFANT_LAP("Kucakta bebek"),
        INFANT_SEAT("Kendi koltuğunda çocuk");

        private final String label;
        PassengerType(String label) { this.label = label; }
        public String getLabel() { return label; }
    }

    public enum QuickFilter {
        BEST("En iyi"),
        CHEAPEST("En ucuz"),
        FASTEST("En kısa");

        private final String label;
        QuickFilter(String label) {
            this.label = label;
        }
        public String getLabel() {
            return label;
        }
    }

    public enum TransferFilter {
        DIRECT("Direkt"), ONE("1"), TWO("2+");

        private final String value;
        TransferFilter(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
}
