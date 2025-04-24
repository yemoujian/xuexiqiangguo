package com.example.xuexiqiangguo.models;

public class Video {
    private String id;
    private String url;
    private String title;
    private Integer duration;
    private String source;
    private String publishTime;
    private String topic;
    private String editor;

    public Video(String id, String url, String title, Integer duration, String source, String publishTime, String topic, String editor) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.duration = duration;
        this.source = source;
        this.publishTime = publishTime;
        this.topic = topic;
        this.editor = editor;
    }

    public String getId() {
        return id;
    }
    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public Integer getDuration() {
        return duration;
    }
    public String getDurationString() {

        Integer seconds = duration / 1000;
        Integer minutes = seconds / 60;
        Integer remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    public String getSource() {
        return source;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public String getTopic() {
        return topic;
    }

    public String getEditor() {
        return editor;
    }


    public String parseDate(Integer millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }
}
