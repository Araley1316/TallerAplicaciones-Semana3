# 🚚 DespachoExpress – Proyecto Android (Semana 9)

## 🧭 Introducción
El presente informe corresponde al desarrollo y validación del proyecto **DespachoExpress**, una aplicación móvil creada en **Android Studio** con el propósito de optimizar el proceso de cálculo y seguimiento de despachos.  
Su diseño se centra en ofrecer una herramienta práctica y eficiente que permita gestionar direcciones, coordenadas y confirmaciones de pedidos de manera automatizada, integrando cálculos geográficos y validaciones de navegación entre pantallas.

A lo largo del proyecto, se exponen las etapas técnicas que conforman la construcción del sistema, desde la implementación de las interfaces principales —como el inicio de sesión, el cálculo de despacho y el estado del pedido— hasta las pruebas de rendimiento, usabilidad y validación funcional.  
El desarrollo se orienta bajo principios de **programación estructurada y orientada a objetos (POO)**, priorizando la legibilidad del código, la modularidad y la estabilidad del sistema.

---

## ⚙️ Descripción del Proyecto
**DespachoExpress** es una aplicación desarrollada en **Kotlin** que permite calcular el costo y tiempo estimado de despacho de un pedido, a partir de la dirección de destino ingresada por el usuario.  
La aplicación utiliza la clase **Geocoder** para convertir direcciones en coordenadas geográficas (latitud y longitud), y emplea cálculos matemáticos y reglas comerciales para determinar el valor del envío.

El proyecto está compuesto por múltiples actividades conectadas de forma secuencial, que aseguran una navegación fluida entre módulos.

- **LoginActivity:** Control de acceso mediante correo electrónico y contraseña.  
- **MenuPrincipalActivity:** Pantalla central del sistema, desde donde se accede a los módulos principales.  
- **CalculoActivity:** Permite ingresar la dirección de destino, calcular las coordenadas y validar los datos antes de continuar.  
- **EstadoDespachoActivity:** Muestra la información del pedido, el estado del despacho y el mapa con las coordenadas obtenidas.  
- **TemperaturaActivity:** Control complementario que simula la lectura de temperatura del despacho.  
- **Botón “Salir de la App”:** Disponible únicamente en la pantalla de login para finalizar completamente la sesión.

---

## 🧩 Flujo de Navegación
El flujo de navegación fue diseñado para garantizar una experiencia intuitiva y eficiente.  
El recorrido general del usuario es el siguiente:

**Login → Menú Principal → Cálculo → Estado del Despacho → Login**

Una vez realizado el cálculo y confirmado el pedido, el sistema retorna automáticamente a la pantalla de inicio de sesión, cerrando el ciclo de operación.

---

## 🧱 Estructura del Proyecto
El proyecto está organizado en paquetes y recursos que facilitan el mantenimiento del código y la separación por responsabilidades:

- Carpeta **activities:** Contiene todas las clases principales de la aplicación (Login, Menú, Cálculo, Estado, Temperatura).  
- Carpeta **layout:** Incluye los archivos XML con la interfaz gráfica de cada actividad.  
- Carpeta **drawable** y **mipmap:** Contiene íconos, logos e imágenes utilizadas en la interfaz.  

Esta estructura modular permite la fácil integración de nuevas funciones y mantiene un orden lógico entre los componentes.

---

## 🧠 Funcionalidades Clave
- ✅ **Validación de entrada:** Control de campos vacíos y formato de dirección.  
- 🌍 **Conversión geográfica:** Uso de Geocoder para obtener coordenadas desde texto.  
- 🔄 **Gestión de estados:** Envío y recepción de datos entre actividades mediante Intents.  
- 🗺️ **Visualización dinámica:** Mapa con renderizado rápido en el emulador API 30.  
- 🔐 **Autenticación simulada:** Estructura base para futura conexión a Firebase.  
- 🎨 **Interfaz optimizada:** Diseño moderno con ícono de camión y navegación simplificada.  

---

## 🧪 Pruebas y Validaciones
Durante la etapa de pruebas, se ejecutaron distintos tipos de validación para asegurar la funcionalidad completa del sistema.

### 🔸 Prueba 1: Validación de Campos
- **Objetivo:** Comprobar que el sistema no permite continuar sin ingresar datos.  
- **Resultado:** Correcta validación de campos vacíos.  

### 🔸 Prueba 2: Geocodificación
- **Objetivo:** Confirmar que la dirección ingresada genera coordenadas válidas.  
- **Resultado:** Coordenadas obtenidas correctamente mediante Geocoder.  

### 🔸 Prueba 3: Navegación
- **Objetivo:** Verificar el traspaso de datos entre actividades.  
- **Resultado:** Flujo completo sin interrupciones ni pérdida de datos.  

### 🔸 Prueba 4: Cierre de Sesión
- **Objetivo:** Validar el retorno automático al login tras confirmar pedido.  
- **Resultado:** Retorno correcto al inicio de sesión, cumpliendo el flujo esperado.  

### 🔸 Prueba 5: Rendimiento
Esta prueba se enfocó en la observación del rendimiento general bajo condiciones de carga.  
Se realizaron simulaciones en el emulador de Android Studio reduciendo la memoria RAM y ejecutando múltiples procesos en segundo plano.  
La aplicación mantuvo estabilidad, con leves demoras en la carga inicial del mapa pero sin cierres inesperados.  
El consumo de CPU y RAM se mantuvo dentro de los parámetros normales, confirmando un rendimiento estable durante todo el flujo operativo.

---

## 🧰 Herramientas Utilizadas
- **Android Studio Flamingo (API 30)**  
- **Lenguaje de programación:** Kotlin  
- **Emulador de pruebas:** Pixel 5  
- **Herramientas de monitoreo:** Android Profiler y Logcat  
- **Control de versiones:** Git + GitHub  
- **Sistema operativo:** Windows 10  





