package com.christopherwmurphy.boringbutbigapp.database.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "exercise_videos",
        indices = {@Index(value = {"id"}, unique = true)})
public class ExerciseVideosEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "video_url")
    private String video_url;

    public ExerciseVideosEntity(Integer id, String video_url) {
        this.id = id;
        this.video_url = video_url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseVideosEntity that = (ExerciseVideosEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(video_url, that.video_url);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, video_url);
    }

    @Override
    public String toString() {
        return "ExerciseVideosEntity{" +
                "id=" + id +
                ", video_url='" + video_url + '\'' +
                '}';
    }
}
