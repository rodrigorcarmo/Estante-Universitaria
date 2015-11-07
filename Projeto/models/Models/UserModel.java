
package Models;

public class UserModel {
    private int idUsuario;
    private String email;
    private String password;
    private String cep;
    private String endereco;
    private int endNum;
    private String endComplemento;
    private String estado;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }
    private String cidade;
    private String universidade;
    private String telefone;
    private String celular;
    private String nome;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setUniversidade(String universidade) {
        this.universidade = universidade;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndComplemento() {
        return endComplemento;
    }

    public void setEndComplemento(String endComplemento) {
        this.endComplemento = endComplemento;
    }

    public String getPassword() {
        return password;
    }

    public String getCep() {
        return cep;
    }

    public String getEndereco() {
        return endereco;
    }



    public String getEstado() {
        return estado;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUniversidade() {
        return universidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCelular() {
        return celular;
    }

    public String getNome() {
        return nome;
    }
    
        
}
