package br.com.alunoonline.api.service;

import br.com.alunoonline.api.dtos.HistoricoAlunoResponse;
import br.com.alunoonline.api.enums.MatriculaAlunoStatusEnum;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MatriculaAlunoService {

    @Autowired
    MatriculaAlunoRepository matriculaAlunoRepository;

    /*
    É aqui que o aluno vai se matricular
    */
    public void criarMatricula(MatriculaAluno matriculaAluno) {
        matriculaAluno.setStatus(MatriculaAlunoStatusEnum.MATRICULADO);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    public void trancarMatricula(Long matriculaAlunoid) {
        MatriculaAluno matriculaAluno = matriculaAlunoRepository.findById(matriculaAlunoid)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Matrticula Aluno não encontrada!"));
        if (!MatriculaAlunoStatusEnum.MATRICULADO.equals(matriculaAluno.getStatus())){
            // Lançar o erro se o status não for matriculado
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Só e possível trancar uma matricula com o status MATRICULADO");
        }

        matriculaAluno.setStatus(MatriculaAlunoStatusEnum.TRANCADO);

        matriculaAlunoRepository.save(matriculaAluno);
    }

    private  void calculaMedia(MatriculaAluno matriculaAluno) {
        if (nota1 != null &&  nota2 != null) {
            Double media = (nota1 + nota2) / 2;
            matriculaAluno.setStatus(media >= MEDIA_PARA_APROVACAO ? MatriculaAlunoStatusEnum.APROVADO);
        }
    }

    public HistoricoAlunoResponse emitirHistorico(Long alunoId){
        List<MatriculaAluno> matriculasDoAluno = matriculaAlunoRepository.findByAlunoId(alunoId);

        if(matriculasDoAluno.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Este aluno não possui matrícula");
        }

        return null;
    }

}
