/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pruebas.sheetsmaven;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ELVIS
 */
public class Sheetsc{
    private static String nombreAp;

    public Sheetsc(String nombreAp){
        this.nombreAp=nombreAp; 
       
    }
   
  
   
    private static Credential autorizar() throws IOException,GeneralSecurityException{
        InputStream in=Sheets.class.getResourceAsStream("/credencial.json");
        System.out.println(in);
        GoogleClientSecrets clienteSecrets=GoogleClientSecrets.load(
                GsonFactory.getDefaultInstance(),new InputStreamReader(in)
        );
        
        List<String> scopes =Collections.singletonList(SheetsScopes.SPREADSHEETS);
        
        GoogleAuthorizationCodeFlow flow= new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),GsonFactory.getDefaultInstance(),
                clienteSecrets,scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new File("tokens")))
                .setAccessType("offline")
                .build();
        
        Credential credencial=new AuthorizationCodeInstalledApp(flow,new LocalServerReceiver())
                .authorize("user");
        
        return credencial;
        
    }
    public static Sheets obtenerServicioSheets() throws IOException,GeneralSecurityException{
        Credential credential=autorizar();
       
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
        GsonFactory.getDefaultInstance(),credential)
                .setApplicationName(nombreAp)
                .build();
    }
    
 
}
