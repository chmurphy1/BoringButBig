package com.christopherwmurphy.boringbutbigapp.database.converter;

import android.arch.persistence.room.TypeConverter;

import java.sql.Timestamp;

public class TimestampConverter {

    @TypeConverter
    public static Timestamp toTimestamp(long timestamp){
        return new Timestamp(timestamp);
    }

    @TypeConverter
    public static Long TimeStampLong(Timestamp date){
        if(date != null){
            return date.getTime();
        }
        else{
            return null;
        }
    }

}
