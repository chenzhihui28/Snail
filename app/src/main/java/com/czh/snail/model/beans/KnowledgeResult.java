package com.czh.snail.model.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KnowledgeResult {
    public boolean error;
    public @SerializedName("results")
    List<Knowledge> knowledges;
}
