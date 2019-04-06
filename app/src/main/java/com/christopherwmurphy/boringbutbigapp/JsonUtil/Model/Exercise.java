package com.christopherwmurphy.boringbutbigapp.JsonUtil.Model;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;

public class Exercise {
    private Integer id;
    private ExerciseVideos video;
    private String name;
    private String language;

    public Exercise() {
        this.id = 0;
        this.video = null;
        this.name = Constants.EMPTY;
        this.language = Constants.EMPTY;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ExerciseVideos getVideo() {
        return video;
    }

    public void setVideo(ExerciseVideos vid) {
        this.video = vid;
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

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", video=" + video +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
