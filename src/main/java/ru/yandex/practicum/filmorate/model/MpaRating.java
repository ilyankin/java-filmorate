package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MpaRating {
    G("G"), // General audiences – All ages admitted
    PG("PG"), // Parental guidance suggested – Some material may not be suitable for children.
    PG_13("PG-13"), // Parents strongly cautioned – Some material may be inappropriate for children under 13.
    R("R"), // Restricted – Under 17 requires accompanying parent or adult guardian.
    NC_17("NC-17"); // Adults Only – No one 17 and under admitted.

    private final String name;

    MpaRating(String name) {
        this.name = name;
    }

    @JsonCreator
    public static MpaRating forValue(@JsonProperty("id") int id) {
        return MpaRating.values()[id - 1];
    }

    public static MpaRating nameOf(String name) {
        for (MpaRating mpa : MpaRating.values()) {
            if (mpa.name.equals(name)) return mpa;
        }
        return null;
    }

    public int getId() {
        return ordinal() + 1;
    }

    public String getName() {
        return name;
    }
}
