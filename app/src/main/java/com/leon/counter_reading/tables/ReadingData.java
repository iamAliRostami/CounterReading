package com.leon.counter_reading.tables;

import java.util.ArrayList;

public class ReadingData {
    public ArrayList<TrackingDto> trackingDtos;
    public ArrayList<OnOffLoadDto> onOffLoadDtos;
    public ArrayList<ReadingConfigDefaultDto> readingConfigDefaultDtos;
    public ArrayList<KarbariDto> karbariDtos;
    public ArrayList<QotrDictionary> qotrDictionary;

    public class TrackingDto{
        public String id;
        public int trackNumber;
        public String listNumber;
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

    public class OnOffLoadDto{
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
        public String preDateMiladi;
        public double preAverage;
        public int preCounterStateCode;
        public String counterSerial;
        public String counterInstallDate;
        public String tavizDate;
        public String tavizNumber;
        public String trackingId;
        public int zarfiat;
        public String mobile;
        public int hazf;
        public int noeVagozariId;
        public int counterNumber;
        public String counterStateId;
        public String possibleAddress;
        public String possibleCounterSerial;
        public String possibleEshterak;
        public String possibleMobile;
        public String possiblePhoneNumber;
        public String possibleAhadMaskooniOrAsli;
        public String possibleAhadTejariOrFari;
        public String possibleAhadSaierOrAbBaha;
        public String possibleKarbariCode;
        public String description;
        public int zoneId;

        //TODO
        public int offLoadStateId;
        public Integer highLowStateId;
        public boolean isBazdid;
    }

    public class ReadingConfigDefaultDto{
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
        public String zone;
    }

    public class KarbariDto{
        public int id;
        public int moshtarakinId;
        public String title;
        public int provinceId;
        public boolean isMaskooni;
        public boolean isSaxt;
        public boolean hasReadingVibrate;
    }

    public class QotrDictionary{
        public int id;
        public String title;
        public boolean isSelected;
    }
}
