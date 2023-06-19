package Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class main {
	
	// Classe soilSample
	public static class soilSample {
		int id;
		float ph;
		int umidade;
	}
	
	public static final int ID_MIN = 4600;
	public static final int ID_MAX = 4800 - ID_MIN;
	public static final int PH_MIN = 40;
	public static final int PH_MAX = 81 - PH_MIN;
	public static final int UMIDADE_MIN = 20;
	public static final int UMIDADE_MAX = 91 - UMIDADE_MIN;

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Digite a quantidade de sensores instalados: ");
		int quantidadeSensores = sc.nextInt();
		while(quantidadeSensores <= 0 || quantidadeSensores > 20) {
			System.out.print("Quantidade inválida (mínimo 1 - máximo 20): ");
			quantidadeSensores = sc.nextInt();
		}
		
		soilSample mapSensors[] = new soilSample[quantidadeSensores];
		List<Integer> changedPh = new ArrayList<>();
		
		// Dia 1
		for(int contador = 0; contador < quantidadeSensores; contador++) {
			soilSample sample = new soilSample();
			Random r = new Random();
			int x = r.nextInt(ID_MAX) + ID_MIN;
			for(int tamanhoVetorAtual = 0; tamanhoVetorAtual < contador; tamanhoVetorAtual++) {
				while(mapSensors[tamanhoVetorAtual].id == x) {
					x = r.nextInt(ID_MAX) + ID_MIN;
				}
			}
			sample.id = x;
			sample.ph = (float) (r.nextInt(PH_MAX) + PH_MIN) / 10;
			sample.umidade = r.nextInt(UMIDADE_MAX) + UMIDADE_MIN;
			mapSensors[contador] = sample;
		}
		
		// Adicionando ao vetor os ID's com pH alterado
		queryPh(mapSensors, changedPh, quantidadeSensores);
		System.out.println("---------------- DIA 1 ----------------");
		System.out.println("Foram contabilizados: " + changedPh.size() + " registros de pH alterado!");
		System.out.println("pHs alterados (1º dia): " + changedPh);
		
		System.out.println();
		System.out.println("Observações diárias: ");
		for(int contador = 0; contador < quantidadeSensores; contador++) {
			System.out.println("Id: " + mapSensors[contador].id + " pH: " + mapSensors[contador].ph + " umidade: " + mapSensors[contador].umidade);
		}
		
		// Dia 2
		dayChange(mapSensors, PH_MIN, PH_MAX, UMIDADE_MIN, UMIDADE_MAX, quantidadeSensores);
		// Adicionando ao vetor os ID's com pH alterado
		queryPh(mapSensors, changedPh, quantidadeSensores);
		System.out.println();
		System.out.println("---------------- DIA 2 ----------------");
		System.out.println("Foram contabilizados: " + changedPh.size() + " registros de pH alterado!");
		System.out.println("pHs alterados (1º e 2º dia): " + changedPh);
		
		System.out.println();
		System.out.println("Observações diárias: ");
		for(int contador = 0; contador < quantidadeSensores; contador++) {
			System.out.println("Id: " + mapSensors[contador].id + " pH: " + mapSensors[contador].ph + " umidade: " + mapSensors[contador].umidade);
		}
		
		// Dia 3
		dayChange(mapSensors, PH_MIN, PH_MAX, UMIDADE_MIN, UMIDADE_MAX, quantidadeSensores);
		// Adicionando ao vetor os ID's com pH alterado
		queryPh(mapSensors, changedPh, quantidadeSensores);
		System.out.println();
		System.out.println("---------------- DIA 3 ----------------");
		System.out.println("Foram contabilizados: " + changedPh.size() + " registros de pH alterado!");
		System.out.println("pHs alterados (1º, 2º e 3º dia): " + changedPh);
		
		System.out.println();
		System.out.println("Observações diárias: ");
		for(int contador = 0; contador < quantidadeSensores; contador++) {
			System.out.println("Id: " + mapSensors[contador].id + " pH: " + mapSensors[contador].ph + " umidade: " + mapSensors[contador].umidade);
		}
		
		// Ordenação
		System.out.println();
		System.out.println("----------------------------------------------------------------------------");
		System.out.println();
		System.out.print("Você deseja organizar os dados de forma crescente (1) ou decescente (2)? ");
		int escolhaOrdenacao = sc.nextInt();
		
		
		if(escolhaOrdenacao == 1) {
			bubbleSortCrescente(quantidadeSensores, mapSensors);
			for(int contador = 0; contador < quantidadeSensores; contador++) {
				System.out.println("Id: " + mapSensors[contador].id + " pH: " + mapSensors[contador].ph + " umidade: " + mapSensors[contador].umidade);
			}
			
		}
		
		if(escolhaOrdenacao == 2) {
			bubbleSortDecrescente(quantidadeSensores, mapSensors);
			for(int contador = 0; contador < quantidadeSensores; contador++) {
				System.out.println("Id: " + mapSensors[contador].id + " pH: " + mapSensors[contador].ph + " umidade: " + mapSensors[contador].umidade);
			}
			
		}
		
		System.out.println();
		System.out.println("----------------------------------------------------------------------------");
		System.out.println();
		System.out.print("Digite o código do sensor para buscar: ");
		int escolhaBusca = sc.nextInt();
		
		System.out.println(buscaBinaria(mapSensors, escolhaOrdenacao, escolhaBusca));
		
	}
	// Funções Auxiliares
	public static void queryPh(soilSample mapSensors[], List<Integer> changedPh, int quantidadeSensores) {
		for(int contador = 0; contador < quantidadeSensores; contador++) {
			if(mapSensors[contador].ph < 5.5 || mapSensors[contador].ph > 6.5) {
				if(!changedPh.contains(mapSensors[contador].id))
					changedPh.add(mapSensors[contador].id);
			}
		}
	}
	
	public static void dayChange(soilSample mapSensors[], int PH_MIN, int PH_MAX, int UMIDADE_MIN, int UMIDADE_MAX, int quantidadeSensores) {
		for(int contador = 0; contador < quantidadeSensores; contador++) {
			Random r = new Random();
			mapSensors[contador].ph = ((float) (r.nextInt(PH_MAX) + PH_MIN) / 10);
			mapSensors[contador].umidade = (r.nextInt(UMIDADE_MAX) + UMIDADE_MIN);
		}
	}
	
	public static void bubbleSortCrescente(int quantidadeSensores, soilSample mapSensors[]) {
		int aux;
		float auxpH;
		int auxUmidade;
		
		for(int j = 0; j < quantidadeSensores; j++) {
			for(int i = 0; i < (quantidadeSensores - 1); i++) {
				if(mapSensors[i].id > mapSensors[i+1].id) {
					aux = mapSensors[i].id;
					auxpH = mapSensors[i].ph;
					auxUmidade = mapSensors[i].umidade;
					
					mapSensors[i].id = mapSensors[i+1].id;
					mapSensors[i].ph = mapSensors[i+1].ph;
					mapSensors[i].umidade = mapSensors[i+1].umidade;
					
					mapSensors[i+1].id = aux;
					mapSensors[i+1].ph = auxpH;
					mapSensors[i+1].umidade = auxUmidade;
				}
			}
		}
	}
	
	public static void bubbleSortDecrescente(int quantidadeSensores, soilSample mapSensors[]) {
		int aux;
		float auxpH;
		int auxUmidade;
		
		for(int j = 0; j < quantidadeSensores; j++) {
			for(int i = 0; i < (quantidadeSensores - 1); i++) {
				if(mapSensors[i].id < mapSensors[i+1].id) {
					aux = mapSensors[i].id;
					auxpH = mapSensors[i].ph;
					auxUmidade = mapSensors[i].umidade;
					
					mapSensors[i].id = mapSensors[i+1].id;
					mapSensors[i].ph = mapSensors[i+1].ph;
					mapSensors[i].umidade = mapSensors[i+1].umidade;
					
					mapSensors[i+1].id = aux;
					mapSensors[i+1].ph = auxpH;
					mapSensors[i+1].umidade = auxUmidade;
				}
			}
		}
	}
	
	public static String buscaBinaria(soilSample mapSensors[], int escolhaOrdenacao, int escolhaBusca) {
		boolean achou = false;
		int index = 0;
		int inicio = 0;
		int fim = mapSensors.length - 1;
		int meio;
		
		if(escolhaOrdenacao == 1) {
			while(inicio <= fim) {
				meio = (int) ((inicio + fim) /2);
				if(mapSensors[meio].id == escolhaBusca) {
					achou = true;
					index = meio;
					break;
				}else if(mapSensors[meio].id < escolhaBusca) {
					inicio = meio + 1;
				}else {
					fim = meio - 1;
				}
			}
			
			if(achou == true) {
				System.out.println();
				return "Sensor na posição: " + index + " do vetor"
						+ "\nDados do sensor: "
						+ "\nID: " + mapSensors[index].id 
						+ "\npH: " + mapSensors[index].ph + 
						"\nUmidade: " + mapSensors[index].umidade;
			}else {
				return "Sensor inexistente!";
			}
		}else {
			while(inicio <= fim) {
				meio = (int) ((inicio + fim) /2);
				if(mapSensors[meio].id == escolhaBusca) {
					achou = true;
					index = meio;
					break;
				}else if(mapSensors[meio].id > escolhaBusca) {
					inicio = meio + 1;
				}else {
					fim = meio - 1;
				}
			}
			
			if(achou == true) {
				System.out.println();
				return "Sensor na posição: " + index + " do vetor"
						+ "\nDados do sensor: "
						+ "\nID: " + mapSensors[index].id 
						+ "\npH: " + mapSensors[index].ph + 
						"\nUmidade: " + mapSensors[index].umidade;
			}else {
				return "Sensor inexistente!";
			}
		}

	}

}
