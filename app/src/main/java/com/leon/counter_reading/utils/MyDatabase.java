package com.leon.counter_reading.utils;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.leon.counter_reading.tables.CounterStateDao;
import com.leon.counter_reading.tables.CounterStateDto;
import com.leon.counter_reading.tables.KarbariDao;
import com.leon.counter_reading.tables.KarbariDto;
import com.leon.counter_reading.tables.OnOffLoadDao;
import com.leon.counter_reading.tables.OnOffLoadDto;
import com.leon.counter_reading.tables.QotrDictionary;
import com.leon.counter_reading.tables.QotrDictionaryDao;
import com.leon.counter_reading.tables.ReadingConfigDefaultDao;
import com.leon.counter_reading.tables.ReadingConfigDefaultDto;
import com.leon.counter_reading.tables.SavedLocation;
import com.leon.counter_reading.tables.SavedLocationsDao;
import com.leon.counter_reading.tables.TrackingDao;
import com.leon.counter_reading.tables.TrackingDto;

@Database(entities = {SavedLocation.class, KarbariDto.class, OnOffLoadDto.class,
        QotrDictionary.class, ReadingConfigDefaultDto.class, TrackingDto.class,
        CounterStateDto.class},
        version = 8, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract KarbariDao karbariDao();

    public abstract OnOffLoadDao onOffLoadDao();

    public abstract QotrDictionaryDao qotrDictionaryDao();

    public abstract ReadingConfigDefaultDao readingConfigDefaultDao();

    public abstract SavedLocationsDao savedLocationDao();

    public abstract CounterStateDao counterStateDao();

    public abstract TrackingDao trackingDao();

    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE t1_backup AS SELECT * FROM KarbariDto");
            database.execSQL("DROP TABLE KarbariDto");
            database.execSQL("ALTER TABLE t1_backup RENAME TO KarbariDto");
            database.execSQL("DROP TABLE t1_backup");

            database.execSQL("CREATE TABLE t1_backup AS SELECT * FROM SavedLocation");
            database.execSQL("DROP TABLE SavedLocation");
            database.execSQL("ALTER TABLE t1_backup RENAME TO SavedLocation");
            database.execSQL("DROP TABLE t1_backup");

            database.execSQL("CREATE TABLE t1_backup AS SELECT * FROM OnOffLoadDto");
            database.execSQL("DROP TABLE OnOffLoadDto");
            database.execSQL("ALTER TABLE t1_backup RENAME TO OnOffLoadDto");
            database.execSQL("DROP TABLE t1_backup");


            database.execSQL("CREATE TABLE t1_backup AS SELECT * FROM QotrDictionary");
            database.execSQL("DROP TABLE QotrDictionary");
            database.execSQL("ALTER TABLE t1_backup RENAME TO QotrDictionary");
            database.execSQL("DROP TABLE t1_backup");

            database.execSQL("CREATE TABLE t1_backup AS SELECT * FROM ReadingConfigDefaultDto");
            database.execSQL("DROP TABLE ReadingConfigDefaultDto");
            database.execSQL("ALTER TABLE t1_backup RENAME TO ReadingConfigDefaultDto");
            database.execSQL("DROP TABLE t1_backup");

            database.execSQL("CREATE TABLE t1_backup AS SELECT * FROM TrackingDto");
            database.execSQL("DROP TABLE TrackingDto");
            database.execSQL("ALTER TABLE t1_backup RENAME TO TrackingDto");
            database.execSQL("DROP TABLE t1_backup");

            database.execSQL("CREATE TABLE t1_backup AS SELECT * FROM CounterStateDto");
            database.execSQL("DROP TABLE CounterStateDto");
            database.execSQL("ALTER TABLE t1_backup RENAME TO CounterStateDto");
            database.execSQL("DROP TABLE t1_backup");
        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE t1_backup AS SELECT * FROM TrackingDto");
            database.execSQL("DROP TABLE TrackingDto");
            database.execSQL("ALTER TABLE t1_backup RENAME TO TrackingDto");
            database.execSQL("DROP TABLE t1_backup");
        }
    };

    public static final Migration MIGRATION_6_7 = new Migration(7, 8) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE UNIQUE INDEX 'id' ON TrackingDto(id);");
            database.execSQL("CREATE UNIQUE INDEX 'id' ON KarbariDto(id);");
            database.execSQL("CREATE UNIQUE INDEX 'id' ON OnOffLoadDto(id);");
            database.execSQL("CREATE UNIQUE INDEX 'id' ON QotrDictionary(id);");
            database.execSQL("CREATE UNIQUE INDEX 'id' ON ReadingConfigDefaultDto(id);");
            database.execSQL("CREATE UNIQUE INDEX 'id' ON CounterStateDto(id);");
        }
    };
}
