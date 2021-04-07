package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

// Campo � cada quadrado do jogo
public class Campo {

	private final int linha;
	private final int coluna;
	// Campo aberto - indica se o campo está aberto ou fechado
	private boolean aberto = false;
	private boolean minado = false;
	// Marcado indica se campo está com sinalizador
	private boolean marcado = false;

	private List<Campo> vizinhos = new ArrayList<>();

	Campo(int _linha, int _coluna) {
		this.linha = _linha;
		this.coluna = _coluna;
	}

	// Cria regra pra adicionar campos vizinhos
	// Consideremos diagonal se n�o estiver na mesma
	// linha ou coluna.
	// Na diagonal dif = 2; na linha ou coluna dif = 1;
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;

		// Calcula dif entre linha e coluna
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;

		// Se n�o estiver em diagonal (linha ou coluna)
		if (deltaGeral == 1 && !diagonal) {
			// Adiciona como vizinho
			vizinhos.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}

	}

	void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;
		}
	}

	boolean abrir() {
		if (!aberto && !marcado) {
			aberto = true;
			// Se o campo estiver minado jogo acaba
			// Usando exceção pra sinalizar
			if (minado) {
				// TODO implementar nova vers�o
				// FIXME
			}

			if (vizinhancaSegura()) {
				// Abre todos os vizinhos seguros
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		} else {
			return false;
		}
	}

	boolean vizinhancaSegura() {
		// Se nenhum vizinho der match está seguro
		// Se a lambda for falso ao menos 1 vizinho está minado
		// e a vizinhança não é segura
		return vizinhos.stream().noneMatch(v -> v.minado);
	}

	void minar() {
		minado = true;
	}
	
	public boolean isMinado() {
		return minado;
	}

	public boolean isMarcado() {
		return marcado;
	}
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isFechado() {
		return !isAberto();
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	// Objetivo alcançado para um único campo
	boolean objetivoAlcancado() {
		// Não minado e aberto
		boolean desvendado = !minado && aberto;
		// protegido é minado e marcado
		boolean protegido = minado && marcado;
		
		return desvendado || protegido;
	}
	long minasNaVizinhanca() {
		// Descobrir quantidade de minas na vizinhança
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	void reiniciar() {
		// Zera atributos
		aberto = false;
		minado = false;
		marcado = false;
	}

	 
}
