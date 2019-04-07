package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.christopherwmurphy.boringbutbigapp.database.Entity.SetSchemeEntity;

import java.util.List;

@Dao
public interface SetSchemeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SetSchemeEntity scheme);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SetSchemeEntity> schemes);
}
