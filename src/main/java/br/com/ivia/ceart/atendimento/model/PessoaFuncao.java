package br.com.ivia.ceart.atendimento.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@IdClass(PessoaFuncao.class)
@Table(name = "pessoa_funcao", schema = "producao")
@JsonInclude(Include.NON_NULL)
@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode(callSuper=false)
public class PessoaFuncao extends BaseModel {

	private static final long serialVersionUID = 4998056949905245620L;

	@Id
	@ManyToOne
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
	private Pessoa id_pessoa;
	
	@Id
	@ManyToOne
    @JoinColumn(name = "id_funcao", referencedColumnName = "id")
	private Funcao id_funcao;	

	private Date dt_inicio;

	private Date dt_fim;

}
