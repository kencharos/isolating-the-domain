package example.domain.type.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 日付
 */
public class Date {
    private LocalDate value;

    public Date(String dayOfMonth) {
        this(LocalDate.parse(dayOfMonth, DateTimeFormatter.ISO_DATE));
    }

    public Date(LocalDate value) {
        this.value = value;
    }

    public LocalDate value() {
        return value;
    }

    public YearMonth yearMonth() {
        return new YearMonth(value.getYear(), value.getYear());
    }

    public Year year() {
        return new Year(value.getYear());
    }

    public Month month() {
        return Month.of(value.getMonth().getValue());
    }

    public int dayOfMonth() {
        return value.getDayOfMonth();
    }

    @Override
    public String toString() {
        return value.format(DateTimeFormatter.ISO_DATE);
    }
}