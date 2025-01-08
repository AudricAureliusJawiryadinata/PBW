package com.example.demo.Guest;

public class Concert {
    private String venue;
    private String city;
    private String date;
    private String time;

    public Concert(String venue, String city, String date, String time) {
        this.venue = venue;
        this.city = city;
        this.date = date;
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
