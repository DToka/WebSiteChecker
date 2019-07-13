module webSiteChecker {

     requires javafx.graphics;
     requires javafx.controls;
     requires javafx.media;
     requires javafx.base;
     requires javafx.web;
     requires javafx.swing;
     requires javafx.fxml;
     requires jdk.httpserver;

     opens view;
     opens model;
     opens controller;
     opens resources;

}