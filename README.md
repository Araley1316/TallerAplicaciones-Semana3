# Aplicación de Cálculo de Despacho - Taller de Aplicaciones Semana 3

Este proyecto corresponde al desarrollo de una aplicación en lenguaje Java que permite calcular el costo de despacho de una distribuidora de alimentos, según el monto de la compra y la distancia al domicilio del cliente. La ejecución se realiza desde la línea de comandos (cmd), sin el uso de entorno IDE.

## Caso de estudio

Una distribuidora ha implementado un servicio de despacho a domicilio para sus clientes. El costo del despacho depende del monto de la compra y la distancia en kilómetros. Este sistema permite automatizar el cálculo según las siguientes reglas:

- Si el total de la compra es mayor o igual a $50.000 y la distancia es menor o igual a 20 km → despacho **gratuito**.
- Si el total de la compra está entre $25.000 y $49.999 → se cobra **$150 por km**.
- Si el total de la compra es menor a $25.000 → se cobra **$300 por km**.

## Requerimientos funcionales

- Permitir el ingreso de los siguientes datos:
  - Nombre
  - Edad
  - Marca
  - Modelo
  - Cilindrada
  - Tipo de combustible
  - Capacidad de pasajeros
- Permitir el ingreso del monto total de la compra y la distancia en kilómetros.
- Calcular automáticamente el costo de despacho según las reglas del negocio.
- Calcular el total a pagar (compra + despacho).
- Mostrar los resultados en pantalla de forma ordenada.
- Ejecutar el programa desde la línea de comandos sin IDE.

## Requerimientos no funcionales

- Portabilidad: ejecutable en cualquier sistema operativo con Java instalado.
- Simplicidad: código legible y documentado línea por línea.
- Seguridad básica: aceptación de datos válidos.
- Control de versiones mediante Git y GitHub.
- Documentación del proceso completo en el repositorio.
- Accesibilidad del código para revisión y evaluación.

## Historias de Usuario

### Historia de Usuario 1: Registro de vehículo
**Como** usuario de la aplicación,  
**quiero** ingresar los datos de mi vehículo (marca, modelo, cilindrada, tipo de combustible y capacidad),  
**para** que la aplicación los muestre como parte de los datos de entrega.

### Historia de Usuario 2: Cálculo de despacho
**Como** usuario de la aplicación,  
**quiero** ingresar el monto total de mi compra y la distancia a mi domicilio,  
**para** que la aplicación calcule automáticamente el costo de despacho según las reglas del negocio.

### Historia de Usuario 3: Visualización de resultados
**Como** usuario,  
**quiero** ver en pantalla los datos que ingresé y el total a pagar (compra + despacho),  
**para** confirmar que la información sea correcta antes de finalizar la operación.

### Historia de Usuario 4: Uso sin entorno gráfico
**Como** usuario con conocimientos básicos,  
**quiero** poder ejecutar el programa desde la línea de comandos sin depender de un entorno gráfico,  
**para** facilitar el uso en distintos sistemas y contextos.

### Historia de Usuario 5: Acceso al proyecto y documentación
**Como** desarrollador o evaluador,  
**quiero** acceder al repositorio con el código y documentación clara,  
**para** revisar o reutilizar el proyecto fácilmente.

## Ejecución

1. Abrir consola o terminal.
2. Navegar al directorio del archivo `.java`.
3. Compilar el archivo:
javac Despacho.java
4. Ejecutar el programa:
java Despacho

## Ejemplo de salida

Ingrese su Nombre: Romina Gómez
Ingrese su edad: 42
Ingrese la marca del vehículo: Toyota
Ingrese el modelo del vehículo: Hilux
Ingrese la cilindrada del vehículo: 2.8
Ingrese el tipo de combustible: Diésel
Ingrese la capacidad de pasajeros: 5
Ingrese el total de la compra: 47000
Ingrese la distancia en kilómetros desde el centro a su casa: 10

---DATOS DEL USUARIO---
Nombre: Romina Gómez
Edad: 42
--- DATOS DEL VEHÍCULO ---
Marca: Toyota
Modelo: Hilux
Cilindrada: 2.8
Tipo de Combustible: Diésel
Capacidad de Pasajeros: 5
--- DATOS DE LA COMPRA ---
Monto de la compra: $47000
Distancia del despacho: 10 km
Costo del despacho: $1500
Total a pagar (compra + despacho): $48500

## Cronograma Inicial

| Etapa                            | Fecha estimada | Estado      |
|----------------------------------|----------------|-------------|
| Análisis del caso propuesto      | 30/08/2025     | ✅ Completo |
| Diseño del algoritmo             | 30/08/2025     | ✅ Completo |
| Creación del código en Bloc de notas | 31/08/2025 | ✅ Completo |
| Compilación y pruebas en consola | 31/08/2025     | ✅ Completo |
| Creación de repositorio en GitHub| 01/09/2025     | ✅ Completo |
| Documentación en README.md       | 01/09/2025     | 🟡 En curso |

Revisa el cronograma visual y actualizado en la pestaña **Projects** del repositorio.






