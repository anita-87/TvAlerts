package com.tvalerts.domains;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.parceler.Parcel;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Mapper class for the response of a Tv Show search in the TvMaze REST API.
 * This maps the shows performed via the "shows" path.
 * <p>
 * Uses the Parcel annotation to use the Parceler library to parcel the class.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Show {

    /**
     * The identification of the Tv show in the TvMaze REST API.
     */
    @Getter
    @Setter
    String id;
    /**
     * The URL of the show in the TvMaze REST API.
     */
    @Getter
    @Setter
    String url;
    /**
     * The name of the Tv show.
     */
    @Getter
    @Setter
    String name;
    /**
     * The type of the Tv show.
     */
    @Getter
    @Setter
    String type;
    /**
     * The language of the Tv show.
     */
    @Getter
    @Setter
    String language;
    /**
     * The genres of the Tv show.
     */
    @Getter
    @Setter
    String[] genres;
    /**
     * The image object of the Tv show.
     */
    @Getter
    @Setter
    Image image;
    /**
     * The status of the Tv show.
     * For example, Running, Ended, etc.
     */
    @Getter
    @Setter
    String status;
    /**
     * The date the show was premiered.
     */
    @JsonFormat(pattern = "yyyy-mm-dd")
    @Getter
    @Setter
    Date premiered;
    /**
     * Information about the network that broadcast the Tv show.
     */
    @Getter
    @Setter
    Network network;
    /**
     * The schedule of the show: day/s and time the show is/was aired.
     */
    @Getter
    @Setter
    Schedule schedule;
    /**
     * The summary of the Tv Show.
     */
    @Getter
    @Setter
    String summary;
    /**
     * The cast of the Tv Show.
     */
    @JsonProperty("_embedded")
    @Getter
    @Setter
    EmbeddedCast cast;

    /**
     * Default constructor for the Show class.
     */
    public Show() {
    }

    /**
     * Constructor for the Show class with thirteen parameters.
     *
     * @param id        - the identifier of the Tv Show.
     * @param url       - the URL link of the show in the TvMaze API.
     * @param name      - the name of the Tv show.
     * @param type      - the type of the Tv show.
     * @param language  - the language of the Tv show.
     * @param genres    - array with the genres of the Tv show.
     * @param image     - the image class that holds images of the Tv Show.
     * @param status    - the status of the Tv Show.
     * @param premiered - the date the show was premiered.
     * @param network   - the network class that holds information
     *                  about the Tv network of the Tv Show.
     * @param schedule  - the schedule of the show: day/s and time the show is/was aired.
     * @param summary   - a summary of the Tv Show.
     * @param cast      - the cast of the Tv Show.
     */
    public Show(String id, String url, String name, String type, String language,
                String[] genres, Image image, String status, Date premiered,
                Network network, Schedule schedule, String summary, EmbeddedCast cast) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.type = type;
        this.language = language;
        this.genres = genres;
        this.image = image;
        this.status = status;
        this.premiered = premiered;
        this.network = network;
        this.schedule = schedule;
        this.summary = summary;
        this.cast = cast;
    }
}
