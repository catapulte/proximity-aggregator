package net.catapulte.proximity.model;

import lombok.Getter;

@Getter
public class CatCrossPath {

    private final String tic;
    private final String tac;

    public CatCrossPath(String tic, String tac) {
        this.tic = tic;
        this.tac = tac;
    }
}
