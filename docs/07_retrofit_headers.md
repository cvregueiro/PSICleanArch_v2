# 07 · Interceptores y cabeceras HTTP

**Objetivo:** añadir cabeceras comunes a todas las peticiones de una API con un interceptor de OkHttp, y preparar el acceso a [setlist.fm](https://api.setlist.fm/docs/1.0/index.html).

## Qué cambia

- **API key.** `build.gradle.kts` lee la clave `setlistfm.apikey` de `local.properties` y la expone como `BuildConfig.SETLISTFM_API_KEY`. Como `local.properties` está en `.gitignore`, **la clave nunca entra en el repositorio**.
- **`MusicBrainzApiInterceptor`.** Añade `User-Agent` y `Accept: application/json` a todas las peticiones a MusicBrainz, y sustituye a la anotación `@Headers` de la interfaz.
- **Modelos de setlist.fm.** `SetList` (con la fecha del concierto) y `SetLists` (la respuesta, con la lista `setlist`).
- **`SetListFmAPI` y `SetListFmApiInterceptor`.** La API de setlist.fm exige la cabecera `x-api-key` en cada petición. Se usan en la feature 09.

## Para explicar

- **Anotación o interceptor.** `@Headers` sirve para una petición; un interceptor sirve para todas las de un cliente.
- **Secretos fuera del control de versiones.** Ni las claves ni las contraseñas se escriben en el código ni en git.

## Configuración

Pide una API key en <https://www.setlist.fm/settings/api> y añádela a `local.properties`:

```properties
setlistfm.apikey=TU_API_KEY
```

## Commits de esta rama

1. `9cf19a7` API key de setlist.fm leída de local.properties
1. `0f7f71f` Interceptor con las cabeceras de MusicBrainz
1. `eac34dd` Modelos SetList y SetLists de setlist.fm
1. `7ef2eee` API de setlist.fm con su interceptor (x-api-key)
