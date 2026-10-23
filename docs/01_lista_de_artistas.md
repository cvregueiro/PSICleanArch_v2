# 01 · Lista de artistas

**Objetivo:** mostrar una lista fija de artistas con un `RecyclerView`, accediendo a las vistas con **ViewBinding**.

## Qué cambia

- `build.gradle.kts` activa `buildFeatures.viewBinding` y añade `androidx.recyclerview`. Las versiones se declaran en `gradle/libs.versions.toml` (version catalog).
- `BaseActivity<B extends ViewBinding>` infla el binding, llama a `setContentView(mBinding.getRoot())`, configura la toolbar y aplica los *insets* de las barras del sistema (edge-to-edge, obligatorio desde Android 15).
- `MainActivity` pasa al paquete `app.presentation.main` y rellena el `RecyclerView` con una lista de `String`.
- `ArtistAdapter` crea cada fila con `RowArtistBinding.inflate(...)`, de modo que el ViewHolder guarda el binding y no usa `findViewById`.

## Por qué ViewBinding y no ButterKnife

ButterKnife está obsoleto: usaba un procesador de anotaciones (`@BindView`) y obligaba a hacer `bind`/`unbind`. ViewBinding lo genera el plugin de Android:

- Crea una clase por layout (`activity_main.xml` → `ActivityMainBinding`).
- Es *null-safe* y *type-safe*.
- No necesita librerías externas ni una clase `Application` para configurarlo. Por eso desaparece `GlobalApplication`.

## Cómo probarlo

Ejecuta la app: aparece la lista de seis artistas bajo la toolbar.

## Commits de esta rama

1. `17da192` Esquema de estilo de código del proyecto
1. `80664b1` Dependencias: RecyclerView y ViewBinding (sustituye a ButterKnife)
1. `378288b` BaseActivity genérica con ViewBinding y toolbar
1. `5e66ec7` Lista de artistas con RecyclerView y adaptador
