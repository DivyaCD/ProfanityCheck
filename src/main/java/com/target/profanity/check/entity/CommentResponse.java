package com.target.profanity.check.entity;

import java.util.List;

public class CommentResponse {

    String comment;
    boolean objectionable;
    List<String> objectionableWords;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isObjectionable() {
        return objectionable;
    }

    public void setObjectionable(boolean objectionable) {
        this.objectionable = objectionable;
    }

    public List<String> getObjectionableWords() {
        return objectionableWords;
    }

    public void setObjectionableWords(List<String> objectionableWords) {
        this.objectionableWords = objectionableWords;
    }
}
