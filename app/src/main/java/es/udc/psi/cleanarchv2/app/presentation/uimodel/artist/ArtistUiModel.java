package es.udc.psi.cleanarchv2.app.presentation.uimodel.artist;

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
