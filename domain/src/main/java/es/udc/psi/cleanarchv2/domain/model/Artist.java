package es.udc.psi.cleanarchv2.domain.model;

public class Artist {

    private String id;
    private String name;

    public Artist(String id,
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
