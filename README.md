#Sistema de gestion de transporte
# 🚌 TransCesar S.A.S. — Sistema de Gestión de Tickets Intermunicipales

en este programa realizaremos un modelo que nos permita......
Sistema de consola desarrollado en Java para la empresa de transporte intermunicipal
TransCesar S.A.S., construido aplicando Programación Orientada a Objetos y
Arquitectura en Capas.

## 📋 Descripción

TransCesar es un sistema que moderniza la gestión operativa de una empresa de
transporte intermunicipal, eliminando el manejo manual de registros y reduciendo
errores en la operación diaria. Permite administrar vehículos, conductores y
pasajeros, gestionar la venta de tickets y ofrecer a los pasajeros la posibilidad
de reservar su cupo con anticipación.

El sistema fue construido de forma incremental, incorporando nuevas funcionalidades
sin afectar lo ya existente gracias a su arquitectura en capas.

### ¿Qué puede hacer el sistema?

- Registrar y gestionar vehículos de tres tipos: Buseta, MicroBus y Bus, cada uno
  con su propia capacidad y tarifa base.
- Registrar conductores con validación de licencia y pasajeros con descuentos
  automáticos según su perfil (Regular, Estudiante o Adulto Mayor).
- Vender tickets verificando disponibilidad de cupos, aplicando descuentos por
  tipo de pasajero y recargos en días festivos.
- Gestionar rutas formales con origen, destino, distancia y tiempo estimado.
- Permitir a los pasajeros reservar un cupo con anticipación, con expiración
  automática y conversión en ticket cuando se confirma el viaje.
- Generar reportes filtrados por fecha, tipo de vehículo, tipo de pasajero y
  resumen del día.
- Persistir toda la información en archivos de texto para que esté disponible
  entre sesiones.

## 🏗️ Arquitectura

El proyecto sigue una arquitectura en capas estricta. La comunicación fluye
en una única dirección:
```
view → service → dao → model
```

| Capa      | Responsabilidad                                      |
|-----------|------------------------------------------------------|
| `model`   | Entidades del negocio (clases e interfaces)          |
| `dao`     | Lectura y escritura en archivos de texto             |
| `service` | Reglas de negocio y validaciones                     |
| `view`    | Interfaz de consola e interacción con el usuario     |

## ⚙️ Tecnologías

- Java (JDK 11+)
- Archivos de texto plano para persistencia
- Git con flujo de ramas por funcionalidad (`feature/nombre-requerimiento`)
- Conventional Commits
