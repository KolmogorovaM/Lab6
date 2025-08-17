package ru.marina.base;

import ru.marina.model.MusicBand;

import java.io.Serializable;

public class Request implements Serializable {
    private final String[] input;
    private MusicBand musicBand;

    public Request(String[] input) {
        this.input = input;
    }

    public Request(String[] input, MusicBand musicBand) {
        this.input = input;
        this.musicBand = musicBand;
    }

    public String[] getInput() {
        return input;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }
}
