package br.com.controlefinanceiro.controlefinanceiro.apikey.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "api_key")
@NoArgsConstructor
@AllArgsConstructor
public class ApiKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "key")
    private String key;


	public ApiKey(String s, String authorization, String header) {
	}
}