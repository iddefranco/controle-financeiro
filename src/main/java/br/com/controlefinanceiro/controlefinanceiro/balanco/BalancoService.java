package br.com.controlefinanceiro.controlefinanceiro.balanco;


import br.com.controlefinanceiro.controlefinanceiro.http.balanco.dto.BalancoDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.repository.LancamentoRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalancoService {

	private final LancamentoRepository lancamentoRepository;


	public BalancoDto retornaBalanco(LocalDate dataIni, LocalDate dataFim,
										   Long idCategoria) {

		return lancamentoRepository.retornaBalanco(dataIni, dataFim, idCategoria);
	}
}
