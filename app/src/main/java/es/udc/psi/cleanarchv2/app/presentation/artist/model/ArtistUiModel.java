package es.udc.psi.cleanarchv2.app.presentation.artist.model;

public class ArtistUiModel {

    private String id;
    private String name;

    public ArtistUiModel(String id,
                           String name) {

        this.id = id;
        this.name = name;
    }

    public String getId() {

        return id;
    }

    public String getName() {

        return name;
    }
}
