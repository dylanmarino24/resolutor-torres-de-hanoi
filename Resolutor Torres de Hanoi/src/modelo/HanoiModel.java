package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class HanoiModel {

    // ESTRUCTURA EXPLÍCITA DEL GRAFO: Lista de adyacencia 
    private Map<String, List<String>> listaAdyacencia;

    public HanoiModel() {
        this.listaAdyacencia = new HashMap<>();
    }

    public List<String> encontrarCaminoMasCorto(String inicio, String objetivo, int numDiscos) {
        //Construir todo el grafo en memoria ANTES de hacer la búsqueda
        construirGrafoCompleto(numDiscos);

        //Verificar que los estados ingresados existan en el grafo
        if (!listaAdyacencia.containsKey(inicio) || !listaAdyacencia.containsKey(objetivo)) {
            return Collections.emptyList();
        }

        //Aplicar Búsqueda en Anchura (BFS) usando la lista de adyacencia explícita
        Queue<String> cola = new LinkedList<>();
        Map<String, String> padres = new HashMap<>(); 
        
        cola.add(inicio);
        padres.put(inicio, null);
        
        while (!cola.isEmpty()) {
            String actual = cola.poll();
            
            if (actual.equals(objetivo)) {
                return reconstruirCamino(padres, objetivo);
            }
            
            // Consultamos los vecinos directamente desde la estructura del grafo
            List<String> vecinos = listaAdyacencia.get(actual);
            
            for (String vecino : vecinos) {
                if (!padres.containsKey(vecino)) { 
                    padres.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }
        
        return Collections.emptyList(); 
    }

    /**
     * construye el grafo explícito generando todos los nodos posibles 
     * y calculando todas las aristas válidas entre ellos.
     */
    private void construirGrafoCompleto(int numDiscos) {
        listaAdyacencia.clear(); // Limpiamos el grafo por si se cambio el número de discos
        
        // a) Generar todos los vértices posibles
        List<String> todosLosNodos = generarTodosLosEstados(numDiscos);
        for (String nodo : todosLosNodos) {
            agregarNodo(nodo);
        }
        
        // b) Generar todas las aristas  iterando sobre los vértices creados
        for (String nodo : todosLosNodos) {
            List<String> vecinosValidos = obtenerMovimientosValidosFisicamente(nodo, numDiscos);
            for (String vecino : vecinosValidos) {
                agregarArista(nodo, vecino);
            }
        }
    }

    // --- MÉTODOS CLÁSICOS DE LA ESTRUCTURA GRAFO ---

    private void agregarNodo(String nodo) {
        listaAdyacencia.putIfAbsent(nodo, new ArrayList<>());
    }

    private void agregarArista(String origen, String destino) {
        // Al ser un grafo no dirigido, el ciclo principal se encarga de 
        // agregar destino->origen cuando evalúa el nodo de destino.
        listaAdyacencia.get(origen).add(destino);
    }

    // --- MÉTODOS AUXILIARES ---

    /**
     * Genera recursivamente todas las combinaciones posibles de estados.
     * Ejemplo para 2 discos: "AA", "AB", "AC", "BA", "BB", ... "CC"
     */
    private List<String> generarTodosLosEstados(int numDiscos) {
        List<String> estados = new ArrayList<>();
        generarCombinaciones(numDiscos, "", estados);
        return estados;
    }

    private void generarCombinaciones(int n, String actual, List<String> estados) {
        if (actual.length() == n) {
            estados.add(actual);
            return;
        }
        generarCombinaciones(n, actual + "A", estados);
        generarCombinaciones(n, actual + "B", estados);
        generarCombinaciones(n, actual + "C", estados);
    }

    /**
     * Lógica física del juego para saber a dónde se pueden mover los discos 
     * desde un estado específico. (Usado para crear las aristas).
     */
    private List<String> obtenerMovimientosValidosFisicamente(String estado, int numDiscos) {
        List<String> vecinos = new ArrayList<>();
        int[] discoSuperior = new int[3]; 
        Arrays.fill(discoSuperior, -1);   
        
        for (int i = 0; i < numDiscos; i++) {
            int poste = estado.charAt(i) - 'A';
            if (discoSuperior[poste] == -1) {
                discoSuperior[poste] = i;
            }
        }
        
        for (int posteOrigen = 0; posteOrigen < 3; posteOrigen++) {
            int discoMoviendose = discoSuperior[posteOrigen];
            if (discoMoviendose == -1) continue; 
            
            for (int posteDestino = 0; posteDestino < 3; posteDestino++) {
                if (posteOrigen == posteDestino) continue; 
                
                int discoDestinoSuperior = discoSuperior[posteDestino];
                
                if (discoDestinoSuperior == -1 || discoMoviendose < discoDestinoSuperior) {
                    char[] nuevoEstado = estado.toCharArray();
                    nuevoEstado[discoMoviendose] = (char) ('A' + posteDestino);
                    vecinos.add(new String(nuevoEstado));
                }
            }
        }
        
        return vecinos;
    }

    private List<String> reconstruirCamino(Map<String, String> padres, String objetivo) {
        List<String> camino = new ArrayList<>();
        String actual = objetivo;
        
        while (actual != null) {
            camino.add(actual);
            actual = padres.get(actual);
        }
        
        Collections.reverse(camino); 
        return camino;
    }
}