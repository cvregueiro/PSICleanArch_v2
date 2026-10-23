# PSiCleanArch

Aplicación Android de ejemplo para la asignatura PSI. Evoluciona paso a paso desde una aplicación **monolítica** hasta una **arquitectura en capas (Clean)**. Busca artistas en [MusicBrainz](https://musicbrainz.org) y muestra sus conciertos con [setlist.fm](https://www.setlist.fm).

Este proyecto es la continuación de un proyecto previo, disponible en <https://gitlab.com/cvregueiro/psi-clean-arch>.

## Tecnologías

- Android Studio 2026.1, Android Gradle Plugin 9.4, Gradle 9.8 (Kotlin DSL y *version catalog*).
- Java 17, `compileSdk`/`targetSdk` 37, `minSdk` 24.
- ViewBinding, Material 3, RecyclerView, Retrofit 3, OkHttp 5 y Gson.

## Configuración

La API de setlist.fm necesita una clave personal. Pídela en <https://www.setlist.fm/settings/api> y añádela a `local.properties`. Ese fichero no se sube nunca al repositorio:

```properties
setlistfm.apikey=TU_API_KEY
```

## Estructura de ramas (gitflow)

- `master`: proyecto inicial (plantilla de Android Studio).
- `develop`: integración de todas las features, con un *merge* `--no-ff` por feature.
- `feature/NN_*`: cada paso de la evolución, en varios commits pequeños y con su explicación en `docs/`.

Para ver los cambios de una feature: `git log --oneline develop..feature/NN_nombre` o `git log -p feature/NN_nombre`.

| Rama | Fase | Contenido |
|------|------|-----------|
| [`feature/01_lista_de_artistas`](docs/01_lista_de_artistas.md) | Monolítica | RecyclerView y ViewBinding (sustituye a ButterKnife) |
| [`feature/02_model_artist`](docs/02_model_artist.md) | Monolítica | Modelo `Artist` |
| [`feature/03_retrofit_1`](docs/03_retrofit_1.md) | Monolítica | Retrofit síncrono (y por qué falla en el hilo principal) |
| [`feature/04_retrofit_async`](docs/04_retrofit_async.md) | Monolítica | Retrofit asíncrono con `enqueue` |
| [`feature/05_models`](docs/05_models.md) | Monolítica | Modelos que encajan con el JSON de MusicBrainz |
| [`feature/06_retrofit_logging`](docs/06_retrofit_logging.md) | Monolítica | Logging HTTP con OkHttp |
