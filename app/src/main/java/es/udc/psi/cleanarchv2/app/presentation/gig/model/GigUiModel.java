package es.udc.psi.cleanarchv2.app.presentation.gig.model;

public class GigUiModel {

    private String date;
    private String venue;
    private String place;
    private String tour;

    public GigUiModel(String date,
                        String venue,
                        String place,
                        String tour) {

        this.date = date;
        this.venue = venue;
        this.place = place;
        this.tour = tour;
    }

    public String getDate() {

        return date;
    }

    public String getVenue() {

        return venue;
    }

    public String getPlace() {

        return place;
    }

    public String getTour() {

        return tour;
    }

    public boolean hasTour() {

        return tour != null && !tour.isEmpty();
    }
}
