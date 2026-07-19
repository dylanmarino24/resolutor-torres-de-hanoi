# Resolutor de Torres de Hanoi (Grafo Explicito + BFS)

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
