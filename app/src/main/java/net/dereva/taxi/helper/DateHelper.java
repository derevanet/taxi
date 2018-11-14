package net.dereva.taxi.helper;

import net.dereva.taxi.model.Order;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    public static final String datePattern = "dd MMMM yyyy";
    public static final String timePattern = "HH:mm";

    public static String showOrderDate(Order order) {
        Date orderDate = order.getOrderTime();
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern, myDateFormatSymbols);

        return formatter.format(orderDate);
    }

    public static String showOrderTime(Order order) {
        Date orderDate = order.getOrderTime();
        SimpleDateFormat formatter = new SimpleDateFormat(timePattern);

        return formatter.format(orderDate);
    }

    private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols(){

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }

    };
}
