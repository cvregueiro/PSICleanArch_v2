package es.udc.psi.cleanarchv2.data.remote;

import java.io.IOException;
import java.util.List;

import es.udc.psi.cleanarchv2.data.remote.dto.SetListDto;

/**
 * Remote source of the setlists (gigs) of an artist.
 */
public interface GigRemoteDataSource {

    List<SetListDto> searchSetLists(String artistId) throws IOException;
}
