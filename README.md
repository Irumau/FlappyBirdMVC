# 🐦 Flappy Bird Clone - MVC Strict Architecture

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-GUI-blue?style=for-the-badge)
![Architecture](https://img.shields.io/badge/Pattern-MVC%20Strict-green?style=for-the-badge)

Un clon del clásico **Flappy Bird** desarrollado en **Java (Swing)**. Este proyecto no es solo un juego, sino una implementación demostrativa de una **Arquitectura Modelo-Vista-Controlador (MVC) Estricta**, diseñada para garantizar el desacoplamiento total entre la lógica del juego y el renderizado gráfico.

## 🚀 Características del Juego

Más allá de la mecánica clásica, el juego incluye características avanzadas:

* **Sistema de Dificultad Dinámica:** La velocidad del mundo y la frecuencia de obstáculos aumentan a medida que sube el puntaje.
* **Modo Bonus (Portal):** Mecánica única donde el jugador puede entrar a un portal y acceder a una fase de bonificación sin obstáculos y con velocidad "Turbo".
* **Patrones de Monedas:** Algoritmos matemáticos (`CoinManager`) para generar monedas en patrones de Ola (Wave), ZigZag, Túnel y Aleatorio.
* **Sistema de HighScore:** Persistencia de datos local para guardar el mejor puntaje.
* **Renderizado Suave:** Uso de `RenderingHints` e interpolación bilineal para gráficos nítidos.

## 🏗️ Arquitectura y Diseño Técnico

El punto fuerte de este proyecto es su diseño de software. A diferencia de implementaciones básicas, este proyecto utiliza un enfoque **MVC Puro**:

### 1. Desacoplamiento Total (Strict MVC)
La **Vista** (`GameViewNew`) no conoce al **Modelo**. No existen referencias directas. La comunicación ocurre exclusivamente a través del Controlador.

### 2. Patrón DTO (Data Transfer Object)
Para enviar datos del Controlador a la Vista sin romper el encapsulamiento, se implementó la clase `GameFrameDTO`.
* El Controlador captura una "instantánea" del estado del juego (posición del pájaro, tubos, monedas, score) en cada frame.
* Empaqueta estos datos primitivos en el DTO.
* La Vista recibe el DTO y renderiza la imagen.
* **Resultado:** La Vista es "tonta" (pasiva) y el Modelo es puro (sin dependencias gráficas).

### 3. Managers y Delegación
La lógica no está aglomerada. Se divide en gestores específicos:
* `PipesManager`: Controla la generación y reciclaje de tubos.
* `CoinManager`: Controla los patrones de aparición de monedas y zonas de seguridad (Hitbox safety).
* `PortalManager`: Lógica de aparición y activación del modo Bonus.
* `SoundManager`: Gestión centralizada de efectos de sonido y música.
| Tecla / Acción | Función |
| :--- | :--- |
| **ESPACIO / Click Izq.** | Saltar / Iniciar Juego |
| **R** | Reiniciar (en pantalla de Game Over) |
| **M** | Volver al Menú (en pantalla de Game Over) |
## 🛠️ Instalación y Ejecución

### Requisitos
* Java Development Kit (JDK) 8 o superior.

### Pasos
1.  Clonar el repositorio:
    ```bash
    git clone [https://github.com/TU_USUARIO/TU_REPO.git](https://github.com/TU_USUARIO/TU_REPO.git)
    ```
2.  Abrir el proyecto en tu IDE favorito (NetBeans, IntelliJ, Eclipse).
3.  Ejecutar la clase principal: `com.mycompany.flappybird.FlappyBird`.
## 📸 Capturas de Pantalla
| Menú Principal | Gameplay | Modo Bonus |
| :---: | :---: | :---: |
![MENU]<img width="784" height="562" alt="image" src="https://github.com/user-attachments/assets/98443ccb-532f-4db5-8ffa-a24be16c2e17" /> | ![Game]<img width="782" height="562" alt="image" src="https://github.com/user-attachments/assets/ae43d21a-8993-4678-b6bc-d95853211bf0" /> | ![Bonus] <img width="787" height="563" alt="image" src="https://github.com/user-attachments/assets/4d795520-f5f4-4eb3-834d-2693610b1b10" />


**Mauricio** Estudiante de Desarrollo de Software
