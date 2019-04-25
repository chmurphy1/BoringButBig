package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.SetScheme;
import com.christopherwmurphy.boringbutbigapp.database.Entity.SetSchemeEntity;

import java.util.List;

@Dao
public interface SetSchemeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SetSchemeEntity scheme);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SetSchemeEntity> schemes);

    @Query("Select * from set_scheme")
    List<SetSchemeEntity> getAllSchemes();

    @Query("delete from set_scheme where set_id = :id")
    void delete(int id);
}
