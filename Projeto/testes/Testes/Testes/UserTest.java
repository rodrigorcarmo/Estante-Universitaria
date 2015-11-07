/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import Models.UserModel;
import Persistence.UserPersistence;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pedro
 */
public class UserTest {

    @Test
    public void testRegisterUser() {
        UserModel um = new UserModel();
        um.setCelular("123456789");
        um.setCep("123456789");
        um.setCidade("Cidade");
        um.setEmail("email@email.com");
        um.setEndComplemento("Endereco Complemento");
        um.setEndNum(1234);
        um.setEstado("Estado");
        um.setEndereco("Rua rua");
        um.setNome("Nome");
        um.setPassword("123456789qwerty");
        um.setTelefone("123456789");
        um.setUniversidade("Universidade");
        UserPersistence.insert(um);
        UserModel teste = UserPersistence.getUser(um.getEmail());
        assertEquals(teste.getCelular(), um.getCelular());
        assertEquals(teste.getCep(), um.getCep());
        assertEquals(teste.getCidade(), um.getCidade());
        assertEquals(teste.getEmail(), um.getEmail());
        assertEquals(teste.getEndComplemento(), um.getEndComplemento());
        assertEquals(teste.getEndNum(), um.getEndNum());
        assertEquals(teste.getEndereco(), um.getEndereco());
        assertEquals(teste.getNome(), um.getNome());
        assertEquals(teste.getPassword(), um.getPassword());
        assertEquals(teste.getTelefone(), um.getTelefone());
        assertEquals(teste.getUniversidade(), um.getUniversidade());

    }
    
    @Test
    public void testUpdateUser(){
        UserModel um = UserPersistence.getUser("email@email.com");
        Gson gson = new Gson();
        String id = String.valueOf(um.getIdUsuario());
        JsonObject jTeste = (JsonObject) gson.toJsonTree(um);
        for (Map.Entry<String, JsonElement> j : jTeste.entrySet()) {
               if(j.getKey().equals("idUsuario") || j.getKey().equals("email"));
               else{
                   if(j.getKey().equalsIgnoreCase("endNum")){
                       jTeste.addProperty(j.getKey(), 1);
                   }
                   else
                    jTeste.addProperty(j.getKey(), "update");
               }
        }
        UserPersistence.updateUser(jTeste, id);
        UserModel teste = UserPersistence.getUser("email@email.com");
        assertEquals(teste.getCelular(), "update");
        assertEquals(teste.getCep(), "update");
        assertEquals(teste.getCidade(), "update");
        assertEquals(teste.getEmail(), "email@email.com");
        assertEquals(teste.getEndComplemento(), "update");
        assertEquals(teste.getEndNum(), 1);
        assertEquals(teste.getEndereco(), "update");
        assertEquals(teste.getNome(), "update");
        assertEquals(teste.getPassword(), "update");
        assertEquals(teste.getTelefone(), "update");
        assertEquals(teste.getUniversidade(), "update");
        
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
