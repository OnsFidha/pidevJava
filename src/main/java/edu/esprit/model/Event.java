package edu.esprit.model;

public class Event {
    private String EventImageSrc;
    private String ProfileImageSrc;
    private String username;
    private String EventName;
    private String nbStars;
    private String nbComments;

    public String getEventImageSrc() {
        return EventImageSrc;
    }

    public void setEventImageSrc(String eventImageSrc) {
        EventImageSrc = eventImageSrc;
    }

    public String getProfileImageSrc() {
        return ProfileImageSrc;
    }

    public void setProfileImageSrc(String profileImageSrc) {
        ProfileImageSrc = profileImageSrc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNbStars() {
        return nbStars;
    }

    public void setNbStars(String nbStars) {
        this.nbStars = nbStars;
    }

    public String getNbComments() {
        return nbComments;
    }

    public void setNbComments(String nbComments) {
        this.nbComments = nbComments;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }
}
