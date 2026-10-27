package es.udc.psi.cleanarchv2.app.domain.gig.service;

import java.io.IOException;
import java.util.List;

import es.udc.psi.cleanarchv2.app.domain.model.Gig;

public interface GigService {

    List<Gig> searchGigs(String artistId) throws IOException;
}
