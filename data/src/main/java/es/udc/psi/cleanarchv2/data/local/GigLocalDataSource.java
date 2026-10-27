package es.udc.psi.cleanarchv2.data.local;

import java.io.IOException;
import java.util.List;

import es.udc.psi.cleanarchv2.data.remote.dto.SetListDto;

/**
 * Local copy of the setlists (gigs) of each artist, to show them without network.
 */
public interface GigLocalDataSource {

    List<SetListDto> getSetLists(String artistId) throws IOException;

    void saveSetLists(String artistId,
                      List<SetListDto> setLists) throws IOException;
}
