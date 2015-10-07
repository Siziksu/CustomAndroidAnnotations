package com.siziksu.annotations.app.mock;

import com.siziksu.annotations.app.annotation.Info;

@Info(
    priority = Info.Priority.LOW,
    createdBy = "Esteban",
    contextDescription = "Info annotations fake class",
    lastModified = "07/10/2015",
    tags = {"class", "annotations", "fake"})
public class InfoFakeClass {

}