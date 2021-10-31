package br.com.ivia.ceart.atendimento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "atendimento_anexo", schema = "producao")
@JsonInclude(Include.NON_NULL)
@SequenceGenerator(name = "seq_anexo", sequenceName = "producao.anexo_id_anexo_seq", allocationSize = 1)
@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode(callSuper=false)
public class AtendimentoAnexo extends BaseModel {

	private static final long serialVersionUID = 4998056949905245620L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_anexo")
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@ManyToOne
    @JoinColumn(name = "cd_atendimento", referencedColumnName = "id")
    @JsonIgnore
	private Atendimento atendimento;
	
	@NotEmpty(message="{campo.nome.obrigatorio}")
	@Column(name="nome",nullable=false,length=50)
	private String nome;
	
	@Column(name="tipo",nullable=true,length=20)
	private String tipo;
	

}
