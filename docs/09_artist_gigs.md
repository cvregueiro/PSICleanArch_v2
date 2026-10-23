# 09 · Bolos (gigs) de un artista en una segunda actividad

**Objetivo:** al pulsar un artista, abrir una segunda pantalla con todos sus conciertos, obtenidos de setlist.fm con la API y el interceptor de la feature 07.

## Qué cambia

- **Modelos.** `SetList` se amplía con `id`, `venue` y `tour`. Se añaden `Venue`, `City`, `Country` y `Tour`, que reproducen el JSON anidado de setlist.fm.
- **`GigsActivity`.**
  - Recibe el id (MBID) y el nombre del artista en el `Intent`, mediante un método de factoría `newIntent(...)`.
  - Muestra el nombre en la toolbar con la flecha de volver.
  - Pide `search/setlists?artistMbid=...` con `SetListFmApiInterceptor`.
- **`GigAdapter` y `row_gig.xml`.** Muestran fecha, sala, ciudad/país y gira.
- **`ArtistAdapter.OnArtistClickListener`.** El adaptador avisa a la actividad del artista pulsado y `MainActivity` lanza `GigsActivity`.
- **Casos especiales.**
  - setlist.fm responde **404** cuando el artista no tiene conciertos: se trata como "sin resultados".
  - Si falta la API key en `local.properties`, se muestra un aviso en lugar de hacer la petición.
- **Seguridad.** El `HttpLoggingInterceptor` oculta la cabecera `x-api-key` con `redactHeader`.

## Para explicar

- **Navegar entre actividades.** Se pasan datos con *extras*, y el método `newIntent` documenta qué necesita la actividad.
- **Comunicar adaptador y actividad con una interfaz** (*listener*), sin que el adaptador conozca la actividad.
- **Estado de partida para la fase Clean.** La actividad hace de todo: vista, red y transformación de datos. Es la versión **monolítica** que se refactoriza a partir de la siguiente feature.

## Cómo probarlo

Busca un artista (por ejemplo, Rancid) y pulsa sobre él: se abre la lista de sus conciertos más recientes (primera página de setlist.fm, 20 resultados).

## Commits de esta rama

1. `e31e317` Modelos de setlist.fm: Venue, City, Country y Tour
1. `a17c216` Pantalla de bolos: GigsActivity con su adaptador
1. `10c3347` Al pulsar un artista se abre la pantalla de sus bolos
