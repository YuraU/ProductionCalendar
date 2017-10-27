package com.yura.productioncalendar.model;


import com.annimon.stream.Stream;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarInfo {

    @SerializedName("Год/Месяц")
    @Expose
    private String year;

    @SerializedName("Январь")
    @Expose
    private String january;

    @SerializedName("Февраль")
    @Expose
    private String february;

    @SerializedName("Март")
    @Expose
    private String march;

    @SerializedName("Апрель")
    @Expose
    private String april;

    @SerializedName("Май")
    @Expose
    private String may;

    @SerializedName("Июнь")
    @Expose
    private String june;

    @SerializedName("Июль")
    @Expose
    private String july;

    @SerializedName("Август")
    @Expose
    private String august;

    @SerializedName("Сентябрь")
    @Expose
    private String september;

    @SerializedName("Октябрь")
    @Expose
    private String october;

    @SerializedName("Ноябрь")
    @Expose
    private String november;

    @SerializedName("Декабрь")
    @Expose
    private String december;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getJanuary() {
        return january;
    }

    public void setJanuary(String january) {
        this.january = january;
    }

    public String getFebruary() {
        return february;
    }

    public void setFebruary(String february) {
        this.february = february;
    }

    public String getMarch() {
        return march;
    }

    public void setMarch(String march) {
        this.march = march;
    }

    public String getApril() {
        return april;
    }

    public void setApril(String april) {
        this.april = april;
    }

    public String getMay() {
        return may;
    }

    public void setMay(String may) {
        this.may = may;
    }

    public String getJune() {
        return june;
    }

    public void setJune(String june) {
        this.june = june;
    }

    public String getJuly() {
        return july;
    }

    public void setJuly(String july) {
        this.july = july;
    }

    public String getAugust() {
        return august;
    }

    public void setAugust(String august) {
        this.august = august;
    }

    public String getSeptember() {
        return september;
    }

    public void setSeptember(String september) {
        this.september = september;
    }

    public String getCctober() {
        return october;
    }

    public void setOctober(String october) {
        this.october = october;
    }

    public String getNovember() {
        return november;
    }

    public void setNovember(String november) {
        this.november = november;
    }

    public String getDecember() {
        return december;
    }

    public void setDecember(String december) {
        this.december = december;
    }

    public Calendar getFirstDay() {
        Calendar firstDay = Calendar.getInstance();
        firstDay.set(Integer.parseInt(year), 1, 1);
        return firstDay;
    }

    public Calendar getLastDay() {
        Calendar lastDay = Calendar.getInstance();
        //CHECKSTYLE:OFF
        lastDay.set(Integer.parseInt(year), 11, 31);
        //CHECKSTYLE:ON
        return lastDay;
    }

    public List<CalendarDay> getWeekendDays() {
        int iYear = Integer.parseInt(year);

        List<CalendarDay> weekends = new ArrayList<>();

        //CHECKSTYLE:OFF
        for (int month = 0; month <= 11; month++) {
            weekends.addAll(getWeekendsInMonth(iYear, month));
        }
        //CHECKSTYLE:ON

        return weekends;
    }

    public List<CalendarDay> getHolidays() {
        int iYear = Integer.parseInt(year);

        List<CalendarDay> holidays = new ArrayList<>();

        //CHECKSTYLE:OFF
        for (int month = 0; month <= 11; month++) {
            holidays.addAll(getHolidaysInMonth(iYear, month));
        }
        //CHECKSTYLE:ON

        return holidays;
    }

    public List<CalendarDay> getWeekendsInMonth(int iYear, int iMonth) {
        String[] days = getDays(iMonth);

        return Stream.of(days)
                .filter(f -> !f.contains("*"))
                .map(day -> CalendarDay.from(iYear, iMonth, Integer.valueOf(day)))
                .toList();
    }

    public List<CalendarDay> getHolidaysInMonth(int iYear, int iMonth) {
        String[] days = getDays(iMonth);

        return Stream.of(days)
                .filter(f -> f.contains("*"))
                .map(day -> {
                    day = day
                            .replace(" ", "")
                            .replace("*", "");

                    return CalendarDay.from(iYear, iMonth, Integer.valueOf(day));
                })
                .toList();
    }

    private String[] getDays(int month) {
        String[] days = null;

        //CHECKSTYLE:OFF
        switch (month) {
            case 0:
                days = january.split(",");
                break;
            case 1:
                days = february.split(",");
                break;
            case 2:
                days = march.split(",");
                break;
            case 3:
                days = april.split(",");
                break;
            case 4:
                days = may.split(",");
                break;
            case 5:
                days = june.split(",");
                break;
            case 6:
                days = july.split(",");
                break;
            case 7:
                days = august.split(",");
                break;
            case 8:
                days = september.split(",");
                break;
            case 9:
                days = october.split(",");
                break;
            case 10:
                days = november.split(",");
                break;
            case 11:
                days = december.split(",");
                break;
            default:
                new IllegalArgumentException("Invalid params month");
        }
        //CHECKSTYLE:ON

        return days;
    }

}
