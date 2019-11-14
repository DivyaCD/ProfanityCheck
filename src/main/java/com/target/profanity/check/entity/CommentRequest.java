package com.target.profanity.check.entity;

public class CommentRequest {
    String comment;
    boolean requireObjectionableWords;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isRequireObjectionableWords() {
        return requireObjectionableWords;
    }

    public void setRequireObjectionableWords(boolean requireObjectionableWords) {
        this.requireObjectionableWords = requireObjectionableWords;
    }
}
