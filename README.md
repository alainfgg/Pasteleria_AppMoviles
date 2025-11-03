# Pastelería Mil Sabores (App. Moviles)
Aplicación móvil de e-commerce para la empresa "Pastelería Mil Sabores", desarrollada en Kotlin nativo con Jetpack Compose. Este proyecto se basa en la arquitectura MVVM (Model-View-ViewModel) para Android.

Sección: 001V

Docente: Vicente Zapata

## Integrantes

-Francisco Vásquez

-Ashley Vargas

-Alain González 


## Funcionalidades Implementadas
Este proyecto cumple con los requisitos de la Evaluación Parcial 2:

### Arquitectura y UI

Arquitectura MVVM: El proyecto está estructurado con una clara separación de responsabilidades e intercomunicación constante(View, ViewModel, Repository).


Sistema de Diseño: Se implementó un tema de Material 3 (Theme.kt) basado en la paleta de colores (Color.kt) y tipografía (Type.kt) definidas en las especificaciones del caso.


Navegación Funcional: Se utiliza Jetpack Navigation (AppNav) para gestionar el flujo entre todas las pantallas de la aplicación.

### Formularios y Lógica de Estado

Lógica Desacoplada: Toda la lógica de validación de los formularios de Login y Registro se maneja en sus respectivos ViewModels (LoginViewModel, RegistroViewModel), cumpliendo con el requisito de que la lógica no esté acoplada a la vista.

Formularios Validados:


Registro: Valida la longitud de la contraseña y el formato de email institucional (contraseña mayor a 6 caracteres y correo:@duoc.cl / @profesor.duoc.cl).


Login: Valida la longitud de la contraseña.


Pedidos: Valida que los campos de cantidad, dirección y personalización NO sean nulos.


Formulario de Personalización: La pantalla de producto permite personalizar las tortas con Tamaño (15, 20, 30 y 40 personas), Forma (Circular, Cuadrada) y un Mensaje opcional.

### Persistencia, Animaciones y Recursos Nativos
Persistencia Local (Room): La lista de "Pedidos realizados" se guarda en una base de datos Room local. La app guarda los pedidos y los recarga al reiniciar, cumpliendo así con el requisito de persistencia.

Animaciones Funcionales: Se implementó Modifier.animateItem() en la LazyColumn de pedidos. Los nuevos pedidos se añaden al inicio de la lista y los ítems existentes se desplazan con una animación.

Recursos Nativos:


Cámara: La pantalla de formulario de pedido accede a la cámara del dispositivo para adjuntar una foto de referencia.


Compartir: La pantalla de Contacto (Info) utiliza el Intent nativo de Android para compartir la información de contacto de la pastelería.


### ** Nota Importante (Animaciones): Si las animaciones de la lista de pedidos no se visualizan, verificar en el dispositivo/emulador que las "Opciones de desarrollador" tengan las escalas de animación (ventana, transición, animador) configuradas en 1x y no en "desactivada"
