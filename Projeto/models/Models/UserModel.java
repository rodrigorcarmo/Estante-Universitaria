
package Models;

public class UserModel {
    private String email;
    private String password;
    private String cep;
    private String endereco;
    private String endNum;
    private String endComplemento;
    private String estado;
    private String cidade;
    private String universidade;
    private String telefone;
    private String celular;
    private String nome;

    public String getEmail() {
        return email;
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

    public String getEndNum() {
        return endNum;
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
