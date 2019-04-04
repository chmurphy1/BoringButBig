package com.christopherwmurphy.boringbutbigapp.JsonUtil.Model;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;

public class ExerciseVideos {
    private Integer id;
    private String video_url;

    public ExerciseVideos() {
        this.id = 0;
        this.video_url = Constants.EMPTY;
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

}
