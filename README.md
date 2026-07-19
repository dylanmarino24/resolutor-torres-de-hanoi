# Resolutor de Torres de Hanoi (Grafo Explicito Y BFS)

Este proyecto es una implementación en Java que resuelve el clásico juego de las Torres de Hanoi encontrando la ruta más corta posible.

## Estructura y Algoritmos
* **Arquitectura MVC:** El código está estrictamente dividido en Modelo, Vista y Controlador.
* **Teoría de Grafos:** El modelo construye un grafo simple Explícito con lista de Adyacencia donde cada nodo es un estado válido del juego y las aristas son los movimientos legales permitidos.
* **Algoritmo BFS:** Se aplica Búsqueda en Anchura (Breadth-First Search) para garantizar que la solución mostrada utiliza el menor número de movimientos posibles.
* **Interfaz Gráfica:** Utiliza Java Swing para la entrada de datos y el renderizado 2D de las torres, permitiendo avanzar o retroceder paso a paso.

## Como ejecutar
1. Clona este repositorio: `git clone https://github.com/dylanmarino24/resolutor-torres-de-hanoi.git`
2. Ve a la carpeta `src`.
3. Compila y ejecuta el archivo `Main.java`.

## Guia de Uso y Funcionamiento

Al ejecutar el programa, se abrirá una interfaz gráfica donde podrás configurar los parámetros del juego. Para entender cómo ingresar los datos, debes comprender cómo el sistema lee las posiciones.

### Cómo se representan los estados (Ejemplo con 3 discos)
Cada estado del juego se escribe como una combinación de letras (A, B o C) que representan los tres postes. El orden de las letras se lee de izquierda a derecha, correspondiendo al tamaño de los discos **desde el más pequeño hasta el más grande**:

* **AAA:** Significa que el disco pequeño está en A, el mediano está en A y el grande está en A. Es decir, todos los discos están apilados en el poste A (posición inicial clásica).
* **CCC:** Significa que todos los discos están apilados en el poste C (posición final clásica).
* **CBA:** Significa que el disco más pequeño está en el poste C, el mediano en el poste B y el más grande en el poste A.

Nota: La cantidad de letras debe coincidir exactamente con el número de discos seleccionado. Si seleccionas 5 discos, un estado válido sería `AAAAA` o `CCCCC`.

### Pasos para probar el programa

1. **Seleccionar discos:** En el control numérico, elige la cantidad de discos con los que deseas realizar la simulación (se recomienda probar con 3 o 4 para empezar).
2. **Definir Estado Inicial:** En la casilla de texto "Inicio", ingresa la configuración inicial de los discos (por ejemplo, `AAA`).
3. **Definir Estado Objetivo:** En la casilla de texto "Objetivo", ingresa la configuración a la que deseas llegar (por ejemplo, `CCC`).
4. **Procesar la solución:** Haz clic en el botón "Resolver". En ese momento, el programa construirá el grafo completo en memoria, buscará la ruta óptima y dibujará el estado inicial en el panel central.
5. **Simulación interactiva:** Utiliza los botones "Anterior" y "Siguiente" ubicados en la parte inferior para avanzar o retroceder en la secuencia de movimientos. El panel actualizará los gráficos en tiempo real mostrando el desplazamiento físico de los discos entre los postes.
