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

- `master`: versiones publicadas. Cada versión es un *merge* de una rama `release/x.y` con su etiqueta `vx.y`.
- `develop`: integración de todas las features, con un *merge* `--no-ff` por feature. Es la rama por defecto.
- `feature/NN_*`: cada paso de la evolución, en varios commits pequeños y con su explicación en `docs/`.
- `release/x.y`: preparación de una versión. Sale de `develop` y se fusiona en `master` y en `develop`.

| Versión | Rama | Contenido |
|---------|------|-----------|
| [`v1.0`](docs/release_1.0.md) | `release/1.0` | Features 01 a 16: de la aplicación monolítica a la Clean Architecture con módulos |

Para ver los cambios de una feature: `git log --oneline develop..feature/NN_nombre` o `git log -p feature/NN_nombre`.

| Rama | Fase | Contenido |
|------|------|-----------|
| [`feature/01_lista_de_artistas`](docs/01_lista_de_artistas.md) | Monolítica | RecyclerView y ViewBinding (sustituye a ButterKnife) |
| [`feature/02_model_artist`](docs/02_model_artist.md) | Monolítica | Modelo `Artist` |
| [`feature/03_retrofit_1`](docs/03_retrofit_1.md) | Monolítica | Retrofit síncrono (y por qué falla en el hilo principal) |
| [`feature/04_retrofit_async`](docs/04_retrofit_async.md) | Monolítica | Retrofit asíncrono con `enqueue` |
| [`feature/05_models`](docs/05_models.md) | Monolítica | Modelos que encajan con el JSON de MusicBrainz |
| [`feature/06_retrofit_logging`](docs/06_retrofit_logging.md) | Monolítica | Logging HTTP con OkHttp |
| [`feature/07_retrofit_headers`](docs/07_retrofit_headers.md) | Monolítica | Interceptores y cabeceras; API key de setlist.fm |
| [`feature/08_search_query`](docs/08_search_query.md) | Monolítica | Búsqueda con EditText y botón |
| [`feature/09_artist_gigs`](docs/09_artist_gigs.md) | Monolítica | Segunda actividad con los bolos del artista |
| [`feature/10_MVP`](docs/10_MVP.md) | Clean | Model-View-Presenter |
| [`feature/11_MVVP`](docs/11_MVVP.md) | Clean | Modelos de vista y mappers |
| [`feature/12_services`](docs/12_services.md) | Clean | Dominio: entidades, servicios y `TaskRunner` |
| [`feature/13_datasources`](docs/13_datasources.md) | Clean | Datasource de artistas |
| [`feature/14_clean_architecture`](docs/14_clean_architecture.md) | Clean | Clean Architecture en un módulo: paquetes `presentation`/`domain`/`data`, casos de uso, repositorios, `AppContainer` y tests |
| [`feature/15_remote_local`](docs/15_remote_local.md) | Clean | Fuentes de datos remota y local: con red consulta y guarda en ficheros; sin red lee lo guardado |
| [`feature/16_modules`](docs/16_modules.md) | Clean | Módulos `:domain`/`:data`/`:app`: el compilador hace cumplir la regla de dependencias; [esquema en cebolla](docs/arquitectura_cebolla.md) |
