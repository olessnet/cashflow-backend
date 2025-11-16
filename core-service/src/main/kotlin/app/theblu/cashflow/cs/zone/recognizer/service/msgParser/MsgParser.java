package app.theblu.cashflow.cs.zone.recognizer.service.msgParser;

import app.theblu.cashflow.cs.domain.Msg;
import app.theblu.cashflow.cs.domain.org.Org;
import app.theblu.cashflow.cs.domain.template.MsgDataField;
import app.theblu.cashflow.cs.domain.template.MsgTemplate;
import app.theblu.cashflow.cs.zone.recognizer.dto.MsgRecognitionRes;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

@Slf4j
public class MsgParser {
    public static MsgRecognitionRes parse(Msg msg, MsgTemplate template, Org org) {
        var rec = new MsgRecognitionRes();
        var data = template.data(msg.getBody());


        if (msg.getId().equals("02ac85bc8314a6ca6df359cff2f9b569")) {
            System.out.println();
        }
        rec.setId(msg.getId());
        rec.setStatus(MsgRecognitionRes.Status.RECOGNIZED);
        rec.setFiMsgIntent(template.getFiMsgIntent());
        rec.setFiChannel(template.getFiChannel());
        rec.setOrgShortName(org.getShortName());
        rec.setTimestamp(parseDateTime(data, template.getDateFormat(), template.getTimeFormat(), msg.getTimestamp()));
        rec.setAmount(parseAmount(data));
        rec.setCurrency(parseCurrency(data));
        rec.setMyAccountId(parseMyAccountId(data));
        rec.setMerchantId(parseMerchantId(data));
        rec.setReferenceId(parseReferenceId(data));

        return rec;
    }

    // TODO Optimize and refractor
    public static Long parseDateTime(Map<MsgDataField, String> data, String dateFormat, String timeFormat, long msgTime) {
        LocalDate localDate = null;
        LocalTime localTime = null;

        localDate = parseDate(data, dateFormat);
        if (localDate == null) {
            Instant instant = Instant.ofEpochSecond(msgTime / 1000);
            ZoneId zoneId = ZoneId.systemDefault(); // Use the system's default time zone
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
            localDate = localDateTime.toLocalDate();
        }

        localTime = parseTime(data, timeFormat);
        if (localTime == null) {
            Instant instant = Instant.ofEpochSecond(msgTime / 1000);
            ZoneId zoneId = ZoneId.systemDefault(); // Use the system's default time zone
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
            localTime = localDateTime.toLocalTime();
        }

        var ldt = LocalDateTime.of(localDate, localTime);
        Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
        long epochMillis = instant.toEpochMilli();
        return epochMillis;
    }

    private static LocalDate parseDate(Map<MsgDataField, String> data, String dateFormat) {
        if (!data.containsKey(MsgDataField.DATE)) return null;

        val date = sanatize(data.get(MsgDataField.DATE));

        // parse date time with given format
        try {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .append(DateTimeFormatter.ofPattern(dateFormat))
                    .toFormatter();
            var localDate = LocalDate.parse(date, formatter);
            return localDate;
        } catch (Exception e) {
        }


        // parse date with fallback format
//        try {
//            Locale locale = Locale.ENGLISH;
//            List<Month> allMonths = Arrays.asList(Month.values());
//            List<String> shortMonthNames = new ArrayList<>();
//            for (Month month : allMonths) {
//                String abbr = month.getDisplayName(TextStyle.SHORT, locale);
//                shortMonthNames.add(abbr.toLowerCase());
//            }
//
//            var temp = date.trim().toLowerCase();
//            for (String month : shortMonthNames) {
//                val monthTem = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();
//                if (temp.contains(month)) {
//                    temp = temp.replace(month, monthTem);
//                }
//            }
//
//            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
//                    .parseCaseInsensitive()
//                    .append(DateTimeFormatter.ofPattern(dateFormat))
//                    .toFormatter();
//            var localDate = LocalDate.parse(date.toLowerCase(), formatter);
//            return localDate;
//        } catch (Exception e) {
//        }


        // parse date with fallback format
        try {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .append(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
                    .toFormatter();
            var localDate = LocalDate.parse(date, formatter);
            return localDate;
        } catch (Exception e) {
        }

        return null;
    }

    private static LocalTime parseTime(Map<MsgDataField, String> data, String timeFormat) {
        if (!data.containsKey(MsgDataField.TIME)) return null;

        var formatter = DateTimeFormatter.ofPattern(timeFormat);
        try {
            var localTime = LocalTime.parse(sanatize(data.get(MsgDataField.TIME)), formatter);
            return localTime;
        } catch (Exception e) {
        }

        return null;
    }

    private static double parseAmount(Map<MsgDataField, String> data) {
        if (!data.containsKey(MsgDataField.AMOUNT)) return 0.0;

        var amountStr = data.get(MsgDataField.AMOUNT);
        try {
            String temp = amountStr.replaceAll(",", "");
            return Double.parseDouble(sanatize(temp));
        } catch (Exception e) {
            // TODO Log Exception
            return 0.0;
        }
    }

    private static String parseCurrency(Map<MsgDataField, String> data) {
        if (!data.containsKey(MsgDataField.CURRENCY_CODE)) return null;
        var value = sanatize(data.get(MsgDataField.CURRENCY_CODE));
        if (value == null || value == "") return null;

        return CurrencyParser.parse(value);
    }

    private static String parseMyAccountId(Map<MsgDataField, String> data) {
        return sanatize(data.get(MsgDataField.MY_ACCOUNT_ID));
    }

    private static String parseMerchantId(Map<MsgDataField, String> data) {
        return sanatize(data.get(MsgDataField.MERCHANT_ID));

    }

    private static String parseReferenceId(Map<MsgDataField, String> data) {
        return sanatize(data.get(MsgDataField.REFERENCE_ID));
    }

    private static String sanatize(String str) {
        if (str == null) return null;
        String temp = str.trim();
        if (temp.isEmpty()) return null;

        var list = new char[]{'.', '*', '(', ')', ':', ';', '|', '/', '-'};
        for (char c : list) {
            while (true) {
                if (temp.charAt(0) == c) {
                    temp = temp.substring(1).trim();
                    continue;
                }
                break;
            }
            while (true) {
                if (temp.charAt(temp.length() - 1) == c) {
                    temp = temp.substring(0, temp.length() - 1).trim();
                    continue;
                }
                break;
            }
        }
        return temp;
    }
}
