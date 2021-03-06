package com.christopherwmurphy.boringbutbigapp.database.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Objects;

import static android.arch.persistence.room.ForeignKey.SET_NULL;

@Entity(tableName = "exercise",
        foreignKeys = @ForeignKey(entity = ExerciseVideosEntity.class,
                                           parentColumns = "id",
                                           childColumns = "video_id",
                                           onDelete = SET_NULL),
        indices = {@Index(value = {"id"}, unique = true),
                   @Index("video_id"),
                   @Index(value = {"id", "language"}, unique = true)})
public class ExerciseEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "language")
    private String language;

    @ColumnInfo(name = "video_id")
    private Integer videoId;

    public ExerciseEntity(Integer id,
                          String name,
                          String language,
                          Integer videoId) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.videoId = videoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseEntity that = (ExerciseEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(language, that.language) &&
                Objects.equals(videoId, that.videoId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, language, videoId);
    }

    @Override
    public String toString() {
        return "ExerciseEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", videoId=" + videoId +
                '}';
    }
}
