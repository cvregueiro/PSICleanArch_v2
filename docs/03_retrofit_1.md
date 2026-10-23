# 03 · Acceso a MusicBrainz con Retrofit (síncrono)

**Objetivo:** obtener los artistas de la API REST de [MusicBrainz](https://musicbrainz.org/doc/MusicBrainz_API) con Retrofit.

## Qué cambia

- Dependencias `retrofit`, `converter-gson` y `gson`. Se activa `buildFeatures.buildConfig` para poder usar `BuildConfig.VERSION_NAME`.
- Permiso `INTERNET` en el `AndroidManifest.xml`.
- `MusicBrainzAPI` declara la petición con anotaciones: `@GET("artist/")` con los parámetros `@Query` `query` y `fmt`. Lleva además una cabecera `@Headers` con un `User-Agent` propio, porque MusicBrainz rechaza peticiones sin un User-Agent identificable.
- `MainActivity` crea el `Retrofit` y ejecuta la llamada con `call.execute()`.

## El error que se busca mostrar

`execute()` es **síncrono** y se ejecuta en el hilo principal (UI). Android lo prohíbe y lanza `NetworkOnMainThreadException`: la app captura la excepción, la escribe en el log y muestra la lista vacía. Además, la respuesta de MusicBrainz es un objeto `{count, artists:[...]}` y no una lista, así que la interfaz todavía no encaja con el JSON.

- La siguiente feature resuelve el hilo con una llamada asíncrona.
- La feature 05 resuelve el modelo de la respuesta.

## Cómo probarlo

Ejecuta la app y busca en Logcat la etiqueta `MainActivity`: aparece `NetworkOnMainThreadException`.

## Commits de esta rama

1. `70888c6` Dependencias de Retrofit y Gson; se activa BuildConfig
1. `f378923` Permiso de acceso a Internet
1. `d54ef1f` Interfaz MusicBrainzAPI de Retrofit
1. `1d3779a` Llamada síncrona a MusicBrainz desde la actividad
