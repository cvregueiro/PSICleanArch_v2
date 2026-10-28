# 02 · Modelo Artist

**Objetivo:** dejar de trabajar con `String` y representar cada artista con una clase del modelo.

## Qué cambia

- Nueva clase `app.domain.Artist` con `id` y `name`.
- `MainActivity` construye una `List<Artist>`.
- `row_artist.xml` pasa a ser un `LinearLayout` vertical con dos `TextView`: el nombre y el id.
- `ArtistAdapter` trabaja con `Artist` y el ViewHolder tiene un método `bind(Artist)`.

## Para explicar

- **Separación entre datos y vista.** El adaptador recibe objetos del modelo y decide cómo pintarlos.
- **`bind()` en el ViewHolder.** Encapsula cómo se muestra una fila y deja `onBindViewHolder` en una sola línea.

## Cómo probarlo

Cada fila muestra el nombre del artista y, debajo, su id.

## Commits de esta rama

1. `f7a98eb` Modelo Artist
1. `646df1f` La fila muestra el id y el nombre del artista
