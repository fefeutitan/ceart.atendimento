package br.com.ivia.ceart.atendimento.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="celula",schema="producao")
@JsonInclude(Include.NON_NULL)
@SequenceGenerator(name="seq_celula",sequenceName="producao.celula_id_celula_seq",allocationSize=1)
@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode(callSuper=false)
public class Celula extends BaseModel{

	private static final long serialVersionUID = 4998056949905245620L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_celula")
	@Column(name="id",nullable=false,unique=true)
	private Integer id;
	
	@NotEmpty(message="{campo.nome.obrigatorio}")
	@Column(name="nome",nullable=false,length=50)
	private String nome;

	@Column(name="email_cc",nullable=false,length=50)
	private String email_cc;	
	
	@LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "celula", fetch = FetchType.EAGER, orphanRemoval = true, cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE })
    private List<PessoaCelula> pessoa;

}
