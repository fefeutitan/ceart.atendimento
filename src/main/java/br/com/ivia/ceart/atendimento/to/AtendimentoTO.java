package br.com.ivia.ceart.atendimento.to;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.domain.Sort.Direction;

import br.com.ivia.ceart.atendimento.model.OpcaoAtendimento;
import br.com.ivia.ceart.atendimento.model.TipoAtendimento;
import br.com.ivia.ceart.atendimento.util.enums.SituacaoAtual;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtendimentoTO implements Serializable {

	private static final long serialVersionUID = -2795349486504616322L;


	private String protocolo;
	
	private String solicitante;
	
	private String cpfCnpj;
	
	private Date dataInicioBusca;
	
	private Date dataFimBusca;
	
	private TipoAtendimento tipoAtendimento; 
	
	private OpcaoAtendimento opcaoAtendimento; 
	
	private SituacaoAtual situacao;
	
	private Integer pageNumber;
	
	private Integer pageSize;
	
	private Direction direction;
	
	private String[] by;

}
