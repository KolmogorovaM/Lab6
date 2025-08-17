package ru.marina.base;

import ru.marina.model.MusicBand;
import ru.marina.model.Status;
import java.io.Serializable;

public class Response implements Serializable {
    private final Status status;
    private final String message;
    private MusicBand musicBand;

    public Response(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response(Status status, String message, MusicBand musicBand) {
        this.status = status;
        this.message = message;
        this.musicBand = musicBand;
    }

    public Status getStatus() {
        return status;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }

    public String getMessage() {
        return message;
    }
}
