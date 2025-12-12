# Pastelería Mil Sabores 
Aplicación móvil de e-commerce desarrollada para la transformación digital de "Pastelería Mil Sabores" en su 50º aniversario.

## Integrantes:

- Alain González

- Francisco Vásquez

- Ashley Vargas
  
Asignatura: Desarrollo de Aplicaciones Móvil 001V

Docente: Vicente Zapata


## Funcionalidades Implementadas

El proyecto para la Evaluación Final Transversal está construido siguiendo estrictos estándares de calidad, utilizando Arquitectura MVVM, persistencia de datos local y consumo de APIs REST.

## Funcionalidades Implementadas Claves
- Gestión de Usuarios: Registro con validación de correos institucionales (@duoc.cl) y contraseñas seguras.

- Carrito Inteligente: Cálculo automático de precios según tamaño y forma de la torta.

- Lógica de Descuentos: Aplicación automática de beneficios (50% OFF para mayores de 50 años).

- Escáner QR: Integración nativa con la cámara para consulta rápida de productos.

- Social Sharing: Funcionalidad para compartir productos favoritos mediante Intents nativos.

- Modo Offline: Persistencia de datos (carrito y sesión) mediante base de datos local (Room).

## Stack Tecnológico
- Lenguaje: Kotlin

- UI Toolkit: Jetpack Compose (Material Design 3)

- Arquitectura: MVVM (Model-View-ViewModel)

- Base de Datos Local: Room Database (SQLite abstraction)

- Networking: Retrofit + GSON

- Asincronía: Coroutines & Flows

- Testing: JUnit & Mockk

## Endpoints y Consumo de API
- a. API de Recetas (TheMealDB)
Utilizada para poblar el módulo de "Ideas de Postres" con contenido dinámico.

Base URL: https://www.themealdb.com/api/json/v1/1/

Endpoints:

GET /filter.php?c=Dessert: Obtiene el listado filtrado de postres.

- b. Microservicio de Contenido (Placeholder/Custom)
Utilizada para la gestión de publicaciones y noticias de la pastelería.

Base URL: https://jsonplaceholder.typicode.com

Endpoints:

GET /post: Recupera el listado de novedades y posts informativos.

## Estructura del Proyecto
El código sigue una estructura de paquetes basada en responsabilidades (Clean Architecture approach):

com.example.pasteleria
├── data
│   ├── dao          # Data Access Objects (Room)
│   ├── database     # Configuración de Room (AppDatabase)
│   ├── model        # Data Classes (Producto, Usuario, CartItem)
│   └── remote       # Interfaces de Retrofit y Endpoints
├── view             # Pantallas (Screens) en Jetpack Compose
│   ├── auth         # Login y Registro
│   ├── home         # Catálogo y Ofertas
│   ├── cart         # Carrito de Compras
│   └── common       # Componentes reutilizables
└── viewmodel        # Lógica de negocio y StateHolders

## Testing y Calidad
El proyecto incluye una suite de pruebas unitarias para validar la lógica crítica de negocio:

- AuthRepositoryTest: Valida la lógica de autenticación simulando respuestas de base de datos (Mocking).

- LoginViewModelTest: Asegura que el estado de la UI (LoginUiState) reaccione correctamente a la entrada del usuario.

- UI Tests: Verificación de renderizado de componentes críticos en LoginScreen.

## Instalación
Clonar el repositorio en Android Studio:
- git clone https://github.com/tu-usuario/pasteleria-app-moviles.git

- Sincronizar el proyecto con Gradle.

- Ejecutar en un Emulador (Min SDK 24) o Dispositivo Físico.

** Nota Importante (Animaciones): Si las animaciones de la lista de pedidos no se visualizan, verificar en el dispositivo/emulador que las "Opciones de desarrollador" tengan las escalas de animación (ventana, transición, animador) configuradas en 1x y no en "desactivada" **
