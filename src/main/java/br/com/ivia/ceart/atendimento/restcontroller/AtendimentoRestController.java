package br.com.ivia.ceart.atendimento.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.ivia.ceart.atendimento.model.Atendimento;
import br.com.ivia.ceart.atendimento.model.AtendimentoAnexo;
import br.com.ivia.ceart.atendimento.service.AtendimentoAnexoService;
import br.com.ivia.ceart.atendimento.service.AtendimentoService;
import br.com.ivia.ceart.atendimento.service.StorageService;
import br.com.ivia.ceart.atendimento.to.AtendimentoTO;
import br.com.ivia.ceart.atendimento.to.MessageTO;
import br.com.ivia.ceart.atendimento.to.MotivoSituacaoTO;

@RestController
@RequestMapping(path = "atendimento")
public class AtendimentoRestController {
	
    @Autowired
    private AtendimentoService service;    

	@Autowired
	private AtendimentoAnexoService atendimentoAnexoService;
    
	@Autowired
	StorageService storageService;
	
	List<String> files = new ArrayList<String>();
    
	@RequestMapping("")
	public String index(){
		return "RestController: Index - Atendimento";
	}
	
    @GetMapping(path = "findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageTO> findAll() {
        return new ResponseEntity<>(new MessageTO("Atendimentos encontrados", true, service.findAll()), HttpStatus.OK);
    }
    
    @PostMapping(path = "visualizarlistagem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageTO> visualizarListagem(@RequestBody AtendimentoTO atendimento) {
        return new ResponseEntity<>(service.findAll(atendimento), HttpStatus.OK);
    }
    
	@GetMapping(path = "visualizardetalhes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageTO> visualizarDetalhes(@PathVariable("id") Integer id) {
		return new ResponseEntity<>(new MessageTO("Atendimento encontrado", true, service.findById(id)),
				HttpStatus.OK);
	}
	
    @PostMapping(path = "incluir", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageTO> incluir(@RequestBody Atendimento atendimento) {
        return new ResponseEntity<>(new MessageTO("Atendimento salvo com sucesso.", true, service.save(atendimento)), HttpStatus.OK);
    }
    
    @PutMapping(path = "alterar", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageTO> alterar(@RequestBody Atendimento atendimento) {
        return new ResponseEntity<>(new MessageTO("Atendimento atualizado com sucesso.", true, service.save(atendimento)), HttpStatus.OK);
    }
    
	@PostMapping(path = "alterarsituacao", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageTO> alterarsituacao(@RequestBody MotivoSituacaoTO motivoSituacao) {
		return new ResponseEntity<>(new MessageTO("Situação de atedimento alterada com sucesso", true,
				service.alterarSituacao(motivoSituacao)), HttpStatus.OK);
	}
    
    @PostMapping(path = "reenviar", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageTO> reenviar(@RequestBody Atendimento atendimento) {
        return new ResponseEntity<>(new MessageTO("Reenvio realizado com sucesso.", true, service.reenviar(atendimento)), HttpStatus.OK);
    }
    
    @PostMapping(path = "enviarEmail", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageTO> enviarEmail(@RequestBody Atendimento atendimento) {
        return new ResponseEntity<>(new MessageTO("Email enviado com sucesso.", true, service.enviarEmail(atendimento)), HttpStatus.OK);
    }    

	@PostMapping("/upload")
	public ResponseEntity<MessageTO> handleFilesUpload(
			@RequestParam("uploadingImagens") MultipartFile[] uploadingImagens,
			@RequestParam("uploadingDocs") MultipartFile[] uploadingDocs, @RequestParam("atendimentoid") Integer id) {
		try {
			storageService.init();
			for (MultipartFile uploadedFile : uploadingImagens) {
				String nomeArquivo = System.currentTimeMillis() + "_" + uploadedFile.getOriginalFilename().replaceAll("\\s+", "_");
				storageService.store(uploadedFile, nomeArquivo);
				AtendimentoAnexo atendimentoAnexo = new AtendimentoAnexo();
				atendimentoAnexo.setNome(nomeArquivo);
				atendimentoAnexo.setTipo("imagem");
				Atendimento atendimento = new Atendimento();
				atendimento.setId(id);
				atendimentoAnexo.setAtendimento(atendimento);
				atendimentoAnexoService.save(atendimentoAnexo);
			}

			for (MultipartFile uploadedFile : uploadingDocs) {
				String nomeArquivo = System.currentTimeMillis() + "_" + uploadedFile.getOriginalFilename().replaceAll("\\s+", "_");
				storageService.store(uploadedFile, nomeArquivo);
				AtendimentoAnexo atendimentoAnexo = new AtendimentoAnexo();
				atendimentoAnexo.setNome(nomeArquivo);
				atendimentoAnexo.setTipo("doc");
				Atendimento atendimento = new Atendimento();
				atendimento.setId(id);
				atendimentoAnexo.setAtendimento(atendimento);
				atendimentoAnexoService.save(atendimentoAnexo);
			}

			return new ResponseEntity<>(new MessageTO("Upload realizado com sucesso!", true, files), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageTO("Erro no upload dos arquivos!" + e.getMessage(), false, files),
					HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping("/docs/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getDoc(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@GetMapping("/imagens/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getImagem(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).contentType(MediaType.IMAGE_PNG).body(file);
	}
}
