# 06 · Logging de Retrofit

**Objetivo:** ver en Logcat cada petición y respuesta HTTP.

## Qué cambia

- Dependencia `com.squareup.okhttp3:logging-interceptor`.
- La actividad crea un `OkHttpClient` con un `HttpLoggingInterceptor` y se lo pasa a Retrofit con `.client(...)`.
- El nivel es `BODY` en las compilaciones *debug* y `NONE` en *release* (`BuildConfig.DEBUG`).

## Para explicar

- **Retrofit y OkHttp.** Retrofit delega el transporte en OkHttp; los *interceptors* de OkHttp ven y pueden modificar cada petición.
- **No registrar en producción.** En *release* no se registra nada: el log puede contener datos personales o credenciales.

## Cómo probarlo

Ejecuta la app y filtra Logcat por la etiqueta `OkHttp`: aparecen la URL, las cabeceras y el JSON de la respuesta.

## Commits de esta rama

1. `cd2a8fb` Dependencia de logging-interceptor de OkHttp
1. `2df49cb` HttpLoggingInterceptor en el cliente de Retrofit
