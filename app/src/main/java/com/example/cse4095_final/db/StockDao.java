package com.example.cse4095_final.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StockDao {

    @Insert
    void insert(Stock stock);

    @Delete
    void delete(Stock stock);

    @Update
    void update(Stock stock);

    @Query("SELECT * FROM Stock")
    LiveData<List<Stock>> getAll();

    @Query("DELETE FROM Stock")
    void deleteAll();

    @Query("SELECT * FROM Stock where name = :name")
    Stock isStockInDatabase(String name);
}
