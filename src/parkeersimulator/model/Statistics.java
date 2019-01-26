package parkeersimulator.model;

import parkeersimulator.framework.Model;
import parkeersimulator.utility.Settings;

public class Statistics extends Model {

    private int parkingPrice;
    private int reservationPrice;
    private int passPrice;

    private int reservationMoney;
    private int adHocMoney;
    private int parkingPassMoney;
    private int totalWeek;
    private int totalDay;
    private int hourlyAverage;
    private int dailyAverage;

    private int[][] graphHourly;

    public Statistics(){
        setPrices();
    }

    private void setPrices()
    {
        parkingPrice = Settings.get("price.adhoc");
        reservationPrice = Settings.get("price.reserved");
        passPrice = Settings.get("price.pass");
    }

    public void tick(int dayOfWeek, int hour, int minute)
    {
        //TODO change handling of averages to display values after every tick
        if(dayOfWeek == 1 && hour == 0 && minute == 0) {
            dailyAverage = totalWeek / 7;
            totalWeek = 0;
        }
        else if (hour == 0 && minute == 0) {
                hourlyAverage = totalDay / 24;
                totalDay = 0;
            }

        updateViews();
    }

    public int getReservationMoney()
    {
        return reservationMoney;
    }

    public int getAdHocMoney() {
        return adHocMoney;
    }

    public int getDailyAverage() {
        return dailyAverage;
    }

    public int getHourlyAverage() {
        return hourlyAverage;
    }

    public int getParkingPassMoney() {
        return parkingPassMoney;
    }

    public int getTotal() {
        return reservationMoney + parkingPassMoney + adHocMoney;
    }

    public void payAdHoc()
    {
        adHocMoney += parkingPrice;
        totalDay += parkingPrice;
        totalWeek += parkingPrice;
    }

    public void payReservation()
    {
        reservationMoney += reservationPrice;
        totalDay += reservationPrice;
        totalWeek += reservationPrice;
    }

    public void payPass()
    {
        //TODO implement daily payment of members
        parkingPassMoney += passPrice;
        totalDay += passPrice;
        totalWeek += passPrice;
    }

    public void reset()
    {
        setPrices();
        reservationMoney = 0;
        adHocMoney = 0;
        parkingPassMoney = 0;
        hourlyAverage = 0;
        dailyAverage = 0;
        totalWeek = 0;
        totalDay = 0;

        updateViews();
    }
}
