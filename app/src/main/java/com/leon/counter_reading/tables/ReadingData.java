package com.leon.counter_reading.tables;

import java.util.ArrayList;
import java.util.Date;

public class ReadingData {
    public ArrayList<TrackingDto> trackingDtos;
    public ArrayList<OnOffLoadDto> onOffLoadDtos;
    public ArrayList<ReadingConfigDefaultDto> readingConfigDefaultDtos;

    public class TrackingDto {
        public String id;
        public int trackNumber;
        public Object listNumber;
        public String insertDateJalali;
        public int zoneId;
        public String zoneTitle;
        public boolean isBazdid;
        public int year;
        public boolean isRoosta;
        public String fromEshterak;
        public String toEshterak;
        public String fromDate;
        public String toDate;
        public int itemQuantity;
        public int alalHesabPercent;
        public int imagePercent;
        public boolean hasPreNumber;
        public boolean displayBillId;
        public boolean displayRadif;
    }

    public class OnOffLoadDto {
        public String id;
        public String billId;
        public int radif;
        public String eshterak;
        public String qeraatCode;
        public String firstName;
        public String sureName;
        public String address;
        public String pelak;
        public int karbariCode;
        public int ahadMaskooniOrAsli;
        public int ahadTejariOrFari;
        public int ahadSaierOrAbBaha;
        public int qotrCode;
        public int sifoonQotrCode;
        public String postalCode;
        public int preNumber;
        public String preDate;
        public Date preDateMiladi;
        public double preAverage;
        public int preCounterStateCode;
        public String counterSerial;
        public String counterInstallDate;
        public String tavizDate;
        public Object tavizNumber;
        public int zarfiat;
        public String mobile;
        public int hazf;
        public int noeVagozariId;
        public Object counterNumber;
        public Object counterStateId;
        public Object possibleAddress;
        public Object possibleCounterSerial;
        public Object possibleEshterak;
        public Object possibleMobile;
        public Object possiblePhoneNumber;
        public Object possibleAhadMaskooniOrAsli;
        public Object possibleAhadTejariOrFari;
        public Object possibleAhadSaierOrAbBaha;
        public Object possibleKarbariCode;
        public Object description;
    }

    public class ReadingConfigDefaultDto {
        public int id;
        public int zoneId;
        public int defaultAlalHesab;
        public int maxAlalHesab;
        public int minAlalHesab;
        public int defaultImagePercent;
        public int maxImagePercent;
        public int minImagePercent;
        public boolean defaultHasPreNumber;
        public boolean isOnQeraatCode;
        public boolean displayBillId;
        public boolean displayRadif;
        public int lowConstBoundMaskooni;
        public int lowPercentBoundMaskooni;
        public int highConstBoundMaskooni;
        public int highPercentBoundMaskooni;
        public int lowConstBoundSaxt;
        public int lowPercentBoundSaxt;
        public int highConstBoundSaxt;
        public int highPercentBoundSaxt;
        public int lowConstZarfiatBound;
        public int lowPercentZarfiatBound;
        public int highConstZarfiatBound;
        public int highPercentZarfiatBound;
        public int lowPercentRateBoundNonMaskooni;
        public int highPercentRateBoundNonMaskooni;
        public boolean isActive;
        public Object zone;
    }
}
