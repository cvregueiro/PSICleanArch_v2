# 08 · Búsqueda de artistas (EditText + botón)

**Objetivo:** que el usuario escriba los términos de búsqueda en lugar de buscar siempre "rancid".

## Qué cambia

- **Layout.** `activity_main.xml` añade:
  - un `TextInputLayout`/`TextInputEditText` (Material 3) con `imeOptions="actionSearch"`;
  - un botón **Buscar**;
  - una vista vacía (`main_empty_view`) para los mensajes;
  - un indicador de progreso.
- **`MainActivity`:**
  - Valida el texto: si está vacío, muestra un error en el campo.
  - Oculta el teclado con `WindowInsetsControllerCompat`.
  - Lanza la búsqueda al pulsar el botón o la tecla de búsqueda del teclado.
  - Gestiona tres estados: cargando, lista y mensaje (sin resultados o error).
- **Consulta.** Se construye la consulta Lucene `artist:"texto"`, escapando `\` y `"`, para buscar el texto como frase.

## Para explicar

- **Estados de la UI.** Cargando, contenido, vacío y error se muestran y ocultan desde la actividad; en la fase MVP pasarán a ser métodos de la vista.
- **Validar la entrada antes de llamar a la red.** Se evita una petición inútil y se da una respuesta inmediata al usuario.

## Cómo probarlo

- Escribe "green day" y pulsa **Buscar** o la tecla de búsqueda del teclado.
- Pulsa **Buscar** con el campo vacío: aparece un error en el campo.
- Escribe un texto sin sentido: aparece "No hay resultados".

## Commits de esta rama

1. `c8a49ef` Textos de la búsqueda
1. `6671e6f` Layout con EditText y botón Buscar
1. `f7fc8f9` Búsqueda del texto escrito por el usuario
