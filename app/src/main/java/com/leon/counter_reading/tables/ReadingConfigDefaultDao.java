package com.leon.counter_reading.tables;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReadingConfigDefaultDao {
    @Query("Select * From ReadingConfigDefaultDto")
    List<ReadingConfigDefaultDto> getReadingConfigDefaultDtos();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReadingConfigDefault(ReadingConfigDefaultDto readingConfigDefault);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllReadingConfigDefault(List<ReadingConfigDefaultDto> readingConfigDefaultDtos);
}
