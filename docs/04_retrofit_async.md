# 04 · Acceso a red asíncrono con Retrofit

**Objetivo:** hacer la petición fuera del hilo principal.

## Qué cambia

- `call.enqueue(new Callback<...>())`: Retrofit ejecuta la petición en un hilo de OkHttp y entrega `onResponse` / `onFailure` en el hilo principal de Android.
- El adaptador se crea vacío y recibe los datos más tarde con `setItems(...)` + `notifyDataSetChanged()`.
- Nuevo recurso `error_general`, que se muestra en un `Toast` si la petición falla.

## Para explicar

- **Diferencia entre `execute()` y `enqueue()`.** El primero bloquea el hilo que lo llama; el segundo no bloquea y devuelve el resultado en un callback.
- **Un dato asíncrono llega "más tarde".** La UI se pinta primero vacía y se actualiza cuando llega la respuesta.
- **El fallo esperado.** La respuesta sigue sin encajar con `List<Artist>`, así que salta `onFailure` y aparece el `Toast` de error. La siguiente feature crea el modelo correcto.

## Commits de esta rama

1. `ba87af4` Mensaje de error general
1. `331c74e` Llamada asíncrona con enqueue y adaptador con setItems
