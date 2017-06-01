package br.edu.utfpr.universidade;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class AlunoBean implements Serializable {

    private List<Aluno> alunos;
    private Aluno aluno = new Aluno();
    private Aluno alunoSelecionado = new Aluno();

    @PostConstruct
    public void init() {
        atualizaListaAlunos();
    }

    public void atualizaListaAlunos() {
        alunos = EManager.getInstance().createNamedQuery("Aluno.findAll").getResultList();
    }

    public void modificaAluno() {
        EManager.getInstance().getTransaction().begin();
        EManager.getInstance().merge(this.alunoSelecionado);
        EManager.getInstance().getTransaction().commit();
        atualizaListaAlunos();
    }
    
    public void deletaAluno() {
        List<Matricula> m = EManager.getInstance().createNamedQuery("Matricula.findByAluno").getResultList();
        EManager.getInstance().getTransaction().begin();
        for (int i = 0; i < m.size(); i++) {
            EManager.getInstance().remove(m.get(i));
        }
        EManager.getInstance().remove(this.alunoSelecionado);
        EManager.getInstance().getTransaction().commit();
        atualizaListaAlunos();
    }

    public void enviaAluno(Aluno a) {
        this.alunoSelecionado = a;
    }

    public void novoCadastro() {
        this.aluno.setMatricula(1000000 + new Random().nextInt(9999999 - 1000000 + 1));
        EManager.getInstance().getTransaction().begin();
        EManager.getInstance().persist(this.aluno);
        EManager.getInstance().getTransaction().commit();
        this.aluno = new Aluno();
        atualizaListaAlunos();
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Aluno getAlunoSelecionado() {
        return alunoSelecionado;
    }

    public void setAlunoSelecionado(Aluno alunoSelecionado) {
        this.alunoSelecionado = alunoSelecionado;
    }
    
}
