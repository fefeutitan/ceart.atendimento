package br.com.ivia.ceart.atendimento.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.ivia.ceart.atendimento.util.enums.SituacaoAtual;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "atendimento", schema = "producao")
@JsonInclude(Include.NON_NULL)
@SequenceGenerator(name = "seq_atendimento", sequenceName = "producao.atendimento_id_atendimento_seq", allocationSize = 1)
@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode(callSuper=false)
public class Atendimento extends BaseModel {

	private static final long serialVersionUID = 4998056949905245620L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_atendimento")
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@Column(name = "data", nullable = true)
	private Date data;

	@Column(name = "ano_protocolo", nullable = false)
	private Integer anoProtocolo;
	
	@Column(name = "numero_protocolo", nullable = false, length = 150)
	private String numeroProtocolo;	
	
	@Column(name = "cpf_cnpj", nullable = true, length = 18)
	private String cpfCnpj;

	@Column(name = "tp_pessoa", nullable = true, length = 1)
	private String tipoPessoa;

	@Column(name = "solicitante", nullable = true, length = 50)
	private String solicitante;

	@Column(name = "instituicao", nullable = true, length = 50)
	private String instituicao;

	@Column(name = "cargo", nullable = true, length = 50)
	private String cargo;

	@Column(name = "email_solicitante", nullable = true, length = 50)
	private String email;

	@Column(name = "telefone1", nullable = true, length = 50)
	private String telefone1;

	@Column(name = "telefone2", nullable = true, length = 50)
	private String telefone2;

	@Column(name = "descricao", nullable = true)
	private String descricao;

	@Column(name = "situacao", nullable = true)
	private SituacaoAtual situacao;

	@ManyToOne
    @JoinColumn(name = "cd_tp_atendimento", referencedColumnName = "id")
	private TipoAtendimento tipoAtendimento;

	@ManyToOne
    @JoinColumn(name = "cd_op_atendimento", referencedColumnName = "id")
	private OpcaoAtendimento opcaoAtendimento;
	
	@ManyToOne
    @JoinColumn(name = "cd_municipio", referencedColumnName = "id")
    private Municipio municipio;
	
	@OneToMany(mappedBy="atendimento", cascade= CascadeType.ALL)	
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<AtendimentoAnexo> anexo;
	
	@Transient
	private String motivo;
	
	@Transient
	private String observacao;
}