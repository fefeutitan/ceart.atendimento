package br.com.ivia.ceart.atendimento.to;

import java.io.Serializable;

import br.com.ivia.ceart.atendimento.util.enums.SituacaoAtual;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MotivoSituacaoTO implements Serializable {
	
	private static final long serialVersionUID = 6632534483369365842L;
	
	private Integer idEntidade;
	private Integer idMotivo;
	private Integer idSituacao;
	private Integer usuario;
	private String observacao;
	
	private SituacaoAtual situacao;
	private Integer idRecurso;
	private Boolean ativo;
}
