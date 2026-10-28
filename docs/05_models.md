# 05 · Modelos SearchArtists y Artist

**Objetivo:** que los modelos coincidan con el JSON que devuelve MusicBrainz.

```json
{ "count": 412, "artists": [ { "id": "24f8d8a5-...", "name": "Rancid", ... } ] }
```

## Qué cambia

- `SearchArtists` representa la respuesta completa: `count` y `artists`.
- `Artist.id` pasa a ser `String`, porque es un MBID (UUID), y se anota con `@SerializedName`.
- `MusicBrainzAPI` devuelve `Call<SearchArtists>` y la actividad usa `response.body().getArtists()`.

## Para explicar

- **Gson y los atributos.** Gson rellena los campos por nombre; `@SerializedName` desacopla el nombre Java del nombre en el JSON.
- **Por qué solo `id` y `name`.** No hace falta mapear todo el JSON: los campos que no se declaran se ignoran.

## Cómo probarlo

Al abrir la app aparece la lista real de artistas que coinciden con "rancid".

## Commits de esta rama

1. `9503046` Modelo SearchArtists con la respuesta de la búsqueda
1. `cbf1384` Artist con id String y @SerializedName
1. `cfa82ca` MusicBrainzAPI devuelve SearchArtists
