package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.christopherwmurphy.boringbutbigapp.database.Entity.SetSchemeEntity;

import java.util.List;

@Dao
public interface SetSchemeDao {

    @Insert
    void insert(SetSchemeEntity scheme);

    @Insert
    void insertAll(List<SetSchemeEntity> schemes);
}
