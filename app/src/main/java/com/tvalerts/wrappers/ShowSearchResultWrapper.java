package com.tvalerts.wrappers;

import com.tvalerts.domain.Show;

/**
 * Created by Anita on 23/01/16.
 */
public class ShowSearchResultWrapper {

    private int score;
    private Show show;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
