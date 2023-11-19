package com.consumeapirest.demo;

import java.util.ArrayList;

public class ResponsePadraoDTO {
    public int statusCode;
    public String mensagem;
    public ArrayList<String> erros;
    public ResponsePadraoDTO(){
        this.erros = new ArrayList<>();
    }
}
