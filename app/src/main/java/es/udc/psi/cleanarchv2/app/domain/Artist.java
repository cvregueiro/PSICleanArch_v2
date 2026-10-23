package es.udc.psi.cleanarchv2.app.domain;

public class Artist {

    private int id;
    private String name;

    public Artist(int id,
                  String name) {

        this.id = id;
        this.name = name;
    }

    public int getId() {

        return id;
    }

    public String getName() {

        return name;
    }
}
